package twitterapp.src;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static twitterapp.src.TwitterAppResource.MAX_LENGTH;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class ResourceTest extends TwitterResponseList{
    TwitterAppResource resource;
    TwitterAppResource noMockTwitterResource;
    Response r;


    @Mock
    Twitter mockTwitter = mock(Twitter.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resource = new TwitterAppResource(mockTwitter);
        noMockTwitterResource = new TwitterAppResource();
    }

    @Test
    public void testTweetLength(){
        String shortTweet = "this is a short tweet part 6";
        r = resource.postTweet(shortTweet);
        assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("Tweet("+shortTweet+") has been posted.",r.getEntity().toString());
    }

    @Test
    public void testFailTweet(){
        try {
            String tweet = "test tweet";
            when(mockTwitter.updateStatus(tweet)).thenThrow(new TwitterException("There was a problem on the server side, please try again later."));
            r = noMockTwitterResource.postTweet(tweet);
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        }
        catch(Exception e){
            fail();
        }
    }

    @Test
    public void testLongTweet(){
        String longTweet = StringUtils.repeat(".",MAX_LENGTH + 1);
        r = resource.postTweet(longTweet);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("Tweet is too long, keep it within 280 characters",r.getEntity().toString());
    }

    @Test
    public void testEmptyTweet(){
        String emptyTweet = "";
        r = resource.postTweet(emptyTweet);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("No tweet entered",r.getEntity());
    }

    @Test
    public void testEmptyTimeline(){
        r = resource.getTimeline();
        when(r.getEntity()).thenReturn(null);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("There was a problem on the server side, please try again later.",r.getEntity().toString());
    }

    @Test
    public void testTimelineReturnJSON() {
        ResponseList<Status> responseList = new TwitterResponseList<Status>();
        Status mockStatus = mock(Status.class);
        try {
            when(mockStatus.getText()).thenReturn("mockStatus");
            responseList.add(mockStatus);
            when(mockTwitter.getHomeTimeline()).thenReturn(responseList);
            r = resource.getTimeline();
            ResponseList<Status> newResponseList = (ResponseList<Status>) r.getEntity();
            assertEquals(1,newResponseList.size());
            assertEquals("mockStatus", responseList.get(0).getText());
        }
        catch (Exception e) {
            fail("Timeline was not returned");
        }

        assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals( MediaType.APPLICATION_JSON_TYPE, r.getMediaType());
    }

    @Test
    public void throwExceptionWhenTimelineDoesNotPrint() {
        ResponseList<Status> responseList = new TwitterResponseList<Status>();
        try{
            when(mockTwitter.getHomeTimeline()).thenThrow(new InternalServerErrorException(""));
            r = noMockTwitterResource.getTimeline();
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        }
        catch(Exception e){

        }



    }


}