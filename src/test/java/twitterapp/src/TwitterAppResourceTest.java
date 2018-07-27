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
import twitterapp.src.exceptions.EmptyTweetException;
import twitterapp.src.exceptions.LongTweetException;
import twitterapp.src.exceptions.TwitterAppException;
import twitterapp.src.resources.TwitterAppResource;
import twitterapp.src.services.TwitterAppService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static twitterapp.src.resources.TwitterAppResource.MAX_LENGTH;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;


public class TwitterAppResourceTest extends TwitterResponseList{
    TwitterAppResource resource;



    @Mock
    Twitter mockTwitter = mock(Twitter.class);
    TwitterAppService mockService = mock(TwitterAppService.class);
    UriInfo mockUriInfo = mock(UriInfo.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resource = new TwitterAppResource(mockTwitter);

        resource.setService(mockService);
    }

   /* @Test
    public void testTweetLength() throws Exception{
        String shortTweet = "this is a short tweet jfgf";
        Status mockStatus = mock(Status.class);
        when(mockService.postTweet(shortTweet)).thenReturn(mockStatus);
        when(mockUriInfo.getAbsolutePath()).thenReturn(URI.create("http://localhost:8080/api/1.0/twitter/tweet?name=jojo"));
        Response r = resource.postTweet(mockUriInfo, shortTweet);
        assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("Tweet("+mockStatus.getText()+") has been posted.", r.getEntity().toString());
    }

    @Test
    public void testFailTweet() throws Exception{

            String tweet = "test tweet";
            when(mockTwitter.updateStatus(tweet)).thenThrow(new TwitterException("There was a problem on the server side, please try again later."));
            when(mockService.postTweet(tweet)).thenThrow(new TwitterAppException("There was a problem on the server side, please try again later."));
            when(mockUriInfo.getAbsolutePath()).thenReturn(URI.create("http://localhost:8080/api/1.0/twitter/tweet?name=jojo"));
            Response r = resource.postTweet(mockUriInfo, tweet);
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));

    }

    @Test
    public void testLongTweet() throws Exception{
        String longTweet = StringUtils.repeat(".", MAX_LENGTH + 5);
        when(mockService.postTweet(longTweet)).thenThrow(new LongTweetException("Tweet is too long, keep it within 280 characters"));
        when(mockUriInfo.getAbsolutePath()).thenReturn(URI.create("http://localhost:8080/api/1.0/twitter/tweet?name=jojo"));
        Response r = resource.postTweet(mockUriInfo, longTweet);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("The tweet needs to under 280 characters.", r.getEntity().toString());
    }

    @Test
    public void testEmptyTweet() throws Exception{
        String emptyTweet = "";
        when(mockService.postTweet(emptyTweet)).thenThrow(new EmptyTweetException("An empty tweet was entered"));
        when(mockUriInfo.getAbsolutePath()).thenReturn(URI.create("http://localhost:8080/api/1.0/twitter/tweet?name=jojo"));
        Response r = resource.postTweet(mockUriInfo, emptyTweet);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("The tweet is empty.", r.getEntity());
    }
*/

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
            assertNotNull(newResponseList);
            assertFalse(newResponseList.isEmpty());
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