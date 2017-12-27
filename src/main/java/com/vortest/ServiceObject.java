package com.vortest;

import com.google.inject.Inject;
import org.junit.Assert;


/**
 * Created by Brian on 12/21/16.
 * The ServiceObject is an abstract super class designed to be implemented once per service endpoint.
 * Each class that extends this must at a minimum define the URI, and may include a ResponseObject for deserialization
 */
public abstract class ServiceObject implements BuildableService{
    private ResponseObject responseObject = new ResponseObject();
    private ServiceExecutor serviceExecutor = new ServiceExecutor(this);
    protected ServiceBuilder serviceBuilder = new ServiceBuilder();

    @Inject
    protected ContextObject contextObject;

    public ServiceObject(){
    }

    /**
     * Executes a method call and returns a deserialized response object
     * @param method the REST method to call ("get", "post", etc.).
     * @param <E> the ResponseObject type that will be returned from the service call.
     * @return the response of the service call.
     */
    protected  <E extends ResponseObject> E call(String method){
        responseObject = serviceExecutor.execute(method,responseObject);

        return (E) responseObject;
    }

    /**
     * Executes a HTTP Post request on this ServiceObject
     * @param <E> The ResponseObject type
     * @return The deserialized ResponseObject
     */
    public  <E extends ResponseObject> E post(){
       return call("POST");
    }

    /**
     * Executes a HTTP Get request on this ServiceObject
     * @param <E> The ResponseObject type
     * @return The deserialized ResponseObject
     */
    public <E extends ResponseObject> E get(){
        return call("GET");
    }


    /**
     * Syntactic sugar for retrying a service call. To be used in conjunction with get(validationFunction).
     * @return this ServiceObject to be used in a chained function call.
     */
    public ServiceRepeater retry() {
        return new ServiceRepeater();
    }


    /**
     * Executes a HTTP Delete request on this ServiceObject
     * @param <E> The ResponseObject type
     * @return The deserialized ResponseObject
     */
    public <E extends ResponseObject> E delete(){
        return call("DELETE");
    }

    /**
     * Executes a HTTP Put request on this ServiceObject
     * @param <E> The ResponseObject type
     * @return The deserialized ResponseObject
     */
    public <E extends ResponseObject> E put() {
        return call("PUT");
    }

    /**
     * Executes a HTTP Patch request on this ServiceObject
     * @param <E> The ResponseObject type
     * @return The deserialized ResponseObject
     */
    public <E extends ResponseObject> E patch() {
        return call("patch");
    }

    /**
     * Gets the ResponseObject used for deserialization
     * @param <E> the response type for the ResponseObject.
     * @return the ResponseObject for this service request.
     */
    public <E extends ResponseObject> E getResponseObject(){

        if(this.responseObject == null){
            get();
        }
        return (E) this.responseObject;
    }

    /**
     * Sets the response type that will be deserialized into from the response from the service.
     * @param klass the class type that will be deserialized into.
     */
    public void setResponseType(Class klass)  {
        try {
            this.responseObject = (ResponseObject) klass.newInstance();
        }
        catch (Exception e){
            Assert.fail("Could not instantiate class " + klass.toString());
        }

    }

    /**
     * Gets the context object for the current service object.
     * @return the context object.
     */
    public ContextObject getContextObject(){
        return this.contextObject;
    }

    /**
     * Gets the service builder that will be used to set the configuration for the service call.
     * @return the service builder.
     */
    public ServiceBuilder getBuilder(){ return this.serviceBuilder; }

    /**
     * Gets the service executor that will be used to execute the service calls.
     * @return the service executor.
     */
    public ServiceExecutor getServiceExecutor(){ return this.serviceExecutor; }

}
