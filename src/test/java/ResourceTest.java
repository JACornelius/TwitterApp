import org.junit.Test;
import org.mockito.*;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;

import twitterapp.src.TwitterAppResource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ResourceTest extends TwitterAppResource{

    @Mock
    TwitterAppResource mockResource = mock(TwitterAppResource.class);
    Twitter mockTwitter = mock(Twitter.class);
    Status mockStatus = mock(Status.class);
    Response r = mock(Response.class);

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    //POST Rest Service
    public void testTweetLength(){
        String shortTweet = "this is a short tweet";
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

    @Test
    public void testNonEmptyTimeline(){
        r = new TwitterAppResource().getTimeline();
        assertTrue(r.getStatus() == 500);
    }
}
