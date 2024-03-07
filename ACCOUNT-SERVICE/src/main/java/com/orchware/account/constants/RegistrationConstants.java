package com.orchware.account.constants;

public interface RegistrationConstants {

    /**
     * Auth Types
     */
    String STANDARD = "STANDARD";
    String OAUTH = "OAUTH";

    /**
     * User Account Types
     */
    String PRIMARY_USER = "Primary";
    String LINKED_USER = "Linked";

    int REGISTRATION_TIMESTAMP_BUFFER = 1000 * 120; // = 2 minutes for testing
    // TODO: add in System_Attr_Values
    interface Message {
        interface Service {
            String SAVE_SUCCESS = "%s saved successfully";

            String REGISTERED_SUCCESS = "%s registered successfully";
            String UPDATE_SUCCESS = "%s with id %d updated successfully";
            String DELETE_SUCCESS = "%s with id %d deleted!";
            String DELETE_ALL_SUCCESS = "All %s deleted!";
        }
    }
}
