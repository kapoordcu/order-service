package de.service.order.application.actor;


import akka.actor.UntypedActor;
import de.service.order.application.service.GreetingService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GreetActor extends UntypedActor {
    private GreetingService greetingService;

    public GreetActor(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof Greet) {
            String name = ((Greet) message).getName();
            getSender().tell(greetingService.greet(name), getSelf());
        } else {
            unhandled(message);
        }
    }

    public static class Greet {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
