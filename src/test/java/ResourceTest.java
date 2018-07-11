import com.fasterxml.jackson.annotation.JsonRawValue;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitterapp.src.TwitterAppResource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;
import java.util.List;

public class ResourceTest extends TwitterAppResource{
    MockMvc mockMvc;

    @Mock
    TwitterAppResource mockResource = mock(TwitterAppResource.class);
    Twitter mockTwitter = mock(Twitter.class);
    Status mockStatus = mock(Status.class);
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    //@ClassRule
   //public static final ResourceTestRule RESOURCES = ResourceTestRule.builder().addResource(mock(TwitterAppResource.class)).build();

    @Test
    //POST Rest Service
    public void testTweetLength(){
        String shortTweet = "this is a short tweet";

        try{
        when(mockTwitter.updateStatus(shortTweet)).thenReturn(mockStatus);
        mockResource.postTweet(shortTweet);
        List<Status> statuses = mockTwitter.getUserTimeline();
        mockStatus = statuses.get(0);
        assertEquals(shortTweet, mockStatus.getText());
        }
        catch(Exception e){

        }
    }

    @Test
    //POST Rest Service
    public void testLongTweet(){
        String longTweet = "This tweet is longer than the 280 limit...................................................................................................................................................................................................................................................";
        try{
            when(mockTwitter.updateStatus(longTweet)).thenReturn(mockStatus);
            mockResource.postTweet(longTweet);
            List<Status> statuses = mockTwitter.getUserTimeline();
            mockStatus = statuses.get(0);
            assertFalse(longTweet.equals(mockStatus.getText()));
        }
        catch(Exception e){

        }
    }
}
