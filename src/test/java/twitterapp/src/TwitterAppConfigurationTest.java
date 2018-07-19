package twitterapp.src;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class TwitterAppConfigurationTest {
    TwitterAppConfiguration configuration;

    @Mock
    TwitterConfiguration mockTwitterConfig = mock(TwitterConfiguration.class);


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        configuration = new TwitterAppConfiguration();
    }

    @Test
    public void testGetTwitter() {
        Twitter twitter = configuration.getTwitter();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerSecret(mockTwitterConfig.getConsumerSecret())
                .setOAuthConsumerKey(mockTwitterConfig.getConsumerKey())
                .setOAuthAccessToken(mockTwitterConfig.getAccessToken())
                .setOAuthAccessTokenSecret(mockTwitterConfig.getAccessTokenSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter testTwitter = tf.getInstance();
        assertEquals(testTwitter, twitter);
    }
}
