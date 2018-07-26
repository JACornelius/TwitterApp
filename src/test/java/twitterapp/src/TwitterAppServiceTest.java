package twitterapp.src;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitterapp.src.services.TwitterAppService;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static twitterapp.src.resources.TwitterAppResource.MAX_LENGTH;

public class TwitterAppServiceTest {
    TwitterAppService service;
    Twitter mockTwitter;
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = TwitterAppService.getService();
        mockTwitter = mock(Twitter.class);
        service.setTwitter(mockTwitter);
    }



  @Test
    public void testGoodTweet(){
        String tweet = "good tweethjfgj";

        try{
            Status s = mock(Status.class);
            when(mockTwitter.updateStatus(tweet)).thenReturn(s);
            assertEquals(s, service.postTweet(tweet));
        }
        catch (Exception e){

        }


    }

    @Test
    public void testBadTweetinPostTweet() throws Exception{
        String emptyTweet = "";
        String longTweet = StringUtils.repeat("a", MAX_LENGTH + 3);
        Status s = service.postTweet(emptyTweet);
        assertTrue(s == null);
        s = service.postTweet(longTweet);
        assertTrue(s == null);
        try{
            when(mockTwitter.updateStatus("bad tweet")).thenThrow(new TwitterException("There was a problem on the server side, please try again later."));
            s = service.postTweet("bad tweet");
            assertTrue(s == null);
        }
        catch (Exception e) {

        }

    }
    @Test
    public void testBadTweetTest() throws Exception{
        when(mockTwitter.updateStatus("bad tweet")).thenThrow(new TwitterException("There was a problem on the server side, please try again later."));
        assertFalse(service.testBadTweet("bad tweet"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyTweetExceptionHandling() throws TwitterAppException{
        String emptyTweet = "";

      service.testBadTweet(emptyTweet);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testLongTweetExceptionHandling() throws TwitterAppException{
        String longTweet = StringUtils.repeat("a", MAX_LENGTH+3);
        service.testBadTweet(longTweet);

    }

    @Test
    public void testGoodTimeline() {
        ResponseList<Status> responseList = new TwitterResponseList<Status>();
        Status mockStatus = mock(Status.class);
        Status mockStatus1 = mock(Status.class);
        try{
            when(mockStatus.getText()).thenReturn("mockStatus");
            when(mockStatus1.getText()).thenReturn("mockStatus1");
            responseList.add(mockStatus);
            responseList.add(mockStatus1);
            when(mockTwitter.getHomeTimeline()).thenReturn(responseList);
            assertEquals(responseList, service.getTimeline());
            assertNotNull(service.getTimeline());

        }
        catch (Exception e){
            fail("Timeline was not returned");
        }
    }

    @Test
    public void testBadTimeline() throws Exception{
        doThrow(new TwitterException("There was a problem on the server side, please try again later.")).when(mockTwitter).getHomeTimeline();
        assertNull(service.getTimeline());
    }

}
