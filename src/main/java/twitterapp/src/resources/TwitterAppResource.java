package twitterapp.src.resources;


import twitterapp.src.exceptions.EmptyReplyTweetId;
import twitterapp.src.exceptions.EmptyTweetMsgException;
import twitterapp.src.exceptions.LongTweetException;
import twitterapp.src.exceptions.TwitterAppException;
import twitterapp.src.models.ReplyTweetRequest;
import twitterapp.src.models.PostTweetRequest;
import twitterapp.src.models.TwitterPost;
import twitterapp.src.services.TwitterAppService;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;

@Path("/api/1.0/twitter")
public class TwitterAppResource {

    public static int MAX_LENGTH = 280;

    TwitterAppService service;

       public void setService(TwitterAppService s) {
        service = s;
    }

    @Inject
    public TwitterAppResource(TwitterAppService service) {
        this.service = service;

    }

    @GET
    @Path("/timeline")
    public Response getHomeTimeline() {
        try{
            Optional<List<TwitterPost>> statuses = service.getHomeTimeline();
            List<TwitterPost> result = statuses.map(res -> statuses.get())
                                               .orElse(null);
            return Response.ok(result, MediaType.APPLICATION_JSON_TYPE).build();
        }
        catch(TwitterAppException e){
            return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
        }
    }

    @GET
    @Path("/timeline/user")
    public Response getUserTimeline() {
        try{
            Optional<List<TwitterPost>> statuses = service.getUserTimeline();
            List<TwitterPost> result = statuses.map(res -> statuses.get())
                                               .orElse(null);
            return Response.ok(result, MediaType.APPLICATION_JSON_TYPE).build();
        }
        catch(TwitterAppException e){
            return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
        }
    }

    @POST
    @Path("/tweet")
    @Consumes("application/json")
    public Response postTweet(PostTweetRequest input) throws Exception {
        Optional<TwitterPost> twitterPost;
            try {
                twitterPost = service.postTweet(input);
            } catch (EmptyTweetMsgException e) {
                return Response.serverError().entity("The tweet is empty.").build();
            } catch(LongTweetException e){
                return Response.serverError().entity("The tweet needs to under 280 characters.").build();
            } catch (TwitterAppException e){
                return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
            }

        return Response.ok(twitterPost.get(), MediaType.APPLICATION_JSON_TYPE).build();
    }

    @POST
    @Path("/tweet/reply")
    @Consumes("application/json")
    public Response replyTweet(ReplyTweetRequest input) throws Exception {
        Optional<TwitterPost> twitterPost;
        try {
            twitterPost = service.replyTweet(input);
        } catch (EmptyTweetMsgException e) {
            return Response.serverError().entity("The tweet is empty.").build();
        } catch (LongTweetException e) {
            return Response.serverError().entity("The tweet needs to under 280 characters.").build();
        } catch (EmptyReplyTweetId e) {
            return Response.serverError().entity("No reply TweetID was entered").build();
        } catch (TwitterAppException e) {
            return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
        }
        return Response.ok(twitterPost.get(), MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/tweet/filter")
    public Response filterTweets(@QueryParam("filter") String filter) {
        Optional<List<TwitterPost>> listTwitterPost;
        try{
            listTwitterPost = service.filterTweets(filter);
            List<TwitterPost> result = listTwitterPost.map(res -> listTwitterPost.get())
                                                      .orElse(null);
            return Response.ok(result, MediaType.APPLICATION_JSON_TYPE).build();
        }
        catch(TwitterAppException e){
            return Response.serverError().entity("There was a problem on the server side, please try again later.").build();
        }

    }

}



