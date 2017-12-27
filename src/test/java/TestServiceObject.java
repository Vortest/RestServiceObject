import com.vortest.ContextObject;
import org.junit.Test;
import service_objects.PostsServiceObject;
import service_objects.PostServiceObject;

/**
 * Created by Brian on 12/27/17.
 */
public class TestServiceObject {

    @Test
    public void TestGetServiceObject(){
        ContextObject contextObject = new ContextObject();

        PostServiceObject serviceObject = new PostServiceObject();
        serviceObject.get();

    }

    @Test
    public void TestGetServiceArray(){
        ContextObject contextObject = new ContextObject();

        PostsServiceObject serviceObject = new PostsServiceObject();
        serviceObject.get();

    }
}
