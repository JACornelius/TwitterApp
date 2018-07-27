package twitterapp.src.models;
import java.util.Date;

public class TwitterPost {
    private String message;
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

}
