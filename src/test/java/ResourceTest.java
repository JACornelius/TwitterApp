import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import twitter4j.*;
import twitterapp.src.Timeline;
import twitterapp.src.TwitterAppResource;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.annotation.Nonnull;
import javax.swing.text.html.parser.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.*;

public class ResourceTest extends TwitterResponseList{
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
        ResponseList<Status> responseList = new TwitterResponseList<Status>();
        Status mockStatus = mock(Status.class);
         try {
             when(mockStatus.getText()).thenReturn("mockStatus");
             responseList.add(mockStatus);
             when(mockTwitter.getHomeTimeline()).thenReturn(responseList);
             r = resource.getTimeline();
             when(r.getEntity()).thenReturn(responseList);
             assertTrue(responseList.size() == 1);
             assertTrue(responseList.get(0).getText() == "mockStatus");
        }
        catch (Exception e){

        }

        assertTrue(r.getStatus() == 200);
        assertTrue(r.getMediaType() == MediaType.APPLICATION_JSON_TYPE);

    }

}

