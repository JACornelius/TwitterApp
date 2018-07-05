package twitterapp.src;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.Status;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/1.0/twitter")
public class TwitterAppResource {
    static final int MAX_LENGTH = 280;

    @GET
    @Path("/timeline")
    public Response getTimeline()
    {
        String timeline ="";
        Twitter t = TwitterFactory.getSingleton();
        try{
            List<Status> statuses = t.getHomeTimeline();
            timeline = "Printing home timeline: \n";
            for(Status s: statuses)
            {
                timeline = timeline + s.getUser().getName() + ": " + s.getText() + "\n";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Code 500: There was a problem, possibly incorrect authentication codes");
            return Response.status(500).entity("There was a problem, possible incorrect authentication codes").build();
        }
        System.out.println("Code 200: Timeline has been printed.");
        return Response.ok(timeline, MediaType.APPLICATION_JSON_TYPE).build();
    }


    @POST
    @Path("/tweet")
    public Response postTweet(String tweet){
    Twitter t = TwitterFactory.getSingleton();
    if (tweet.length() < MAX_LENGTH) {
        System.out.println("Code 500: Tweet is too long, keep it within in 280 characters");
       return Response.status(500).entity("Tweet is too long, keep it with in 280 characters").build();
    }
    else {
        try {
            t.updateStatus(tweet);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Code 500: Tweet was not posted, possibly due to authentication error or duplicated tweet");
            return Response.status(500).entity("Tweet was not posted, possibly due to authentication error or duplicated tweet").build();
        }
        System.out.print("Code 200: Tweet has been posted.");
        return Response.status(200).entity("Tweet has been posted").build();
    }
    }
}





