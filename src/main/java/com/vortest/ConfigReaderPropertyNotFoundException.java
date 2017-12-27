package com.vortest;

/**
 * Created by csears on 2/23/17.
 */
public class ConfigReaderPropertyNotFoundException extends RuntimeException {
    public ConfigReaderPropertyNotFoundException() {
        super("Property not found in the configuration.");
    }

    public ConfigReaderPropertyNotFoundException(String message) {
        super(message);
    }
}
