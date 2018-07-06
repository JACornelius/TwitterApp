package twitterapp.src;


import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.Status;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

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
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setJSONStoreEnabled(true);
        Twitter t = new TwitterFactory(cb.build()).getInstance();
        String output = "";
        try{

            List<Status> statuses = t.getHomeTimeline();
            for(Status s: statuses)
            {
                output += DataObjectFactory.getRawJSON(s);
            }
            System.out.println("Code 200: Timeline has been printed.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Code 500: There was a problem on the server side, please try again later.");
            return Response.status(500).entity("There was a problem on the server side, please try again later.").build();
        }
        return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(output).build();
    }


    @POST
    @Path("/tweet")
    public Response postTweet(String tweet){
    Twitter t = TwitterFactory.getSingleton();
    if (tweet.length() > MAX_LENGTH) {
        System.out.println("Code 500: Tweet is too long, keep it within in 280 characters");
       return Response.status(500).entity("Tweet is too long, keep it with in 280 characters").build();
    }
    else {
        try {
            t.updateStatus(tweet);
        }
        catch (Exception e) {
            System.out.println("Code 500: There was a problem on the server side, please try again later.");
            return Response.status(500).entity("There was a problem on the server side, please try again later.").build();
        }
        System.out.print("Code 200: Tweet has been posted.");
        return Response.status(200).entity("Tweet has been posted").build();
    }
    }
}





