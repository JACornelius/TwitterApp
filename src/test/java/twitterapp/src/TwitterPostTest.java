package twitterapp.src;

import org.junit.Before;
import org.junit.Test;
import twitterapp.src.models.TwitterPost;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

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
}
