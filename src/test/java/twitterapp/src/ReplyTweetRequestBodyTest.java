package twitterapp.src;

import org.junit.Test;
import twitterapp.src.models.ReplyTweetRequestBody;
import static junit.framework.Assert.assertEquals;

public class ReplyTweetRequestBodyTest {
    @Test
    public void testName(){
        ReplyTweetRequestBody replyTweetRequestBody = new ReplyTweetRequestBody();
        replyTweetRequestBody.setName("testName");
        assertEquals("testName", replyTweetRequestBody.getName());
    }

    @Test
    public void testMessage(){
        ReplyTweetRequestBody replyTweetRequestBody = new ReplyTweetRequestBody();
        replyTweetRequestBody.setMessage("testMessage");
        assertEquals("testMessage", replyTweetRequestBody.getMessage());
    }

    @Test
    public void testReplyTweetId(){
        ReplyTweetRequestBody replyTweetRequestBody = new ReplyTweetRequestBody();
        replyTweetRequestBody.setReplyTweetID(2394739);
        assertEquals(2394739, replyTweetRequestBody.getReplyTweetID());
    }
}
