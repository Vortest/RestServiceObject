package com.vortest;

import com.google.inject.Inject;

import java.lang.reflect.Array;

/**
 * Created by Brian on 12/21/16.
 * The ServiceObject is an abstract super class designed to be implemented once per service endpoint.
 * Each class that extends this must at a minimum define the URI, and may include a ResponseObject for deserialization
 */
public abstract class ServiceArrayObject implements BuildableService {
    private ResponseObject[] responseObjects;
    private ServiceExecutor serviceExecutor = new ServiceExecutor(this);
    protected ServiceBuilder serviceBuilder = new ServiceBuilder();

    @Inject
    protected ContextObject contextObject;

    /**
     * Executes a method call and returns a deserialized response object
     * @param method
     * @param <E>
     * @return
     */
    private  <E extends ResponseObject> E[] call(String method){
        responseObjects = serviceExecutor.execute(method,responseObjects);
        return (E[]) responseObjects;
    }

    /**
     * Executes a HTTP Post request on this ServiceObject
     * @param <E> The ResponseObject type
     * @return The deserialized ResponseObject
     */
    public  <E extends ResponseObject> E[] post(){
       return call("post");
    }

    /**
     * Executes a HTTP Get request on this ServiceObject
     * @param <E> The ResponseObject type
     * @return The deserialized ResponseObject
     */
    public <E extends ResponseObject> E[] get(){
        return call("get");
    }

    /**
     * Executes a HTTP Delete request on this ServiceObject
     * @param <E> The ResponseObject type
     * @return The deserialized ResponseObject
     */
    public <E extends ResponseObject> E[] delete(){
        return call("delete");
    }

    /**
     * Executes a HTTP Put request on this ServiceObject
     * @param <E> The ResponseObject type
     * @return The deserialized ResponseObject
     */
    public <E extends ResponseObject> E[] put() {
        return call("put");
    }


    /**
     * Gets the ResponseObject[] Array
     * @param <E> the ResponseObject type for the response objects.
     * @return an Array of ResponseObjects
     */
    public <E extends ResponseObject> E[] getResponseObjects(){
        if(this.responseObjects.length == 0){
            get();
        }

        return(E[]) this.responseObjects;
    }


    public void setResponseType(Class klass){
        this.responseObjects = (ResponseObject[]) Array.newInstance(klass,0);
    }

    public ServiceBuilder getBuilder(){ return this.serviceBuilder;}

    public ContextObject getContextObject(){
        return this.contextObject;
    }

}
