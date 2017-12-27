package com.vortest;

import io.restassured.response.Response;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by Brian on 12/21/16.
 *
 * The ResponseObject contains the RestAssured response.
 * Can be extended for automatic deserizliation.
 *
 * See SampleResponse for an example
 */
public class ResponseObject {

    /**
     * The RestAssured Response.
     */
    @XmlTransient
    Response response;

    /**
     * Sets the RestAssured response
     * @param response the RestAssured response that is set when the service object is executed.
     */
    public void setResponse(Response response){
        this.response = response;
    }

    /**
     * Gets the RestAssured Response
     * @return the RestAssured response.
     */
    @XmlTransient
    public Response getResponse(){
        return this.response;
    }

    /**
     * Gets the status code from the service response.
     * @return the service's response status code.
     */
    @XmlTransient
    public int getStatusCode(){
        return getResponse().getStatusCode();
    }

    public <T> T path(String jsonPath){
        return getResponse().path(jsonPath);
    }

    public String getBody(){
        return getResponse().getBody().asString();
    }
}
