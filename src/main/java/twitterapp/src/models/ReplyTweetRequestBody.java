package twitterapp.src.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplyTweetRequestBody{
    @JsonProperty("name")
    public String name;
    @JsonProperty("message")
    public String message;
    @JsonProperty("replyTweetID")
    public long replyTweetID;

    public void setName(String name){
        this.name = name;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setReplyTweetID(long replyTweetID) { this.replyTweetID = replyTweetID; }

    public String getName(){
        return name;
    }

    public String getMessage()
    {
        return message;
    }

    public long getReplyTweetID() { return replyTweetID; }
}
