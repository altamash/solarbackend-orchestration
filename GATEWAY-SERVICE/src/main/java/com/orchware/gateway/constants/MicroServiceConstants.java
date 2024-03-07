package com.orchware.gateway.constants;

public class MicroServiceConstants {

    public static final String CORE_BASE_API = "/core";

    public interface CoreServiceConstants {
        String BASE = "CORE-SERVICE";
        String FETCH_ACCOUNT_BY_USERNAME = "/findByUsername/{username}";
    }

    public static final String ACCOUNT_BASE_API = "/account";

    public interface AccountServiceConstants {
        String BASE = "ACCOUNT-SERVICE";
        String REGISTER_ACCOUNT = "/stdRegistration";
        String FETCH_ACCOUNT_BY_USERNAME = "/findByUsername/{username}";
    }

    public interface CommonsService {
        String BASE = "commons-service";
        String ENCRYPTION = "/commons/enc";
        String GENERATE_PEM_STRINGS = "/asymmetric/generatePemStrings";
        String GET_PEM_STRING = "/getPEMString";
        String ENCRYPT_ASYMMETRIC = "/asymmetric/encrypt";
        String DECRYPT_ASYMMETRIC = "/asymmetric/decrypt";
    }
}
