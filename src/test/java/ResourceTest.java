import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import twitter4j.Twitter;
import twitterapp.src.TwitterAppResource;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResourceTest {
    TwitterAppResource resource;

    @Mock
    Twitter mockTwitter = Mockito.mock(Twitter.class);
    Response r = Mockito.mock(Response.class);


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resource = new TwitterAppResource(mockTwitter);
    }

    @Test
    public void testTweetLength(){
        String shortTweet = "this is a short tweet part 6";
        r = resource.postTweet(shortTweet);
        assertEquals(200, r.getStatus());
        assertEquals("Tweet("+shortTweet+") has been posted",r.getEntity().toString());
    }


    @Test
    public void testLongTweet(){
        String longTweet = "This tweet is longer than the 280 limit...................................................................................................................................................................................................................................................";
        r = resource.postTweet(longTweet);
        assertEquals(500, r.getStatus());
        assertEquals("Tweet is too long, keep it with in 280 characters",r.getEntity().toString());
    }

    @Test
    public void testEmptyTweet(){
        String emptyTweet = "";
        r = resource.postTweet(emptyTweet);
        assertEquals(500, r.getStatus());
        assertEquals("No tweet entered.",r.getEntity());
    }

    @Test
    public void testEmptyTimeline(){
        r = resource.getTimeline();
        when(r.getEntity()).thenReturn(null);
        assertEquals(500, r.getStatus());
        assertTrue(r.getMediaType() == MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void testTimelineReturnJSON(){
        r = resource.getTimeline();
        assertTrue(r.getMediaType() == MediaType.APPLICATION_JSON_TYPE);

    }

}

