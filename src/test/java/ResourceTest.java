import org.junit.Test;
import org.mockito.*;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitterapp.src.TwitterAppResource;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class ResourceTest extends TwitterAppResource{
    @Mock
    TwitterAppResource resource = new TwitterAppResource();
    TwitterFactory mockTwitterFactory = mock(TwitterFactory.class);
    Response r = mock(Response.class);

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTweetLength(){
        String shortTweet = "this is a short tweet part 3";
        resource.setTwitter(mockTwitterFactory);
        r = resource.postTweet(shortTweet);
        assertTrue(r.getStatus() == 200);
    }

    @Test
    public void testLongTweet(){
        String longTweet = "This tweet is longer than the 280 limit...................................................................................................................................................................................................................................................";
        resource.setTwitter(mockTwitterFactory);
        r = resource.postTweet(longTweet);

        assertTrue(r.getStatus() == 500);
    }

    @Test
    public void testEmptyTweet(){
        String emptyTweet = "";
        resource.setTwitter(mockTwitterFactory);
        r = resource.postTweet(emptyTweet);
        assertTrue(r.getStatus() == 500);
    }

    //PROBLEM
    @Test
    public void testNonEmptyTimeline(){
        resource.setTwitter(mockTwitterFactory);
        r = resource.getTimeline();
        if(r.getEntity() == null) {
            assertTrue(r.getStatus() == 500);
        }
        else{
            assertTrue(r.getStatus() == 200);
        }
    }

    @Test
    public void testTimelineReturnJSON(){
        resource.setTwitter(mockTwitterFactory);
        r = resource.getTimeline();
        assertTrue(r.getMediaType() == MediaType.APPLICATION_JSON_TYPE);
    }

  }

