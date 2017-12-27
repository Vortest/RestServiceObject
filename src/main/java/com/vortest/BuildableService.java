package com.vortest;

/**
 * Created by Brian on 1/12/17.
 */
public interface BuildableService {
    public ServiceBuilder getBuilder();

    public ContextObject getContextObject();
}
