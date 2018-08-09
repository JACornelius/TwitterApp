package twitterapp.src;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.Twitter;
import twitterapp.src.exceptions.EmptyTweetException;
import twitterapp.src.exceptions.LongTweetException;
import twitterapp.src.exceptions.TwitterAppException;
import twitterapp.src.models.RequestBody;
import twitterapp.src.models.TwitterPost;
import twitterapp.src.resources.TwitterAppResource;
import twitterapp.src.services.TwitterAppService;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static twitterapp.src.resources.TwitterAppResource.MAX_LENGTH;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TwitterAppResourceTest extends TwitterResponseList{
    TwitterAppResource resource;
    TwitterPost twitterPost = new TwitterPost();
    Optional<TwitterPost> twitterPostOptional = Optional.of(twitterPost);
    List<TwitterPost> twitterPostList = new ArrayList<>();
    Optional<List<TwitterPost>> twitterPostListOptional = Optional.of(twitterPostList);

    @Mock
    TwitterAppService mockService = mock(TwitterAppService.class);
    RequestBody requestBody = mock(RequestBody.class);
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resource = new TwitterAppResource( mockService);
        resource.setService(mockService);
    }


    @Test
    public void testTweetLength() throws Exception{

        String shortTweet = "this is a short tweet jfgf";

        twitterPostOptional.get().setMessage(shortTweet);
        twitterPostOptional.get().setUsername("jojo");
        when(mockService.postTweet(isA(RequestBody.class))).thenReturn(twitterPostOptional);

        RequestBody requestBody1 = new RequestBody();
        requestBody1.setMessage(shortTweet);
        requestBody1.setName("jojo");
        Response r = resource.postTweet(requestBody1);
        assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals(twitterPostOptional.get(), r.getEntity());
    }


    @Test
    public void testFailTweet() throws Exception{

            String tweet = "test tweet";
            twitterPostOptional.get().setMessage(tweet);
            when(mockService.postTweet(isA(RequestBody.class))).thenThrow(new TwitterAppException("There was a problem on the server side, please try again later."));
            requestBody.message = tweet;
        Response r = resource.postTweet(requestBody);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
    }
    @Test
    public void testLongTweet() throws Exception{
        String longTweet = StringUtils.repeat(".", MAX_LENGTH + 5);
        when(mockService.postTweet(isA(RequestBody.class))).thenThrow(new LongTweetException("The tweet needs to under 280 characters."));
        RequestBody requestBody1 = new RequestBody();
        requestBody1.setName("jojo");
        requestBody1.setMessage(longTweet);
        Response r = resource.postTweet(requestBody1);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("The tweet needs to under 280 characters.", r.getEntity().toString());

    }

    @Test
    public void testEmptyTweet() throws Exception{
        String emptyTweet = "";
        twitterPostOptional.get().setMessage(emptyTweet);
        when(mockService.postTweet(isA(RequestBody.class))).thenThrow(new EmptyTweetException("An empty tweet was entered"));
        requestBody.message = emptyTweet;
        Response r = resource.postTweet(requestBody);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("The tweet is empty.", r.getEntity());
    }



    @Test
    public void testEmptyTimeline() throws Exception{
        when(mockService.getTimeline()).thenThrow(new TwitterAppException("Unable to get timeline. There was a problem on the server side"));
        Response r = resource.getTimeline();
        when(r.getEntity()).thenReturn(null);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("There was a problem on the server side, please try again later.", r.getEntity().toString());
    }



    @Test
    public void testTimelineReturnJSON() {



        try {
            TwitterPost twitterPost = new TwitterPost("twitterPost", null, null, null, null);
            TwitterPost twitterPost1 = new TwitterPost("twitterPost1", null, null, null, null);

           twitterPostListOptional.get().add(twitterPost);
            twitterPostListOptional.get().add(twitterPost1);
            when(mockService.getTimeline()).thenReturn(twitterPostListOptional);
           Response r = resource.getTimeline();
           List<TwitterPost> newTwitterPostList = (List<TwitterPost>) r.getEntity();
            assertNotNull(newTwitterPostList);
            assertEquals(2, newTwitterPostList.size());
            assertEquals("twitterPost", newTwitterPostList.get(0).getMessage());
            assertEquals("twitterPost1", newTwitterPostList.get(1).getMessage());

            assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
            assertEquals( MediaType.APPLICATION_JSON_TYPE, r.getMediaType());
        }
        catch (Exception e) {
            fail("Timeline was not returned");
        }


    }

    @Test
    public void testFilter() throws Exception{
        TwitterPost twitterPost = new TwitterPost("twitterPost", null, null, null, null);
        TwitterPost twitterPost1 = new TwitterPost("twitterPost1", null, null, null, null);
        twitterPostListOptional.get().add(twitterPost);
        twitterPostListOptional.get().add(twitterPost1);
        when(mockService.filterTweets("twitterPost")).thenReturn(twitterPostListOptional);
        Response r = resource.filterTweets("twitterPost");
        List<TwitterPost> result = (List<TwitterPost>) r.getEntity();
        assertEquals(2, result.size());
        assertEquals("twitterPost", result.get(0).getMessage());
        assertEquals("twitterPost1", result.get(1).getMessage());
        assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals( MediaType.APPLICATION_JSON_TYPE, r.getMediaType());
        assertFalse(result.isEmpty());
    }

    @Test
    public void testBadFilter() throws Exception{
        doThrow(new TwitterAppException("There was a problem on the server side, please try again later.")).when(mockService).filterTweets("potato");
        Response r = resource.filterTweets("potato");
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
    }
    @Test
    public void throwExceptionWhenTimelineDoesNotPrint() throws Exception{

        doThrow(new TwitterAppException("There was a problem on the server side, please try again later.")).when(mockService).getTimeline();
            Response r = resource.getTimeline();
          assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));

    }



}