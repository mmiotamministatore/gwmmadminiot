package it.mm.iot.gw.admin.web.websocket.proxy.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.crossbar.autobahn.wamp.Client;
import io.crossbar.autobahn.wamp.Session;
import io.crossbar.autobahn.wamp.types.CallResult;
import io.crossbar.autobahn.wamp.types.Publication;
import io.crossbar.autobahn.wamp.types.PublishOptions;
import io.crossbar.autobahn.wamp.types.Registration;
import io.crossbar.autobahn.wamp.types.SessionDetails;
import io.crossbar.autobahn.wamp.types.Subscription;
import it.mm.iot.gw.admin.service.model.event.AssetEventManager;
import it.mm.iot.gw.admin.web.websocket.IoTService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WampController {

    @Autowired
    private IoTService iotService;

    @Autowired
    private AssetEventManager assetEventManager;

    private final Logger log = LoggerFactory.getLogger(WampController.class);

    @Value("${app.websocket.client.wamp.url:ws://10.92.1.2:9009/ws}")
    private String url;

    @Value("${app.websocket.client.wamp.realm:demo}")
    private String realm;

    private static final String PROCEDURE = "com.example.demowamp.procedure";

    @Value("${app.websocket.client.wamp.topics:com.example.demowamp.topic,test.analysis}")
    private String[] topics;

    private Executor executor;

    private Session session;

    public void start() {
        this.executor = Executors.newSingleThreadExecutor();
        this.session = new Session(executor);

        session.addOnJoinListener(this::registerExample);
        session.addOnJoinListener(this::callExample);
        session.addOnJoinListener(this::subscribeExample);
        session.addOnJoinListener(this::publishExample);

        log.info(String.format("WampController started with url=%s, realm=%s", url, realm));

        Client client = new Client(session, url, realm, executor);
        try {
            client.connect().get();
        } catch (Exception e) {
            log.error("Errore durante start:", e);
        }
    }

    public void registerExample(Session session, SessionDetails details) {
        // Register procedure expecting two integers as argument and returning sum of
        // them
        CompletableFuture<Registration> completableFuture = session.register(PROCEDURE, this::registerHandler);
        completableFuture.thenAccept(registration -> log.info("Procedure registered: " + registration.procedure));
    }

    private int registerHandler(List<Object> args) {
        return (int) args.get(0) + (int) args.get(1);
    }

    public void callExample(Session session, SessionDetails details) {
        int arg1 = 1, arg2 = 2;

        // Call procedure with the arguments arg1 and arg2
        CompletableFuture<CallResult> completableFuture = session.call(PROCEDURE, arg1, arg2);
        completableFuture.thenAccept(callResult -> System.out.println("Result of call: " + callResult.results));
    }

    public void subscribeExample(Session session, SessionDetails details) {
        for (String topic : topics) {
            CompletableFuture<Subscription> completableFuture = session.subscribe(topic, this::subscribeHandler);
            completableFuture.thenAccept(subscription -> log.info("Subscribed to topic " + subscription.topic));
        }
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private void subscribeHandler(List<Object> args) {
        String data = (String) args.get(0);
        //log.info("Received via subscription: " + data);
        assetEventManager.executeData(data);

        //		 this.messagingTemplate.convertAndSendToUser(
        //	               "biro", "/topic/iot/secure/data/user", args.get(0));
        String user = "4a3e1029-7943-49f6-9c21-8a32e83cbfd0";
        iotService.sendMessageToUser(user, args.get(0));
    }

    public void publishExample(Session session, SessionDetails details) {
        String args = "Hello Subscriber";
        PublishOptions publishOptions = new PublishOptions(true, false);

        // Publish on topic with args and publishOptions
        for (String topic : topics) {
            CompletableFuture<Publication> completableFuture = session.publish(topic, publishOptions, args);
            completableFuture.thenAccept(publication -> log.info("Published on " + topic));
        }
    }
}
