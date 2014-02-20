package fr.xebia.gae.todo.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import fr.xebia.gae.todo.api.model.WelcomeMsg;


@Api(
        name = "startingpoint",
        version = "v1"
)
public class StartingPoint {

    @ApiMethod(name = "welcome", httpMethod = "get")
    public WelcomeMsg getWelcome() {
        return new WelcomeMsg();
    }

    @ApiMethod(name = "welcomeWithName", httpMethod = "get")
    public WelcomeMsg getWelcomeWithName(@Named("name") String name) {
        WelcomeMsg welcomeMsg = new WelcomeMsg();
        welcomeMsg.setMessage("welcome " + name);

        return welcomeMsg;
    }

}
