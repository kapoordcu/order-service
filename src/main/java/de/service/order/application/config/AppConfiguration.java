package de.service.order.application.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;
import de.service.order.application.actor.FulfillmentActor;
import de.service.order.application.ext.SpringExtension;
import de.service.order.application.ext.SpringProps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static de.service.order.application.ext.SpringExtension.SPRING_EXTENSION_PROVIDER;

@Configuration
@ComponentScan
public class AppConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("akka-spring-demo");
        SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
        return system;
    }

    @Test
    public void testActor() {
        ActorSystem system = ActorSystem.create("actor-system", ConfigFactory.load());
        SpringExtension.getInstance().get(system).initialize(applicationContext);

        ActorRef testActor= system.actorOf(SpringProps.create(system, FulfillmentActor.class));
        testActor.tell("hello world",ActorRef.noSender());
    }
}
