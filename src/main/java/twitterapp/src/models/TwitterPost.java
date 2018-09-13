package twitterapp.src.models;

import twitter4j.Status;
import java.util.Date;
import java.util.Objects;

public class TwitterPost {
    private String message ;
    private String userName;
    private String twitterHandle;
    private String profileImageUrl;
    private long statusId;
    private Date createdAt;

    public TwitterPost() {
        this.message = null;
        this.userName = null;
        this.twitterHandle = null;
        this.profileImageUrl = null;
        this.createdAt = null;
        this.statusId = 0;
    }

    public TwitterPost(String message, String userName, String twitterHandle, String profileImageUrl, Date createdAt, long statusId) {
        this.message = message;
        this.userName = userName;
        this.twitterHandle = twitterHandle;
        this.profileImageUrl = profileImageUrl;
        this.createdAt = createdAt;
        this.statusId = statusId;
    }

    public TwitterPost(Status s) {
        this.message = s.getText();
        this.userName = s.getUser().getName();
        this.twitterHandle = s.getUser().getScreenName();
        this.profileImageUrl = s.getUser().getProfileImageURL();
        this.statusId = s.getId();
        this.createdAt = s.getCreatedAt();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof TwitterPost)) return false;
        TwitterPost twitterPost = (TwitterPost) o;

        return Objects.equals(message, twitterPost.message)
                && Objects.equals(userName, twitterPost.userName)
                && Objects.equals(twitterHandle, twitterPost.twitterHandle)
                && Objects.equals(profileImageUrl, twitterPost.profileImageUrl)
                && Objects.equals(createdAt, twitterPost.createdAt)
                && Objects.equals(statusId, twitterPost.statusId);
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

    public long getStatusId() {return this.statusId; }

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

    public void setStatusId(long statusId) { this.statusId = statusId; }

}
