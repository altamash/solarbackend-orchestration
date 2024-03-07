package com.orchware.core.constants;

public interface OrchConstants {

    // TODO: add in System_Attr_Values
//    int REGISTRATION_TIMESTAMP_BUFFER = 24 * 60 * 2; // = 2 days
    int SMART_KEY_VALIDITY_MINUTES = 200; // = 2 minutes for testing
    interface Message {
        interface Service {
            interface CRUD {
                String SAVE_SUCCESS = "%s saved successfully";
                String UPDATE_SUCCESS = "%s with id %d updated successfully";
                String DELETE_SUCCESS = "%s with id %d deleted!";
                String DELETE_ALL_SUCCESS = "All %s deleted!";
            }
        }
    }
}
