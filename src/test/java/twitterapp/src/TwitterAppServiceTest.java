package twitterapp.src;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.Paging;
import twitter4j.StatusUpdate;
import twitterapp.src.exceptions.EmptyReplyTweetId;
import twitterapp.src.exceptions.EmptyTweetMsgException;
import twitterapp.src.exceptions.LongTweetException;
import twitterapp.src.exceptions.TwitterAppException;
import twitterapp.src.models.ReplyTweetRequestBody;
import twitterapp.src.models.RequestBody;
import twitterapp.src.models.TwitterPost;
import twitterapp.src.services.TwitterAppService;
import org.mockito.MockitoAnnotations;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static twitterapp.src.resources.TwitterAppResource.MAX_LENGTH;

public class TwitterAppServiceTest {
    TwitterAppService service;
    Optional<TwitterPost> twitterPost = Optional.empty();
    Optional<List<TwitterPost>> twitterPostList = Optional.empty();
    RequestBody requestBody = new RequestBody();
    ReplyTweetRequestBody replyTweetRequestBody = new ReplyTweetRequestBody();
    @Mock
    Twitter mockTwitter = mock(Twitter.class);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockTwitter = mock(Twitter.class);
        service = new TwitterAppService(mockTwitter);
    }

    @Test
    public void testGoodTweetPost() throws Exception{
        String tweet = "good tweet";
        Status mockStatus = mock(Status.class);
        User u = mock(User.class);
        Date date = new Date(2018,1,1);

        requestBody.setMessage(tweet);
        when(mockStatus.getText()).thenReturn(tweet);
        when(mockStatus.getUser()).thenReturn(u);
        when(mockStatus.getUser().getName()).thenReturn("mockUserName");
        when(mockStatus.getUser().getProfileImageURL()).thenReturn("mockProfileImgURL");
        when(mockStatus.getUser().getScreenName()).thenReturn("mockTwitterHandle");
        when(mockStatus.getCreatedAt()).thenReturn(date);
        when(mockTwitter.updateStatus(requestBody.getMessage())).thenReturn(mockStatus);

        service.postTweet(requestBody);
        assertEquals(tweet, service.postTweet(requestBody).get().getMessage());
    }

    @Test
    public void testGoodTweetReply() throws Exception{
        String tweet = "good tweet";
        long replyID = 23232323;
        Status mockStatus = mock(Status.class);
        User u = mock(User.class);
        Date date = new Date(2018,1,1);

        when(mockStatus.getText()).thenReturn(tweet);
        when(mockStatus.getUser()).thenReturn(u);
        when(mockStatus.getUser().getName()).thenReturn("mockUserName");
        when(mockStatus.getUser().getProfileImageURL()).thenReturn("mockProfileImgURL");
        when(mockStatus.getUser().getScreenName()).thenReturn("mockTwitterHandle");
        when(mockStatus.getCreatedAt()).thenReturn(date);
        when(mockTwitter.updateStatus(new StatusUpdate(tweet).inReplyToStatusId(replyID))).thenReturn(mockStatus);

        replyTweetRequestBody.setMessage(tweet);
        replyTweetRequestBody.setReplyTweetID(replyID);
        service.replyTweet(replyTweetRequestBody);
        assertEquals(tweet, service.replyTweet(replyTweetRequestBody).get().getMessage());
    }

    @Test(expected = TwitterAppException.class)
    public void testBadTweetInPostTweet() throws Exception {
        requestBody.setMessage("bad tweet");

        when(mockTwitter.updateStatus(requestBody.getMessage()))
                .thenThrow(new TwitterException("There was a problem on the server side, please try again later."));

        twitterPost = service.postTweet(requestBody);
        assertTrue(twitterPost == null);
    }

    @Test(expected = TwitterAppException.class)
    public void testBadTweetReply() throws Exception{
        String tweet = "bad tweet";
        long replyID = 23232323;
        Status mockStatus = mock(Status.class);
        User u = mock(User.class);

        replyTweetRequestBody.setMessage(tweet);
        replyTweetRequestBody.setReplyTweetID(replyID);
        when(mockStatus.getText()).thenReturn(tweet);
        when(mockStatus.getUser()).thenReturn(u);
        when(mockStatus.getUser().getName()).thenReturn("mockUserName");
        when(mockStatus.getUser().getProfileImageURL()).thenReturn("mockProfileImgURL");
        when(mockStatus.getUser().getScreenName()).thenReturn("mockTwitterHandle");
        Date date = new Date(2018,1,1);
        when(mockStatus.getCreatedAt()).thenReturn(date);
        when(mockTwitter.updateStatus(new StatusUpdate(tweet).inReplyToStatusId(replyID)))
                .thenThrow(new TwitterException("There was a problem on the server side, please try again later."));

        service.replyTweet(replyTweetRequestBody);
        assertEquals(tweet, service.replyTweet(replyTweetRequestBody).get().getMessage());
    }


    @Test(expected = EmptyTweetMsgException.class)
    public void testEmptyPostTweetMsgExceptionHandling() throws Exception {
        String emptyTweet = "";

        requestBody.setMessage(emptyTweet);

        twitterPost = service.postTweet(requestBody);
        assertTrue(twitterPost == null);
    }

    @Test(expected = EmptyTweetMsgException.class)
    public void testEmptyReplyMsgTweetExceptionHandling() throws Exception {
        String emptyTweet = "";

        replyTweetRequestBody.setMessage(emptyTweet);

        twitterPost = service.replyTweet(replyTweetRequestBody);
        assertTrue(twitterPost == null);
    }

    @Test(expected = EmptyReplyTweetId.class)
    public void testEmptyReplyTweetId() throws Exception {
        replyTweetRequestBody.setReplyTweetID(0);
        replyTweetRequestBody.setMessage("test reply message");
        twitterPost = service.replyTweet(replyTweetRequestBody);
        assertTrue(twitterPost == null);
    }

    @Test(expected = LongTweetException.class)
    public void testLongPostTweetMsgExceptionHandling() throws Exception {
        String longTweet = StringUtils.repeat("a", MAX_LENGTH + 3);

        requestBody.setMessage(longTweet);

        twitterPost = service.postTweet(requestBody);
        assertFalse(twitterPost.isPresent());
    }

    @Test(expected = LongTweetException.class)
    public void testLongReplyTweetMsgExceptionHandling() throws Exception {
        String longTweet = StringUtils.repeat("a", MAX_LENGTH + 3);

        replyTweetRequestBody.setMessage(longTweet);

        twitterPost = service.replyTweet(replyTweetRequestBody);
        assertFalse(twitterPost.isPresent());
    }

    @Test
    public void testHomeTimeline_happyPath() {
        ResponseList<Status> responseList = new TwitterResponseList<Status>();
        try {
            Date date = new Date(2018,1,1);
            for(int i = 0; i <2; i++) {
                Status mockStatus = mock(Status.class);
                User mockUser = mock(User.class);
                when(mockStatus.getText()).thenReturn("mockStatus"+i);
                when(mockStatus.getUser()).thenReturn(mockUser);
                when(mockStatus.getUser().getName()).thenReturn("mockUserName");
                when(mockStatus.getUser().getProfileImageURL()).thenReturn("mockProfileImgURL");
                when(mockStatus.getCreatedAt()).thenReturn(date);
                responseList.add(mockStatus);
            }

            when(mockTwitter.getHomeTimeline(new Paging(1,25))).thenReturn(responseList);

            twitterPostList = service.getHomeTimeline();
            assertEquals(2, twitterPostList.get().size());
            assertEquals(responseList.get(0).getText(), twitterPostList.get().get(0).getMessage());
            assertEquals(responseList.get(1).getText(), twitterPostList.get().get(1).getMessage());
            assertTrue(twitterPostList.isPresent());
        } catch (Exception e) {
            fail("Timeline was not returned");
        }
    }

    @Test
    public void testUserTimeline_happyPath() {
        ResponseList<Status> responseList = new TwitterResponseList<Status>();
        try {
            Date date = new Date(2018,1,1);
            for(int i = 0; i <2; i++) {
                Status mockStatus = mock(Status.class);
                User mockUser = mock(User.class);
                when(mockStatus.getText()).thenReturn("mockStatus"+i);
                when(mockStatus.getUser()).thenReturn(mockUser);
                when(mockStatus.getUser().getName()).thenReturn("mockUserName");
                when(mockStatus.getUser().getProfileImageURL()).thenReturn("mockProfileImgURL");
                when(mockStatus.getCreatedAt()).thenReturn(date);
                responseList.add(mockStatus);
            }
            when(mockTwitter.getUserTimeline(new Paging(1,25))).thenReturn(responseList);

            twitterPostList = service.getUserTimeline();
            assertEquals(2, twitterPostList.get().size());
            assertEquals(responseList.get(0).getText(), twitterPostList.get().get(0).getMessage());
            assertEquals(responseList.get(1).getText(), twitterPostList.get().get(1).getMessage());
            assertTrue(twitterPostList.isPresent());

        } catch (Exception e) {
            fail("Timeline was not returned");
        }
    }

    @Test
    public void testGoodFilter(){
        ResponseList<Status> responseList = new TwitterResponseList<>();
        try {
            Date date = new Date(2018,1,1);
            for(int i = 0; i <2; i++) {
                Status mockStatus = mock(Status.class);
                User mockUser = mock(User.class);
                when(mockStatus.getText()).thenReturn("mockStatus"+i);
                when(mockStatus.getUser()).thenReturn(mockUser);
                when(mockStatus.getUser().getName()).thenReturn("mockUserName");
                when(mockStatus.getUser().getProfileImageURL()).thenReturn("mockProfileImgURL");
                when(mockStatus.getCreatedAt()).thenReturn(date);
                responseList.add(mockStatus);
            }
            when(mockTwitter.getHomeTimeline()).thenReturn(responseList);

            twitterPostList = service.filterTweets("s");
            assertEquals(2, twitterPostList.get().size());
            assertEquals("mockStatus0", twitterPostList.get().get(0).getMessage());
            assertEquals("mockStatus1", twitterPostList.get().get(1).getMessage());
        } catch (Exception e) {
            fail();
        }
    }

    @Test(expected = TwitterAppException.class)
    public void testBadFilter() throws Exception{
        doThrow(new TwitterException("There was a problem on the server side."))
                .when(mockTwitter).getHomeTimeline();
        assertFalse(service.filterTweets("potato").isPresent());
    }

    @Test(expected = TwitterAppException.class)
    public void testHomeTimeline_badPath() throws Exception {
        doThrow(new TwitterException("There was a problem on the server side, please try again later."))
                .when(mockTwitter).getHomeTimeline();
        assertFalse(service.getHomeTimeline().isPresent());
    }

    @Test(expected = TwitterAppException.class)
    public void testUserTimeline_badPath() throws Exception {
        doThrow(new TwitterException("There was a problem on the server side, please try again later."))
                .when(mockTwitter).getHomeTimeline();
        assertFalse(service.getUserTimeline().isPresent());
    }
}