package twitterapp.src.injections;

import dagger.Component;
import twitter4j.Twitter;
import twitterapp.src.resources.TwitterAppResource;
import twitterapp.src.services.TwitterAppService;

import javax.inject.Singleton;

@Singleton
@Component(modules = {TwitterModule.class, ServiceModule.class})
public interface TwitterComponent {
     TwitterAppResource buildTwitterAppResource();

     //void inject();

}
