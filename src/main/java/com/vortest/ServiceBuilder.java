package com.vortest;//import Parameter;
//import Utility;
import com.atlassian.oai.validator.restassured.SwaggerValidationFilter;
import com.google.gson.JsonObject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.methods.RequestBuilder;
import org.junit.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Brian on 12/21/16.
 * The ServiceBuilder class takes a ServiceObject, and builds a RestAssured RequestSpecification
 */
public class ServiceBuilder {
    private String endpoint;
    private RequestBuilder requestBuilder;
    private ContextObject contextObject;
    private RequestSpecification requestSpecification;
    protected String uri;
    protected ArrayList<Header> headers = new ArrayList<>();
    protected String body = "";
    protected ResponseObject bodyObject;
    protected ArrayList<Parameter> parameters = new ArrayList<>();
    protected boolean isXML = false;
    protected boolean isSchemaValidating = false;
    protected String schemaPath = "";
    protected ObjectMapperType mapperType = ObjectMapperType.GSON;

    public ServiceBuilder(){
        buildRequestSpec();
    }

    /**
     * Build Request specification.
     */
    private void buildRequestSpec() {
        requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.URLENC)
                .build();
    }

    /**
     * This is where the magic happens.
     * Call this method to get ready to call.
     */
    public void build(){
        buildUri();
        buildBody();
        buildHeaders();
        buildQueryParameters();
        buildSchemaValidation();

        requestSpecification.log().everything(true);
    }

    /**
     * Gets the RestAssured RequestSpecification
     * @return the RestAssured request specification that can be used for custom configurations.
     */
    public RequestSpecification getRequestSpecification(){
        return this.requestSpecification;
    }


    /**
     * Adds URI info onto the RequestSpecification.
     * If the URI has not been set an Error will display.
     */
    private void buildUri(){
        Assert.assertNotNull("ServiceObjects must define the URI before being executed.  " +
                "Please call this.serviceBuilder.setUri(uri) from within the ServiceObject", getUri());
        requestSpecification.baseUri(getUri());
    }

    /**
     * Adds request body into the RequestSpecification
     */
    private void buildBody(){
        if (bodyObject == null) {
            requestSpecification.body(getBody());
        }
        else {
            requestSpecification.body(getBody(), mapperType);
        }
    }

    /**
     * Adds Headers into the RequestSpecification
     */
    private void buildHeaders(){
        for (Header header: getHeaders()) {
            ((FilterableRequestSpecification) requestSpecification).removeHeader(header.getName());
            requestSpecification.header(header.getName(),header.getValue());
        }
    }

    /**
     * Adds Query Params into the RequestSpecification
     */
    private void buildQueryParameters(){
        for (Parameter param: getParameters()) {
            ((FilterableRequestSpecification) requestSpecification).removeQueryParam(param.getKey());
            requestSpecification.queryParam(param.getKey(),param.getValue());
        }
    }

    /**
     * Adds the schema validation filter to the request in order to validate the request and response based
     * on the swagger doc given.
     */
    private void buildSchemaValidation() {
        //schema validation on and the request does not use xml (the filter does not support xml)
        if (isSchemaValidating && !isXML) {
            SwaggerValidationFilter validationFilter = new SwaggerValidationFilter(schemaPath);
            requestSpecification.filter(validationFilter);
        }
    }

    /**
     * Sets the Request Body.
     * @param body String containing the Request Body info
     */
    public void setBody(String body){
        this.body = body;
    }

    /**
     * Sets the body object to serialize
     * @param body the object to serialize
     */
    public void setBody(ResponseObject body) {
        this.bodyObject = body;
    }

    /**
     * Set the body to a string that contains the json object from the json template file path as a string
     * with the values added into the template.
     * @param jsonFilePath the json template file path to add to the body.
     * @param valuesToAdd the values withe key value pair specified in the Utility.fillInJsonFileTemplate() function.
     */
    public void setBodyFromJSONFilePath(String jsonFilePath, Map<String, String> valuesToAdd) {
        File file = new File(jsonFilePath);
        this.body = Utility.fillInJsonFileTemplate(file, valuesToAdd);
    }

    /**
     * Set the body to a string that contains the json object from the json template file as a string
     * with the values added into the template.
     * @param jsonFile the json template file to add to the body.
     * @param valuesToAdd the values withe key value pair specified in the Utility.fillInJsonFileTemplate() function.
     */
    public void setBodyFromJSONFile(File jsonFile, Map<String, String> valuesToAdd) {
        this.body = Utility.fillInJsonFileTemplate(jsonFile, valuesToAdd);
    }

    /**
     * Sets the body from a json object.
     * @param jsonObject the json object to set the body to.
     */
    public void setBody(JsonObject jsonObject) {
        this.body = jsonObject.toString();
    }

    /**
     * Sets the Request Headers to the supplied array
     * @param headers an ArrayList of the request Headers
     */
    public void setHeaders(ArrayList<Header> headers){
        this.headers = headers;
    }

    /**
     * Adds one header to the list of Request Headers.
     * @param header the header to add into the request.
     */
    public void addHeader(Header header){
        if(this.headers==null){
            this.headers = new ArrayList<>();
        }
        this.headers.add(header);
    }

    /**
     * Sets the Request Parameters to the supplied Paramater. Note this will replace all previously set parameters.
     * @param parameter the parameter to add into the request and replace all other parameters previously set.
     */
    public void setParameter(Parameter parameter){
        this.parameters = new ArrayList<>();
        this.parameters.add(parameter);
    }

    /**
     * Sets the Parameters to the supplied Parameters. Note this will replace all previously set parameters.
     * @param parameters the parameters to set for the request.
     */
    public void setParameters(ArrayList<Parameter> parameters){
        this.parameters = parameters;
    }

    /**
     * Add a parameter that is set to a string that contains the json object from the json template
     * file path as a string with the values added into the template.
     * @param key the key to associate with the value for the parameter
     * @param jsonFilePathforValue the json template file path to add to the body.
     * @param valuesToAdd the values withe key value pair specified in the Utility.fillInJsonFileTemplate() function.
     */
    public void addParameterFromJSONFilePath(String key, String jsonFilePathforValue, Map<String, String> valuesToAdd) {
        Parameter param = new Parameter(key, jsonFilePathforValue, valuesToAdd);
        addParameter(param);
    }

    /**
     * Resets the Parameters for subsequent request calls.
     */
    public void clearParameters(){
        this.parameters = new ArrayList<Parameter>();
    }

    /**
     * Adds a parameter with the given key and json object as the value.
     * @param key the key for the parameter.
     * @param jsonObjectForValue the value for the parameter in the form of a JsonObject.
     */
    public void addParameter(String key, JsonObject jsonObjectForValue) {
        Parameter parameter = new Parameter(key, jsonObjectForValue.toString());
        addParameter(parameter);
    }

    /**
     * Adds a single Parameter to the list of Parameters.
     * Note: this will not replace the other parameters previously set.
     * @param parameter the parameter to add to the request.
     */
    public void addParameter(Parameter parameter){
        if(this.parameters ==null){
            this.parameters = new ArrayList<>();
        }
        this.parameters.add(parameter);
    }

    /**
     * Sets the Request URI, including domain and endpoint.
     * Required before execution can commence.
     * @param uri the uri for the service request.
     */
    public void setUri(String uri){
        this.uri = uri;
    }

    /**
     * Gets the Request URI.
     * @return the uri for the service request.
     */
    public String getUri(){
        return this.uri;
    }

    /**
     * Gets the List of RequestHeaders to be used on the next request.
     * @return the headers that will be used for the request.
     */
    public ArrayList<Header> getHeaders(){
        return this.headers;
    }

    /**
     * Gets the Request Body String or ResponseObject
     * @return the body of the request in the form of a string or ResponseObject.
     */
    public Object getBody() {
        if (bodyObject ==  null) {
            return this.body;
        }
        else {
            return bodyObject;
        }
    }

    /**
     * Gets the List of Request Parameters.
     * @return the list of parameters for this request.
     */
    public ArrayList<Parameter> getParameters(){
        return this.parameters;
    }

    /**
     * Sets this service object to serialze and deserialize using XML instead of JSON.
     */
    public void setToXMLSerialization() {
        isXML = true;
        mapperType = ObjectMapperType.JAXB;
    }

    /**
     * Sets this service object to validate the schema of the request and the response and sets the path to the
     * schema validation. Can be either a file path to a json file or http endpoint.
     * @param path the file path or http endpoint for the json swagger doc.
     */
    public void setToSwaggerSchemaValidation(String path) {
        isSchemaValidating = true;
        schemaPath = path;
    }

    /**
     * Gets the Object Mapper type for this service. Can either be GSON (JSON) or JAXB (XML)
     * @return the Object Mapper type for this service.
     */
    public ObjectMapperType getObjectMapperType() {
        return mapperType;
    }
}
