package twitterapp.src;

import org.junit.Test;
import twitterapp.src.models.RequestBody;

import static junit.framework.Assert.assertEquals;

public class RequestBodyTest {
    @Test
    public void testName(){
        RequestBody requestBody = new RequestBody();
        requestBody.setName("testName");
        assertEquals("testName", requestBody.getName());
    }

    @Test
    public void testMessage(){
        RequestBody requestBody = new RequestBody();
        requestBody.setMessage("testMessage");
        assertEquals("testMessage", requestBody.getMessage());
    }
}
