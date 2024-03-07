package com.orchware.commons.module.storage.service;

import com.microsoft.azure.storage.StorageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URISyntaxException;
import java.util.List;

public interface StorageService {
    String getBlobUrl(String container, String directoryReference, String fileName);

    String storeInContainer(MultipartFile file, String container, String directoryString, String fileName,
                            Long compKey, Boolean relativeUrl) throws URISyntaxException, StorageException, IOException;

    String uploadInputStream(InputStream inputStream, Long length, String container, String directoryReference,
                             String fileName, Long compKey, Boolean relativeUrl) throws URISyntaxException,
            StorageException, UnsupportedEncodingException;

    Boolean downloadToOutputStream(OutputStream outputStream, String container, String relativeFileUrl);

    String uploadByteArray(byte[] bytes, String container, String directoryReference, String fileName)
            throws URISyntaxException, StorageException;

    byte[] downloadToByteArray(String container, String url, String name);

    File getBlob(String container, String url, String name);

    void deleteBlob(String container, String fileName, Long compKey, String path);
}
