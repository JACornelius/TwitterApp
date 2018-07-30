package twitterapp.src.models;
import java.util.Date;

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
}
