package twitterapp.src.injections;

import dagger.Module;
import dagger.Provides;
import twitter4j.Twitter;
import twitterapp.src.services.TwitterAppService;


@Module
public class ServiceModule {
    Twitter twitter;

    public ServiceModule(Twitter twitter){
        this.twitter = twitter;
    }

    @Provides
    public TwitterAppService provideService(){
        TwitterAppService service = TwitterAppService.getService();
        service.setTwitter(twitter);
        return service;
    }

}
