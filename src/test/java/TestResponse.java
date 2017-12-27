import com.google.inject.Inject;
import com.vortest.ContextObject;
import com.vortest.ResponseObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service_objects.PostServiceObject;
import service_objects.PostResponseObject;
import service_objects.PostsServiceObject;

/**
 * Created by Brian on 12/27/17.
 */
public class TestResponse {
    @Inject
    PostServiceObject serviceObject;

    @Before
    public void GetServiceObject(){
        ContextObject contextObject = new ContextObject();
        PostsServiceObject postsServiceObject = new PostsServiceObject();
        postsServiceObject.get();
        serviceObject = new PostServiceObject();
        serviceObject.get();

    }

    @Test
    public void TestDeserializeResponse(){
        serviceObject.setResponseType(PostResponseObject.class);
        serviceObject.get();
       PostResponseObject response = serviceObject.getResponseObject();

        Assert.assertEquals(1, response.id);
    }


    @Test
    public void TestResponseJsonPath(){
        ResponseObject response = serviceObject.getResponseObject();
        int id = response.path("id");
        Assert.assertEquals(1,id );
    }


    @Test
    public void TestNoResponseObject(){
        String response = serviceObject.getResponseObject().getBody();
        Assert.assertEquals("{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
                "  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
                "}",response);
    }
}
