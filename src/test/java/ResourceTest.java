import org.junit.Test;
import org.mockito.*;
import twitter4j.Status;
import twitter4j.Twitter;
import twitterapp.src.TwitterAppResource;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class ResourceTest extends TwitterAppResource{
    @Mock
    TwitterAppResource mockResource = mock(TwitterAppResource.class);
    Twitter mockTwitter = mock(Twitter.class);
    Status mockStatus = mock(Status.class);
    Response r = mock(Response.class);

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    //PROBLEM
    @Test
    //POST Rest Service
    public void testTweetLength(){
        String shortTweet = "this is a short tweet part 2";
        r = new TwitterAppResource().postTweet(shortTweet);
        assertTrue(r.getStatus() == 200);
    }

    @Test
    //POST Rest Service
    public void testLongTweet(){
        String longTweet = "This tweet is longer than the 280 limit...................................................................................................................................................................................................................................................";
        r = new TwitterAppResource().postTweet(longTweet);
        assertTrue(r.getStatus() == 500);
    }

    @Test
    public void testEmptyTweet(){
        String emptyTweet = "";
        r = new TwitterAppResource().postTweet(emptyTweet);
        assertTrue(r.getStatus() == 500);
    }

    //PROBLEM
    @Test
    public void testNonEmptyTimeline(){
        r = new TwitterAppResource().getTimeline();
        if(r.getEntity() == null) {
            assertTrue(r.getStatus() == 500);
        }
        else{
            assertTrue(r.getStatus() == 200);
        }
    }

    @Test
    public void testTimelineReturnJSON(){
        r = new TwitterAppResource().getTimeline();
        assertTrue(r.getMediaType() == MediaType.APPLICATION_JSON_TYPE);
    }
}
