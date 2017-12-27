package service_objects;

import com.google.inject.Inject;
import com.vortest.ContextObject;
import com.vortest.ServiceArrayObject;
import com.vortest.ServiceObject;

import java.util.List;

/**
 * Created by Brian on 12/27/17.
 */
public class PostsServiceObject extends ServiceArrayObject {

    @Inject
    public PostsServiceObject(){

        this.serviceBuilder.setUri("https://jsonplaceholder.typicode.com/posts");
        this.setResponseType(PostResponseObject.class);

    }

}
