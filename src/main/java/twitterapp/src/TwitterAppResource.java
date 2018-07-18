package twitterapp.src;


import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.Response.serverError;

@Path("/api/1.0/twitter")
public class TwitterAppResource {
    static final int MAX_LENGTH = 280;
    Twitter t;
    List<Status> statuses;



    public TwitterAppResource(Twitter twitter){

        t = twitter;
    }


    @GET
    @Path("/timeline")
    public Response getTimeline() {

        try {
            statuses = t.getHomeTimeline();
            if (statuses != null) {
                System.out.println("Code 200: Timeline has been printed.");
                return Response.ok(statuses, MediaType.APPLICATION_JSON_TYPE).build();
            } else {
                    System.out.println("Code 500: There was a problem on the server side, please try again later.");
                    return serverError().entity("There was a problem on the server side, please try again later.").build();

            }
        } catch (TwitterException e) {
            System.out.println("Code 500: There was a problem on the server side, please try again later.");
            return Response.serverError().entity("There was a problem on the server side, please try again later.").build();

        }
    }


    @POST
    @Path("/tweet")
    public Response postTweet(String tweet){
    //Twitter t = twitterFactory.getSingleton();
    if (tweet.length() > MAX_LENGTH) {
        System.out.println("Code 500: Tweet is too long, keep it within 280 characters");
        return Response.serverError().entity("Tweet is too long, keep it within 280 characters").build();
    }
    else if(tweet.length() == 0){
        System.out.println("Code 500: No tweet entered");
        return Response.serverError().entity("No tweet entered").build();
    }
    else {
        try {
            t.updateStatus(tweet);
        }
        catch (Exception e) {
            System.out.println("Code 500: There was a problem on the server side, please try again later.");
            return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
        }
        System.out.print("Code 200: Tweet("+tweet+") has been posted.");
        return Response.ok(statuses, MediaType.APPLICATION_JSON_TYPE).entity("Tweet("+tweet+") has been posted.").build();
    }
    }
}



