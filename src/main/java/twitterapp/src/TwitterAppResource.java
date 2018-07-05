package twitterapp.src;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.glassfish.jersey.server.spi.ResponseErrorMapper;
import twitter4j.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.FormParam;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.*;


@Path("/api/1.0/twitter")
public class TwitterAppResource {
    static final int MAX_LENGTH = 280;

/*    static class jsonObj {
        @JsonProperty String message;
    }*/

//IT WORKS

    @GET
    @Path("/timeline")
    public Response getTimeline()
    {
        String timeline = "Printing home timeline: \n";
        //String newStatusUpdate = "This is your new status: ";
        Twitter t = TwitterFactory.getSingleton();
        try{
            List<Status> statuses = t.getHomeTimeline();

            for(Status s: statuses)
            {
                timeline = timeline + s.getUser().getName() + ": " + s.getText() + "\n";
            }

            //String tweet = "trying to tweet with dropwizard round 2";
            //t.updateStatus(tweet);
            //newStatusUpdate = newStatusUpdate + tweet;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Code 200: Timeline has been printed.");
        return Response.ok(timeline, MediaType.TEXT_PLAIN).build();
    }


    @POST
    @Path("/tweet")
    //@Consumes(MediaType.APPLICATION_JSON)
    public Response postTweet(String tweet){
    Twitter t = TwitterFactory.getSingleton();
    if (tweet.length() < MAX_LENGTH)
    {
        System.out.println("Code 500: Tweet is too long, keep it within in 280 characters");
       return Response.status(500).entity("Tweet is too long, keep it with in 280 characters").build();
    }
    else
    {
        try {
            t.updateStatus(tweet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print("Code 200: Tweet has been posted.");
        return Response.status(200).entity("Tweet has been posted").build();
    }


    }


}





