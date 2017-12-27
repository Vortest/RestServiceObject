package com.vortest;

import java.io.File;
import java.util.Map;

/**
 * A Parameter is used to store key value pairs to be used in a query string
 *
 * Created bymvn  Brian on 12/21/16.
 */
public class Parameter {
    private String key;
    private String value;

    public Parameter(String key, String value){
        this.key = key;
        this.value = value;
    }

    /**
     * Construct a parameter with the given key mapped to the json file that contains a template in the form
     * specified from the Utility.fillInJsonFileTemplate() function.
     * @param key the key to map the json string to.
     * @param jsonTemplate the json file that represents the template for values to fill in from valuesToAdd.
     * @param valuesToAdd the values to replace in the json template.
     */
    public Parameter(String key, File jsonTemplate, Map<String, String> valuesToAdd) {
        this.key = key;
        this.value = Utility.fillInJsonFileTemplate(jsonTemplate, valuesToAdd);
    }

    /**
     * Construct a parameter with the given key mapped to the json file path that contains a template in the form
     * specified from the Utility.fillInJsonFileTemplate() function.
     * @param key the key to map the json string to.
     * @param jsonTemplateFile the json file path that represents the template for values to fill in from valuesToAdd.
     * @param valuesToAdd the values to replace in the json template.
     */
    public Parameter(String key, String jsonTemplateFile, Map<String, String> valuesToAdd) {
        this.key = key;
        File file = new File(jsonTemplateFile);
        this.value = Utility.fillInJsonFileTemplate(file, valuesToAdd);
    }

    public String getKey(){
        return this.key;
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (key != null ? !key.equals(parameter.key) : parameter.key != null) return false;
        if (value != null ? !value.equals(parameter.value) : parameter.value != null) return false;

        return true;
    }
}
