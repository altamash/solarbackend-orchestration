package com.orchware.login.constants;

/**
 * This class includes the name and API end points of other microservices that we need to communicate.
 * NOTE: WRITE EVERYTHING IN ALPHABETICAL ORDER
 */
public class MicroServiceConstants {

    /**
     * Base API for this service
     */
    public static final String BASE_API = "/auth";

    public static final String ACCOUNT_BASE_API = "/account";

    public interface AccountServiceConstants {
        String BASE = "ACCOUNT-SERVICE";
        String REGISTER_ACCOUNT = "/stdRegistration";
        String FETCH_ACCOUNT_BY_USERNAME = "/findByUsername/{username}";
    }

}
