package twitterapp.src;

import org.junit.Test;
import twitterapp.src.models.RequestBody;
import twitterapp.src.models.TwitterPost;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TwitterPostTest {
    TwitterPost twitterPost = new TwitterPost(null, null, null, null, null);

    @Test
    public void testTwitterPost() {
        twitterPost.setTwitterHandle("testTwitterHandle");
        twitterPost.setProfileImageUrl("testProfileImageURL");
        twitterPost.setUsername("testUserName");
        twitterPost.setMessage("testMessage");
        Date date = new Date(2018, 1, 1);
        twitterPost.setCreatedAt(date);
        assertEquals("testTwitterHandle", twitterPost.getTwitterHandle());
        assertEquals("testProfileImageURL", twitterPost.getProfileImageUrl());
        assertEquals("testUserName",twitterPost.getUserName());
        assertEquals("testMessage", twitterPost.getMessage());
        assertEquals(date, twitterPost.getCreatedAt());

    }

    @Test
    public void testEquals(){
        Date date = new Date(2018, 1,1);
        Date date1 = new Date(2018, 2, 2);
        TwitterPost twitterPost = new TwitterPost("messageTwitterPost", "usernameTwitterPost", "twitterHandleTwitterPost", "profileURLTwittterPost", date);
        TwitterPost twitterPost0 = new TwitterPost("messageTwitterPost", "usernameTwitterPost", "twitterHandleTwitterPost", "profileURLTwittterPost", date);
        TwitterPost twitterPost1 = new TwitterPost("messageTwitterPost0", "usernameTwitterPost0", "twitterHandleTwitterPost0", "profileURLTwittterPost0", date1);
        TwitterPost twitterPost2 = new TwitterPost("messageTwitterPost1", "usernameTwitterPost", "twitterHandleTwitterPost", "profileURLTwittterPost", date);
        TwitterPost twitterPost3 = new TwitterPost("messageTwitterPost", "usernameTwitterPost1", "twitterHandleTwitterPost", "profileURLTwittterPost", date);
        TwitterPost twitterPost4 = new TwitterPost("messageTwitterPost", "usernameTwitterPost", "twitterHandleTwitterPost1", "profileURLTwittterPost", date);
        TwitterPost twitterPost5 = new TwitterPost("messageTwitterPost", "usernameTwitterPost", "twitterHandleTwitterPost", "profileURLTwittterPost1", date);
        TwitterPost twitterPost6 = new TwitterPost("messageTwitterPost", "usernameTwitterPost", "twitterHandleTwitterPost", "profileURLTwittterPost", date1);
        RequestBody requestBody = new RequestBody();
        assertTrue(twitterPost.equals(twitterPost));
        assertTrue(twitterPost.equals(twitterPost0));
        assertFalse(twitterPost.equals(twitterPost1));
        assertFalse(twitterPost.equals(twitterPost2));
        assertFalse(twitterPost.equals(twitterPost3));
        assertFalse(twitterPost.equals(twitterPost4));
        assertFalse(twitterPost.equals(twitterPost5));
        assertFalse(twitterPost.equals(twitterPost6));
        assertFalse(twitterPost.equals(requestBody));

    }

    @Test
    public void testHashCode(){
        Date date = new Date(2018, 1,1);
        TwitterPost twitterPost = new TwitterPost("messageTwitterPost", "usernameTwitterPost", "twitterHandleTwitterPost", "profileURLTwittterPost", date);
        TwitterPost twitterPost1 = new TwitterPost("messageTwitterPost", "usernameTwitterPost", "twitterHandleTwitterPost", "profileURLTwittterPost", date);
        assertEquals(twitterPost.hashCode(),twitterPost1.hashCode());

    }
}
