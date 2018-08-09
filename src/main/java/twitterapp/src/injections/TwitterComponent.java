package twitterapp.src.injections;

import dagger.Component;
import twitterapp.src.resources.TwitterAppResource;

import javax.inject.Singleton;

@Singleton
@Component(modules = {TwitterModule.class})
public interface TwitterComponent {
     TwitterAppResource buildTwitterAppResource();

}
