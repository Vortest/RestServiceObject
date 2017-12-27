package com.vortest;

//import ScenarioManager;
//import Utility;
import com.google.gson.JsonSyntaxException;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.print.RequestPrinter;
import io.restassured.internal.print.ResponsePrinter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import org.junit.Assert;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.Instant;

import static io.restassured.RestAssured.given;


/**
 * Created by Brian on 12/21/16.
 * The ServiceExecutor takes a given ServiceObject,
 * builds it using the ServiceBuilder, executes it,
 * and deserializes the results
 */
public class ServiceExecutor {
    private BuildableService serviceObject;
    private PrintStream  printStream;
    private Instant startTime, endTime;

    /**
     * Creates a new ServiceExecutor for the given ServiceObject
     * @param serviceObject the service object that will be executed.
     */
    public ServiceExecutor(BuildableService serviceObject){
        this.serviceObject = serviceObject;
    }

    /**
     * Builds the service object, executes the call, and returns the response
     * @param method The method to call eg "get", "put", "post", "delete".
     * @return the RestAssured Response
     */
    public Response execute(String method) {

        serviceObject.getBuilder().build();
        logRequest(serviceObject.getBuilder(), method);
        Response response = given().spec(serviceObject.getBuilder().getRequestSpecification()).request(method);
        logResponse(response);
        this.serviceObject.getContextObject().set(Response.class.toString(), response);
        return response;
    }

    /**
     * Builds the service object, executes the specified method call, and deserializes the response into the supplied
     * ResponseObject
     * @param method The HTTP Verb to call eg "get" "put" "post" "delete"
     * @param responseObject The ResponseObject to deserialize into
     * @param <E> The ResponseObject type to deserialize into.
     * @return The deserialized ResponseObject
     */
    public <E extends ResponseObject> E execute(String method, ResponseObject responseObject) {
        serviceObject.getBuilder().build();

        Class<? extends ResponseObject> responseObjectClass = responseObject.getClass();

       // logRequest(serviceObject.getBuilder(), method);
        Response response = given().spec(serviceObject.getBuilder().getRequestSpecification()).request(method);
        logResponse(response);

        try{
            responseObject = response.then().extract().body().as(responseObjectClass, serviceObject.getBuilder().getObjectMapperType());
        }
        catch(JsonSyntaxException e){
            System.out.println(String.format("Could not deserialize the Response (%s) %s",responseObjectClass.toString(),e.getMessage()));
        }

        //no body content was received in the response
        if (responseObject == null) {
            try {
                responseObject = responseObjectClass.newInstance();
            } catch (Exception e) {
                Assert.fail(String.format("Could not instantiate response object class: %s", responseObjectClass.toString()));
            }
        }

        responseObject.setResponse(response);
        this.serviceObject.getContextObject().set(Response.class.toString(), response);
        this.serviceObject.getContextObject().set(responseObject);
        return (E) responseObject;
    }

    /**
     * Builds the service object, executes the specified method call, and deserializes the response into the supplied
     * ResponseObject[] array
     * @param method The HTTP Verb to call eg "get" "put" "post" "delete"
     * @param responseObjects The ResponseObject[] array to deserialize into
     * @param <E> The ResponseObject type to deserialize into.
     * @return The deserialized ResponseObject[] array
     */
    public <E extends ResponseObject> E[] execute(String method, ResponseObject[] responseObjects) {
        serviceObject.getBuilder().build();

        logRequest(serviceObject.getBuilder(), method);
        Response response = given().spec(serviceObject.getBuilder().getRequestSpecification()).request(method);

        Class<? extends ResponseObject[]> responseObjectClass = responseObjects.getClass();

        logResponse(response);
        try{
            responseObjects = response.then().extract().body().as(responseObjectClass, serviceObject.getBuilder().getObjectMapperType());
        }
        catch(JsonSyntaxException e){
            System.out.println(String.format("Could not deserialize the Response (%s) %s",responseObjectClass.toString(),e.getMessage()));
        }

        //no body content was received in the response
        if (responseObjects == null) {
            try {
                responseObjects = responseObjectClass.newInstance();
            } catch (Exception e) {
                Assert.fail(String.format("Could not instantiate response object class: %s", responseObjectClass.toString()));
            }
        }

        for (ResponseObject responseObject : responseObjects) {
            responseObject.setResponse(response);
        }

        this.serviceObject.getContextObject().set(Response.class.toString(), response);
        this.serviceObject.getContextObject().set(responseObjects);
        return (E[]) responseObjects;
    }

    public void logRequest(ServiceBuilder builder, String method){
        FilterableRequestSpecification requestSpecification = (FilterableRequestSpecification) builder.getRequestSpecification();
        RequestPrinter.print(requestSpecification, method, serviceObject.getBuilder().getUri(), LogDetail.ALL, new PrintStream(new FileOutputStream(FileDescriptor.out)), true);
        startTime = Instant.now();
    }

    public void logResponse(Response response){
     //   endTime = Instant.now();
        ResponsePrinter.print(response, response, new PrintStream(new FileOutputStream(FileDescriptor.out)), LogDetail.ALL,true);

        //print duration of request
       // Scenario scenario = (Scenario) serviceObject.getContextObject().get(Scenario.class);
//        System.out.println("Service request duration: " + Utility.getTimeDifference(startTime, endTime));
    }


}


