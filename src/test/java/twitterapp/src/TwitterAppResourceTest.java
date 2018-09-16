package twitterapp.src;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitterapp.src.exceptions.EmptyReplyTweetId;
import twitterapp.src.exceptions.EmptyTweetMsgException;
import twitterapp.src.exceptions.LongTweetException;
import twitterapp.src.exceptions.TwitterAppException;
import twitterapp.src.models.PostTweetRequest;
import twitterapp.src.models.ReplyTweetRequest;
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

public class TwitterAppResourceTest extends TwitterResponseList {
    TwitterAppResource resource;
    TwitterPost twitterPost = new TwitterPost();
    Optional<TwitterPost> twitterPostOptional = Optional.of(twitterPost);
    List<TwitterPost> twitterPostList = new ArrayList<>();
    Optional<List<TwitterPost>> twitterPostListOptional = Optional.of(twitterPostList);

    @Mock
    TwitterAppService mockService = mock(TwitterAppService.class);
    PostTweetRequest postTweetRequest = mock(PostTweetRequest.class);
    ReplyTweetRequest replyTweetRequest = mock(ReplyTweetRequest.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        resource = new TwitterAppResource(mockService);
        resource.setService(mockService);
    }

    @Test
    public void testPostTweetMsgLength() throws Exception {
        String shortTweet = "this is a short tweet";
        PostTweetRequest postTweetRequest1 = new PostTweetRequest();
        postTweetRequest1.setMessage(shortTweet);
        postTweetRequest1.setName("jojo");
        twitterPostOptional.get().setMessage(shortTweet);
        twitterPostOptional.get().setUsername("jojo");
        twitterPostOptional.get().setStatusId("2");

        when(mockService.postTweet(isA(PostTweetRequest.class))).thenReturn(twitterPostOptional);

        Response r = resource.postTweet(postTweetRequest1);
        assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals(twitterPostOptional.get(), r.getEntity());
    }
    @Test
    public void testEmptyReplyTweetId() throws Exception {
        String shortTweet = "this is a short tweet";
        ReplyTweetRequest replyTweetRequest1 = new ReplyTweetRequest();
        replyTweetRequest1.setMessage(shortTweet);
        replyTweetRequest1.setReplyTweetID(0);
        twitterPostOptional.get().setMessage(shortTweet);

        when(mockService.replyTweet(isA(ReplyTweetRequest.class)))
                .thenThrow(new EmptyReplyTweetId("No reply TweetID was entered"));

        Response r = resource.replyTweet(replyTweetRequest1);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("No reply tweet ID was provided", r.getEntity().toString());
    }
    @Test
    public void testReplyTweetMsgLength() throws Exception {
        String shortTweet = "this is a short tweet";
        twitterPostOptional.get().setMessage(shortTweet);
        twitterPostOptional.get().setUsername("jojo");
        ReplyTweetRequest replyTweetRequest1 = new ReplyTweetRequest();
        replyTweetRequest1.setMessage(shortTweet);
        replyTweetRequest1.setName("jojo");
        replyTweetRequest1.setReplyTweetID(232323233);

        when(mockService.replyTweet(isA(ReplyTweetRequest.class))).thenReturn(twitterPostOptional);

        Response r = resource.replyTweet(replyTweetRequest1);
        assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals(twitterPostOptional.get(), r.getEntity());
    }

    @Test
    public void testFailPostTweet() throws Exception {
        String tweet = "test tweet";
        twitterPostOptional.get().setMessage(tweet);

        when(mockService.postTweet(isA(PostTweetRequest.class)))
                .thenThrow(new TwitterAppException("There was a problem on the server side, please try again later."));
        postTweetRequest.message = tweet;

        Response r = resource.postTweet(postTweetRequest);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
    }

    @Test
    public void testFailReplyTweet() throws Exception {
        String tweet = "test tweet";
        twitterPostOptional.get().setMessage(tweet);
        replyTweetRequest.message = tweet;

        when(mockService.replyTweet(isA(ReplyTweetRequest.class)))
                .thenThrow(new TwitterAppException("There was a problem on the server side, please try again later."));

        Response r = resource.replyTweet(replyTweetRequest);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
    }

    @Test
    public void testLongPostTweetMsg() throws Exception {
        String longTweet = StringUtils.repeat(".", MAX_LENGTH + 5);
        PostTweetRequest postTweetRequest1 = new PostTweetRequest();
        postTweetRequest1.setName("jojo");
        postTweetRequest1.setMessage(longTweet);

        when(mockService.postTweet(isA(PostTweetRequest.class))).thenThrow(new LongTweetException("The tweet needs to under 280 characters."));

        Response r = resource.postTweet(postTweetRequest1);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("The tweet needs to under 280 characters.", r.getEntity().toString());
    }

    @Test
    public void testReplyLongTweetMsg() throws Exception {
        String longTweet = StringUtils.repeat(".", MAX_LENGTH + 5);
        ReplyTweetRequest replyTweetRequest1 = new ReplyTweetRequest();
        replyTweetRequest1.setName("jojo");
        replyTweetRequest1.setMessage(longTweet);

        when(mockService.replyTweet(isA(ReplyTweetRequest.class)))
                .thenThrow(new LongTweetException("The tweet needs to under 280 characters."));

        Response r = resource.replyTweet(replyTweetRequest1);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("The tweet needs to under 280 characters.", r.getEntity().toString());
    }

