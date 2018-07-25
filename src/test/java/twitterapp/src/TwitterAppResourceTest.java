package twitterapp.src;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitterapp.src.resources.TwitterAppResource;
import twitterapp.src.services.TwitterAppService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static twitterapp.src.resources.TwitterAppResource.MAX_LENGTH;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class TwitterAppResourceTest extends TwitterResponseList{
    TwitterAppResource resource;



    @Mock
    Twitter mockTwitter = mock(Twitter.class);
    TwitterAppService mockService = mock(TwitterAppService.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resource = new TwitterAppResource(mockTwitter);

        resource.setService(mockService);
    }

    @Test
    public void testTweetLength(){
        String shortTweet = "this is a short tweet jfdgdfgf";
        Status mockStatus = mock(Status.class);
        when(mockService.postTweet(shortTweet)).thenReturn(mockStatus);
        Response r = resource.postTweet(shortTweet);
        assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("Tweet("+shortTweet+") has been posted.", r.getEntity().toString());
    }

    @Test
    public void testFailTweet(){
        try {
            String tweet = "test tweet";
            when(mockTwitter.updateStatus(tweet)).thenThrow(new TwitterException("There was a problem on the server side, please try again later."));
            Response r = resource.postTweet(tweet);
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        }
        catch(Exception e){
            fail();
        }
    }

    @Test
    public void testLongTweet(){
        String longTweet = StringUtils.repeat(".", MAX_LENGTH + 5);
        Response r = resource.postTweet(longTweet);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("There was a problem on the server side, please try again later.", r.getEntity().toString());
    }

    @Test
    public void testEmptyTweet(){
        String emptyTweet = "";
        Response r = resource.postTweet(emptyTweet);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("There was a problem on the server side, please try again later.", r.getEntity());
    }

    @Test
    public void testEmptyTimeline(){
        Response r = resource.getTimeline();
        when(r.getEntity()).thenReturn(null);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("There was a problem on the server side, please try again later.", r.getEntity().toString());
    }

    @Test
    public void testTimelineReturnJSON() {
        ResponseList<Status> responseList = new TwitterResponseList<Status>();
        Status mockStatus = mock(Status.class);
        Status mockStatus1 = mock(Status.class);

        try {
            when(mockStatus.getText()).thenReturn("mockStatus");
            when(mockStatus1.getText()).thenReturn("mockStatus1");
            responseList.add(mockStatus);
            responseList.add(mockStatus1);
            when(mockTwitter.getHomeTimeline()).thenReturn(responseList);
            when(mockService.getTimeline()).thenReturn(responseList);
            Response r = resource.getTimeline();
            ResponseList<Status> newResponseList = (ResponseList<Status>) r.getEntity();
            assertEquals(2, newResponseList.size());
            assertEquals("mockStatus", responseList.get(0).getText());
            assertEquals("mockStatus1", responseList.get(1).getText());
            assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
            assertEquals( MediaType.APPLICATION_JSON_TYPE, r.getMediaType());
        }
        catch (Exception e) {
            fail("Timeline was not returned");
        }


    }

    @Test
    public void throwExceptionWhenTimelineDoesNotPrint() throws Exception{
           doThrow(new TwitterException("There was a problem on the server side, please try again later.")).when(mockTwitter).getHomeTimeline();
            Response r = resource.getTimeline();
          assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));

    }


}