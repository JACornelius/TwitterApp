package twitterapp.src.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;

import twitterapp.src.exceptions.EmptyTweetException;
import twitterapp.src.exceptions.LongTweetException;
import twitterapp.src.exceptions.TwitterAppException;
import twitterapp.src.models.RequestBody;
import twitterapp.src.services.TwitterAppService;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/api/1.0/twitter")
public class TwitterAppResource {

    public static int MAX_LENGTH = 280;
    public TwitterAppService service;
    private static Logger log = (Logger) LoggerFactory.getLogger("myLogger");

    public void setService(TwitterAppService s) {
        service = s;
    }

    public TwitterAppResource(Twitter twitter) {
        service = TwitterAppService.getService();
        service.setTwitter(twitter);
    }

    @GET
    @Path("/timeline")
    public Response getTimeline() {
        List<Status> statuses = service.getTimeline();
        if (statuses.isEmpty() == false) {
            return Response.ok(statuses, MediaType.APPLICATION_JSON_TYPE).build();
        } else {
            return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
        }
    }


    @POST
    @Path("/tweet")
    @Consumes("application/json")
    public Response postTweet(RequestBody input) throws Exception{

        String name = input.name;
        log.info("name: " + name);
        Status s;
        String tweet = input.message;
        log.info("message: " + tweet);


            try {
                 s = service.postTweet(tweet);
                //return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
            }
            catch (EmptyTweetException e) {
                return Response.serverError().entity("The tweet is empty.").build();
            }
            catch(LongTweetException e){
                return Response.serverError().entity("The tweet needs to under 280 characters.").build();

            }
            catch (TwitterAppException e){
                return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
            }

        return Response.ok().entity("Tweet(" + s.getText() + ") has been posted.").build();
    }

}



