package com.ljd.hackajob.phonebook.model.exceptions;

public class Messages {
    private Messages() {
        // not meant to be instantiated
    }

    public static final String BUNDLE_NAME = Messages.class.getName();

    // PBE0000: An internal server error occurred.
    public static final String PBE0000 = "PBE0000";

    // PBE0001: No Contact found with id '%s'
    public static final String PBE0001 = "PBE0001";

    // PBE0002: No Phone Number found with type '%s'
    public static final String PBE0002 = "PBE0002";

    // PBE0003: A Phone Number with type '%s' already exists in this Contact.
    public static final String PBE0003 = "PBE0003";
    
    //PBE0004: The request was invalid: '%s'.
    public static final String PBE0004 = "PBE0004";
}
