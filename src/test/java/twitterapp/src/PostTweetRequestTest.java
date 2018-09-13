package twitterapp.src;

import org.junit.Test;
import twitterapp.src.models.PostTweetRequest;

import static junit.framework.Assert.assertEquals;

public class PostTweetRequestTest {
    @Test
    public void testName() {
        PostTweetRequest postTweetRequest = new PostTweetRequest();
        postTweetRequest.setName("testName");
        assertEquals("testName", postTweetRequest.getName());
    }

    @Test
    public void testMessage() {
        PostTweetRequest postTweetRequest = new PostTweetRequest();
        postTweetRequest.setMessage("testMessage");
        assertEquals("testMessage", postTweetRequest.getMessage());
    }
}
