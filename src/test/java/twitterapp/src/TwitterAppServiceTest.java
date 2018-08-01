package twitterapp.src;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.mockito.Mock;
import twitter4j.*;
import twitterapp.src.exceptions.EmptyTweetException;
import twitterapp.src.exceptions.LongTweetException;
import twitterapp.src.exceptions.TwitterAppException;
import twitterapp.src.models.TwitterPost;
import twitterapp.src.services.TwitterAppService;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static twitterapp.src.resources.TwitterAppResource.MAX_LENGTH;

public class TwitterAppServiceTest {
    TwitterAppService service; 
    TwitterPost twitterPost = new TwitterPost(null, null, null, null, null);
    @Mock
    Twitter mockTwitter = mock(Twitter.class);
   
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = TwitterAppService.getService();
        mockTwitter = mock(Twitter.class);
        service.setTwitter(mockTwitter);
    }


    @Test
    public void testGoodTweet() throws Exception{
        String tweet = "good tweethjfgj";
        twitterPost.setMessage(tweet);

        Status s = mock(Status.class);
        when(mockTwitter.updateStatus(twitterPost.getMessage())).thenReturn(s);
        assertEquals(tweet, service.postTweet(twitterPost).getMessage());



    }


    @Test(expected = TwitterAppException.class)
    public void testBadTweetInPostTweet() throws Exception {
        Status s;
        twitterPost.setMessage("bad tweet");
        when(mockTwitter.updateStatus(twitterPost.getMessage())).thenThrow(new TwitterException("There was a problem on the server side, please try again later."));
        twitterPost = service.postTweet(twitterPost);
        assertTrue(twitterPost == null);

    }


    @Test(expected = EmptyTweetException.class)
    public void testEmptyTweetExceptionHandling() throws Exception {
        String emptyTweet = "";
        twitterPost.setMessage(emptyTweet);
        twitterPost = service.postTweet(twitterPost);
        assertTrue(twitterPost == null);


    }

    @Test(expected = LongTweetException.class)
    public void testLongTweetExceptionHandling() throws Exception {
        String longTweet = StringUtils.repeat("a", MAX_LENGTH + 3);
        twitterPost.setMessage(longTweet);
        twitterPost = service.postTweet(twitterPost);
        assertTrue(twitterPost == null);


    }

    @Test
    public void testGoodTimeline() {
        ResponseList<Status> responseList = new TwitterResponseList<Status>();
        Status mockStatus = mock(Status.class);
        Status mockStatus1 = mock(Status.class);
        User mockUser = mock(User.class);
        User mockUser1 = mock(User.class);
        try {
            Date date = new Date(2018,1,1);
            when(mockStatus.getText()).thenReturn("mockStatus");
            when(mockStatus1.getText()).thenReturn("mockStatus1");
            when(mockStatus.getUser()).thenReturn(mockUser);
            when(mockStatus1.getUser()).thenReturn(mockUser1);
            when(mockStatus.getUser().getName()).thenReturn("slkdjf");
            when(mockStatus1.getUser().getName()).thenReturn("lsdjflksdjf");
            when(mockStatus.getUser().getProfileImageURL()).thenReturn("lsjdflsdkfj");
            when(mockStatus1.getUser().getProfileImageURL()).thenReturn("lsdjflsdkjf");
            when(mockStatus.getUser().getScreenName()).thenReturn("lskdjflsdkjf");
            when(mockStatus1.getUser().getScreenName()).thenReturn("sljflskjdfl");
            when(mockStatus.getCreatedAt()).thenReturn(date);
            when(mockStatus1.getCreatedAt()).thenReturn(date);
            responseList.add(mockStatus);
            responseList.add(mockStatus1);
            when(mockTwitter.getHomeTimeline()).thenReturn(responseList);
           List<TwitterPost> twitterPostList = service.getTimeline();
          assertEquals(2, twitterPostList.size());
           assertEquals(responseList.get(0).getText(), twitterPostList.get(0).getMessage());
           assertEquals(responseList.get(1).getText(), twitterPostList.get(1).getMessage());
           assertNotNull(service.getTimeline());

        } catch (Exception e) {
           fail("Timeline was not returned");
        }
    }

    @Test
    public void testGoodFilter(){

        ResponseList<Status> responseList = new TwitterResponseList<>();
        ResponseList<Status> filteredResponseList = new TwitterResponseList<Status>();
        Status mockStatus = mock(Status.class);
        Status mockStatus1 = mock(Status.class);
        Status mockStatus2 = mock(Status.class);
        User mockUser = mock(User.class);
        User mockUser1 = mock(User.class);
        try {
            Date date = new Date(2018,1,1);
            when(mockStatus.getText()).thenReturn("mockStatus");
            when(mockStatus1.getText()).thenReturn("mockStatus1");
            when(mockStatus2.getText()).thenReturn("lklkjjhkjhjf");

            when(mockStatus.getUser()).thenReturn(mockUser);
            when(mockStatus1.getUser()).thenReturn(mockUser1);
            when(mockStatus.getUser().getName()).thenReturn("slkdjf");
            when(mockStatus1.getUser().getName()).thenReturn("lsdjflksdjf");
            when(mockStatus.getUser().getProfileImageURL()).thenReturn("lsjdflsdkfj");
            when(mockStatus1.getUser().getProfileImageURL()).thenReturn("lsdjflsdkjf");
            when(mockStatus.getUser().getScreenName()).thenReturn("lskdjflsdkjf");
            when(mockStatus1.getUser().getScreenName()).thenReturn("sljflskjdfl");
            when(mockStatus.getCreatedAt()).thenReturn(date);
            when(mockStatus1.getCreatedAt()).thenReturn(date);
            responseList.add(mockStatus);
            responseList.add(mockStatus1);
            responseList.add(mockStatus2);
            when(mockTwitter.getHomeTimeline()).thenReturn(responseList);

            List<TwitterPost> twitterPostList = service.filterTweets("s");
            assertEquals(2, twitterPostList.size());
            assertEquals("mockStatus", twitterPostList.get(0).getMessage());
            assertEquals("mockStatus1", twitterPostList.get(1).getMessage());
        } catch (Exception e) {
            fail();
        }
    }
//
    @Test
    public void testBadFilter() throws Exception{
        doThrow(new TwitterException("There was a problem on the server side.")).when(mockTwitter).getHomeTimeline();
        assertTrue(service.filterTweets("potato").isEmpty());
    }

    @Test
    public void testBadTimeline() throws Exception {
        doThrow(new TwitterException("There was a problem on the server side, please try again later.")).when(mockTwitter).getHomeTimeline();
        assertTrue(service.getTimeline().isEmpty());
    }

}
