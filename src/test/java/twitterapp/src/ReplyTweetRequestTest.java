package twitterapp.src;

import org.junit.Test;
import twitterapp.src.models.ReplyTweetRequest;
import static junit.framework.Assert.assertEquals;

public class ReplyTweetRequestTest {
    @Test
    public void testName(){
        ReplyTweetRequest replyTweetRequest = new ReplyTweetRequest();
        replyTweetRequest.setName("testName");
        assertEquals("testName", replyTweetRequest.getName());
    }

    @Test
    public void testMessage(){
        ReplyTweetRequest replyTweetRequest = new ReplyTweetRequest();
        replyTweetRequest.setMessage("testMessage");
        assertEquals("testMessage", replyTweetRequest.getMessage());
    }

    @Test
    public void testReplyTweetId(){
        ReplyTweetRequest replyTweetRequest = new ReplyTweetRequest();
        replyTweetRequest.setReplyTweetID(2394739);
        assertEquals(2394739, replyTweetRequest.getReplyTweetID());
    }
}
