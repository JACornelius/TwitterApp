package twitterapp.src.injections;

import twitter4j.Twitter;

public class TwitterInjection {

    private Twitter twitter;

    public TwitterInjection(Twitter twitter){
        this.twitter = twitter;

    }

    public void setTwitter(Twitter twitter){
        this.twitter = twitter;
    }

    public Twitter getTwitter(){
        return this.twitter;
    }

}
