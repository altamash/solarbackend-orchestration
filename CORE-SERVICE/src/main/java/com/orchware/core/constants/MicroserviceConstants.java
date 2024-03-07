package com.orchware.core.constants;

public class MicroserviceConstants {

    public interface CommonsService {
        String BASE = "COMMONS-SERVICE";
        String ENCRYPTION_BASE_API = "/commons/enc";
        String GENERATE_SECRETKEY = "/symmetric/generateSecretKey";
        String GENERATE_PEM_STRINGS = "/asymmetric/generatePemStrings";
        String ENCRYPT_SYMMETRIC = "/symmetric/encrypt";
        String DECRYPT_SYMMETRIC = "/symmetric/decrypt";
        String ENCRYPT_ASYMMETRIC = "/asymmetric/encrypt";
        String DECRYPT_ASYMMETRIC = "/asymmetric/decrypt";
        String ENCRYPT_HYBRID = "/encryptHybrid";
        String DECRYPT_HYBRID = "/decryptHybrid";
    }

    public interface AccountService {
        String BASE = "ACCOUNT-SERVICE";
        String ACCOUNT_BASE_API = "/account";
        String IS_VALID_ACCOUNT = "/isValidAccount/{acctId}";
    }
}
