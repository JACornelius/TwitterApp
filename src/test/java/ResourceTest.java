import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitterapp.src.Timeline;
import twitterapp.src.TwitterAppResource;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Time;
import java.util.List;

public class ResourceTest {
    TwitterAppResource resource;

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
        String longTweet = StringUtils.repeat(".",281);
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
        List<Status> mockListStatus = mock(List.class);
        ResponseList<Status> mockResponseList = mock(ResponseList.class);
        ResponseList<Status> mockTestResponseList = mock(ResponseList.class);
        try{
            when(mockTwitter.getHomeTimeline()).thenReturn(mockResponseList);
        }
        catch (Exception e){

        }
        r = resource.getTimeline();
        when(r.getEntity()).thenReturn(mockTestResponseList);
        assertTrue(mockTestResponseList.size() == mockResponseList.size());
        assertTrue(mockTestResponseList.get(0) == mockResponseList.get(0));
        assertTrue(r.getStatus() == 200);
        assertTrue(r.getMediaType() == MediaType.APPLICATION_JSON_TYPE);

    }

}