    @Test
    public void testEmptyPostTweetMsg() throws Exception {
        String emptyTweet = "";
        twitterPostOptional.get().setMessage(emptyTweet);
        postTweetRequest.message = emptyTweet;

        when(mockService.postTweet(isA(PostTweetRequest.class))).thenThrow(new EmptyTweetMsgException("An empty tweet was entered"));

        Response r = resource.postTweet(postTweetRequest);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("No tweet was provided.", r.getEntity());
    }

    @Test
    public void testEmptyReplyTweetMsg() throws Exception {
        String emptyTweet = "";
        twitterPostOptional.get().setMessage(emptyTweet);
        replyTweetRequest.message = emptyTweet;

        when(mockService.replyTweet(isA(ReplyTweetRequest.class)))
                .thenThrow(new EmptyTweetMsgException("An empty tweet was entered"));

        Response r = resource.replyTweet(replyTweetRequest);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("No tweet was provided", r.getEntity());
    }

    @Test
    public void testEmptyHomeTimeline() throws Exception {
        when(mockService.getHomeTimeline()).thenThrow(new TwitterAppException("Unable to get timeline. There was a problem on the server side"));

        Response r = resource.getHomeTimeline();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("There was a problem on the server side, please try again later.", r.getEntity().toString());
    }

    @Test
    public void testEmptyUserTimeline() throws Exception {
        when(mockService.getUserTimeline()).thenThrow(new TwitterAppException("Unable to get timeline. There was a problem on the server side"));

        Response r = resource.getUserTimeline();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals("There was a problem on the server side, please try again later.", r.getEntity().toString());
    }

    @Test
    public void testHomeTimelineReturnJSON() {
        try {
            TwitterPost twitterPost = new TwitterPost("twitterPost", null, null, null, null, "0");
            TwitterPost twitterPost1 = new TwitterPost("twitterPost1", null, null, null, null, "0");
            twitterPostListOptional.get().add(twitterPost);
            twitterPostListOptional.get().add(twitterPost1);

            when(mockService.getHomeTimeline()).thenReturn(twitterPostListOptional);

            Response r = resource.getHomeTimeline();
            List<TwitterPost> newTwitterPostList = (List<TwitterPost>) r.getEntity();
            assertNotNull(newTwitterPostList);
            assertEquals(2, newTwitterPostList.size());
            assertEquals("twitterPost", newTwitterPostList.get(0).getMessage());
            assertEquals("twitterPost1", newTwitterPostList.get(1).getMessage());
            assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
            assertEquals(MediaType.APPLICATION_JSON_TYPE, r.getMediaType());
        } catch (Exception e) {
            fail("Timeline was not returned");
        }
    }

    @Test
    public void testUserTimelineReturnJSON() {
        try {
            TwitterPost twitterPost = new TwitterPost("twitterPost", null, null, null, null, "0");
            TwitterPost twitterPost1 = new TwitterPost("twitterPost1", null, null, null, null, "0");
            twitterPostListOptional.get().add(twitterPost);
            twitterPostListOptional.get().add(twitterPost1);

            when(mockService.getUserTimeline()).thenReturn(twitterPostListOptional);

            Response r = resource.getUserTimeline();
            List<TwitterPost> newTwitterPostList = (List<TwitterPost>) r.getEntity();
            assertNotNull(newTwitterPostList);
            assertEquals(2, newTwitterPostList.size());
            assertEquals("twitterPost", newTwitterPostList.get(0).getMessage());
            assertEquals("twitterPost1", newTwitterPostList.get(1).getMessage());
            assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
            assertEquals(MediaType.APPLICATION_JSON_TYPE, r.getMediaType());
        } catch (Exception e) {
            fail("Timeline was not returned");
        }
    }

    @Test
    public void testFilter() throws Exception {
        TwitterPost twitterPost = new TwitterPost("twitterPost", null, null, null, null, "0");
        TwitterPost twitterPost1 = new TwitterPost("twitterPost1", null, null, null, null, "0");
        twitterPostListOptional.get().add(twitterPost);
        twitterPostListOptional.get().add(twitterPost1);

        when(mockService.filterTweets("twitterPost")).thenReturn(twitterPostListOptional);

        Response r = resource.filterTweets("twitterPost");
        List<TwitterPost> result = (List<TwitterPost>) r.getEntity();
        assertEquals(2, result.size());
        assertEquals("twitterPost", result.get(0).getMessage());
        assertEquals("twitterPost1", result.get(1).getMessage());
        assertEquals(Response.Status.OK, Response.Status.fromStatusCode(r.getStatus()));
        assertEquals(MediaType.APPLICATION_JSON_TYPE, r.getMediaType());
        assertFalse(result.isEmpty());
    }

    @Test
    public void testBadFilter() throws Exception {
        doThrow(new TwitterAppException("There was a problem on the server side, please try again later.")).when(mockService).filterTweets("potato");

        Response r = resource.filterTweets("potato");
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
    }

    @Test
    public void throwExceptionWhenHomeTimelineDoesNotPrint() throws Exception {
        doThrow(new TwitterAppException("There was a problem on the server side, please try again later.")).when(mockService).getHomeTimeline();

        Response r = resource.getHomeTimeline();
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, Response.Status.fromStatusCode(r.getStatus()));
    }
}