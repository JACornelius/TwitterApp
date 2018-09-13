package twitterapp.src.exceptions;

public class EmptyTweetMsgException extends Exception {
    public EmptyTweetMsgException(String message){
        super(message);
    }
}
