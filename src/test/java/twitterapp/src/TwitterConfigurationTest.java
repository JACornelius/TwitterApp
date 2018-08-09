package twitterapp.src;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;

public class TwitterConfigurationTest {
    TwitterConfiguration twitterConfig;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        twitterConfig = new TwitterConfiguration();
    }

    @Test
    public void testGetAccessToken(){
        twitterConfig.setAccessToken("accessToken");
        String accessToken = twitterConfig.getAccessToken();
        assertEquals("accessToken", accessToken);
    }

    @Test
    public void testGetConsumerSecret(){
        twitterConfig.setConsumerSecret("consumerSecret");
        String consumerSecret = twitterConfig.getConsumerSecret();
        assertEquals("consumerSecret", consumerSecret);
    }

    @Test
    public void testGetAccessTokenSecret(){
        twitterConfig.setAccessTokenSecret("accessTokenSecret");
        String accessTokenSecret = twitterConfig.getAccessTokenSecret();
        assertEquals("accessTokenSecret", accessTokenSecret);
    }

    @Test
    public void testGetConsumerKey(){
        twitterConfig.setConsumerKey("consumerKey");
        String consumerKey = twitterConfig.getConsumerKey();
        assertEquals("consumerKey", consumerKey);
    }
}
