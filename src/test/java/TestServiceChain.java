import com.google.inject.Guice;
import com.google.inject.Inject;
import com.vortest.DependencyModule;
import org.junit.Test;
import service_objects.PostService;

/**
 * Created by Brian on 12/27/17.
 */
public class TestServiceChain {


    @Test
    public void TestChain(){
       PostService postService = Guice.createInjector().getInstance(PostService.class);
        postService.get();
    }
}
