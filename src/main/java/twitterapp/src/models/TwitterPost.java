package twitterapp.src.models;
import twitter4j.Status;

import java.util.Date;
import java.util.Objects;

public class TwitterPost {
    private String message ;
    private String userName;
    private String twitterHandle;
    private String profileImageUrl;
    private Date createdAt;
    
    public TwitterPost(String message, String userName, String twitterHandle, String profileImageUrl, Date createdAt){
        this.message = message;
        this.userName = userName;
        this.twitterHandle = twitterHandle;
        this.profileImageUrl = profileImageUrl;
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(!(o instanceof TwitterPost)) return false;
        TwitterPost twitterPost = (TwitterPost) o;
        return Objects.equals(message, twitterPost.message)
                && Objects.equals(userName, twitterPost.userName)
                && Objects.equals(twitterHandle, twitterPost.twitterHandle)
                && Objects.equals(profileImageUrl, twitterPost.profileImageUrl)
                && Objects.equals(createdAt, twitterPost.createdAt);
    }

    @Override
    public int hashCode(){
        return Objects.hash(message, userName, twitterHandle, profileImageUrl, createdAt);
    }

    public String getMessage(){
        return this.message;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getTwitterHandle(){
        return this.twitterHandle;
    }

    public String getProfileImageUrl(){
        return this.profileImageUrl;
    }

    public Date getCreatedAt(){
        return this.createdAt;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setUsername(String userName){
        this.userName = userName;
    }

    public void setTwitterHandle(String twitterHandle){
        this.twitterHandle = twitterHandle;
    }

    public void setProfileImageUrl(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }

    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }

    public TwitterPost statusToTwitterPost(Status status){
        TwitterPost twitterPost = new TwitterPost(status.getText(),status.getUser().getName(), status.getUser().getScreenName(), status.getUser().getProfileImageURL(), status.getCreatedAt());
        return twitterPost;
    }
}
