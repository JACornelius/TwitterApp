package twitterapp.src;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.*;
import twitterapp.src.TwitterAppResource;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResourceTest extends TwitterResponseList{
    TwitterAppResource resource;
    static final int MAX_COUNT = 280;

    @Mock
    Twitter mockTwitter = mock(Twitter.class);
    Response r = mock(Response.class);


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
        String longTweet = StringUtils.repeat(".",MAX_COUNT + 1);
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
        assertEquals(MediaType.APPLICATION_JSON_TYPE, r.getMediaType());
    }

    @Test
    public void testTimelineReturnJSON(){
        ResponseList<Status> responseList = new TwitterResponseList<Status>();
        Status mockStatus = mock(Status.class);
         try {
             when(mockStatus.getText()).thenReturn("mockStatus");
             responseList.add(mockStatus);
             when(mockTwitter.getHomeTimeline()).thenReturn(responseList);
             r = resource.getTimeline();
             when(r.getEntity()).thenReturn(responseList);
             assertEquals(1,responseList.size());
             assertEquals("mockStatus", responseList.get(0).getText());
        }
        catch (Exception e){

        }

        assertEquals(200,r.getStatus());
        assertEquals( MediaType.APPLICATION_JSON_TYPE, r.getMediaType());

    }

}

