package twitterapp.src.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;

import twitterapp.src.exceptions.EmptyTweetException;
import twitterapp.src.exceptions.LongTweetException;
import twitterapp.src.exceptions.TwitterAppException;
import twitterapp.src.models.RequestBody;
import twitterapp.src.models.TwitterPost;
import twitterapp.src.services.TwitterAppService;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/api/1.0/twitter")
public class TwitterAppResource {

    public static int MAX_LENGTH = 280;

    public TwitterAppService service;

    public void setService(TwitterAppService s) {
        service = s;
    }

    public TwitterAppResource(Twitter twitter) {
        service = TwitterAppService.getService();
        service.setTwitter(twitter);
    }

    @GET
    @Path("/timeline")
    public Response getTimeline(){
        try{
            Optional<List<TwitterPost>> statuses = service.getTimeline();
            return Response.ok(statuses.get(), MediaType.APPLICATION_JSON_TYPE).build();
        }
        catch(TwitterAppException e){
            return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
        }
    }


    @POST
    @Path("/tweet")
    @Consumes("application/json")
    public Response postTweet(RequestBody input) throws Exception{
        Optional<TwitterPost> twitterPost;

            try {
                twitterPost = service.postTweet(input);
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

        return Response.ok(twitterPost.get(), MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/tweet/filter")
    public Response filterTweets(@QueryParam("filter") String filter){
        Optional<List<TwitterPost>> listTwitterPost;
        try{
            listTwitterPost = service.filterTweets(filter);
            return Response.ok(listTwitterPost.get(), MediaType.APPLICATION_JSON_TYPE).build();
        }
        catch(TwitterAppException e){
            return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
        }

    }

}



