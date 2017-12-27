package service_objects;

import com.google.inject.Inject;
import com.vortest.ContextObject;
import com.vortest.ServiceObject;

/**
 * Created by Brian on 12/27/17.
 */
public class PostService extends ServiceObject {

    @Inject
    public PostService(PostsServiceObject postsServiceObject){
        PostResponseObject[] posts = postsServiceObject.getResponseObjects();

        this.serviceBuilder.setUri("https://jsonplaceholder.typicode.com/posts/" + posts[0].id);
    }


}
