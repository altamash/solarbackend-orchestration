package com.orchware.commons.module.storage.service;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.microsoft.azure.storage.StorageCredentials;
import com.microsoft.azure.storage.StorageCredentialsSharedAccessSignature;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class StorageServiceImpl implements StorageService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${app.storage.azureBlobSasToken}")
    private String sasToken;
    @Value("${app.storage.blobService}")
    private String blogService;
    @Value("${app.storage.container}")
    private String storageContainer;

//    @Value("${app.storage.saasDirectory}")
//    private String saas;
//    @Value("${app.storage.tenantDirectory}")
//    private String tenant;
//    @Value("${app.storage.publicContainer}")
//    private String publicUrl;
//    private CloudBlobDirectory saasDirectory;
//    private CloudBlobDirectory tenantDirectory;
//    private BlobContainerClient publicContainer;

    //Azure BillingCredits File
    public static final String PREFIX = "tempFile";
    public static final String SUFFIX = ".csv";

    /*@PostConstruct
    public void init() throws URISyntaxException, StorageException {
        saasDirectory = createDirectory(saas);
        tenantDirectory = createDirectory(tenant);
        publicContainer = getBlobContainer(publicUrl);
        createDirectory(publicUrl, tenant);
    }*/

    /**
     * @param container
     * @param directoryReference
     * @param fileName
     * @return
     */
    @Override
    public String getBlobUrl(String container, String directoryReference, String fileName) {
        BlobContainerClient containerClient = getBlobContainer(container);
        String blobUrl = null;
        try {
            BlobClient blobClient =
                    containerClient.getBlobClient(directoryReference + "/" + fileName);
            blobUrl = blobClient.getBlobUrl();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return blobUrl;
    }

    @Override
    public String storeInContainer(MultipartFile file, String container, String directoryReference, String fileName,
                                   Long compKey, Boolean relativeUrl) throws URISyntaxException, StorageException,
            IOException {
        try (ByteArrayInputStream dataStream = new ByteArrayInputStream(file.getBytes())) {
            return uploadInputStream(dataStream, file.getSize(), container, directoryReference, fileName, compKey,
                    relativeUrl);
        }
    }

    // Generic function to uload file in a container and directory
    @Override
    public String uploadInputStream(InputStream inputStream, Long length, String container, String directoryReference
            , String fileName, Long compKey, Boolean relativeUrl) throws URISyntaxException, StorageException,
            UnsupportedEncodingException {
        BlobContainerClient containerClient = getBlobContainer(container);
        if (directoryReference != null && !directoryReference.isEmpty()) {
            createDirectory(directoryReference);
        }
        /* Upload the file to the container */
        String blobUrl = null;
        try {
            BlobClient blobClient =
                    containerClient.getBlobClient(directoryReference + "/" + fileName);
            blobClient.upload(inputStream, length, true);
            blobUrl = blobClient.getBlobUrl();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (relativeUrl) {
            blobUrl = URLEncoder.encode(URLDecoder.decode(blobUrl, StandardCharsets.UTF_8.name())
                    .substring(blogService.length() + container.length() +
                            String.valueOf(compKey).length() + 3), StandardCharsets.UTF_8.name());
            blobUrl = blobUrl.replaceAll("\\+", " ");
        }
        return blobUrl;
    }

    @Override
    public Boolean downloadToOutputStream(OutputStream outputStream, String container, String relativeFileUrl) {
        BlobContainerClient containerClient = getBlobContainer(container);

        /* Upload the file to the container */
        try {
            BlobClient blobClient =
                    !containerClient.getBlobClient(relativeFileUrl).exists() ? null :
                            containerClient.getBlobClient(relativeFileUrl);
            if (blobClient == null) {
                return false;
            }
            blobClient.download(outputStream);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
//            throw e;
        }
        return true;
    }

    @Override
    public String uploadByteArray(byte[] bytes, String container, String directoryReference, String fileName)
            throws URISyntaxException, StorageException {
        BlobContainerClient containerClient = getBlobContainer(container);
        if (directoryReference != null && !directoryReference.isEmpty()) {
            createDirectory(directoryReference);
        }
        String blobUrl = null;
        try {
            BlobClient blobClient =
                    containerClient.getBlobClient(directoryReference + "/" + fileName);
            blobClient.upload(BinaryData.fromBytes(bytes), true);
            blobUrl = blobClient.getBlobUrl();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return blobUrl;
    }

    @Override
    public byte[] downloadToByteArray(String container, String url, String name) {
        byte[] bytes = new byte[0];
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            downloadToOutputStream(os, container, url + "/" + name);
            try (InputStream is = new ByteArrayInputStream(os.toByteArray())) {
                bytes = IOUtils.toByteArray(is);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return bytes;
    }

    private BlobContainerClient getBlobContainer(String container) {
        /* Create a new BlobServiceClient with a SAS Token */
        BlobServiceClient blobServiceClient = getBlobServiceClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(container);
        if (!containerClient.exists()) {
            containerClient = blobServiceClient.createBlobContainer(container);
        }
        return containerClient;
    }

    private CloudBlobDirectory createDirectory(String directoryReference) throws URISyntaxException, StorageException {
        StorageCredentials creds = new StorageCredentialsSharedAccessSignature(sasToken);
        CloudBlobClient cloudBlobClient = new CloudBlobClient(new URI(blogService), creds);
        CloudBlobContainer blobContainer = cloudBlobClient.getContainerReference(storageContainer);
        if (blobContainer.exists()) {
            return blobContainer.getDirectoryReference(directoryReference);
        }
        return null;
    }

    private CloudBlobDirectory createDirectory(String container, String directoryReference) throws URISyntaxException
            , StorageException {
        StorageCredentials creds = new StorageCredentialsSharedAccessSignature(sasToken);
        CloudBlobClient cloudBlobClient = new CloudBlobClient(new URI(blogService), creds);
        CloudBlobContainer blobContainer = cloudBlobClient.getContainerReference(container);
        if (blobContainer.exists()) {
            return blobContainer.getDirectoryReference(directoryReference);
        }
        return null;
    }

    @Override
    public File getBlob(String container, String url, String name) {

        File tempFile = null;
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            Boolean blobClient = downloadToOutputStream(os, container, url + "/" + name);
            if (!blobClient) {
                return null;
            }
            try (InputStream is = new ByteArrayInputStream(os.toByteArray())) {
                tempFile = File.createTempFile(PREFIX, SUFFIX);
                tempFile.deleteOnExit();
                try (FileOutputStream out = new FileOutputStream(tempFile)) {
                    FileUtils.copyInputStreamToFile(is, tempFile);
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return tempFile;
    }

    @Override
    public void deleteBlob(String container, String fileName, Long compKey, String path) {
        BlobServiceClient blobServiceClient = getBlobServiceClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(container);
        BlobClient blobClient =
                containerClient.getBlobClient("tenant/" + compKey + path + "/" + fileName);
        blobClient.delete();
    }

    private BlobServiceClient getBlobServiceClient() {
        return new BlobServiceClientBuilder()
                .endpoint(blogService)
                .sasToken(sasToken)
                .buildClient();
    }
}

