package it.mm.iot.gw.admin.config;

import it.mm.iot.gw.admin.security.AuthoritiesConstants;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import tech.jhipster.config.JHipsterProperties;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebsocketConfiguration.class);

    public static final String IP_ADDRESS = "IP_ADDRESS";
    public static final String SESSIONID = "sessionId";

    private final JHipsterProperties jHipsterProperties;

    public WebsocketConfiguration(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String[] allowedOrigins = Optional
            .ofNullable(jHipsterProperties.getCors().getAllowedOrigins())
            .map(origins -> origins.toArray(new String[0]))
            .orElse(new String[0]);
        // registry
        // .addEndpoint("/websocket/tracker")
        // .setHandshakeHandler(defaultHandshakeHandler())
        // .setAllowedOrigins(allowedOrigins)
        // .withSockJS()
        // .setInterceptors(httpSessionHandshakeInterceptor());
        // registry.addEndpoint("/data");
        // registry.addEndpoint("/data").withSockJS();
        // registry.addEndpoint("/datawithbots");
        // registry.addEndpoint("/datawithbots").withSockJS();
        /***/
        registry.addEndpoint("/iot/secure/data");
        registry
            .addEndpoint("/iot/secure/data")
            .setHandshakeHandler(defaultHandshakeHandler())
            .setAllowedOrigins(allowedOrigins)
            .withSockJS()
            .setInterceptors(httpSessionHandshakeInterceptor());
        // registry.addEndpoint("/iot/secure/datawithbots");
        // registry.addEndpoint("/iot/secure/datawithbots").setHandshakeHandler(defaultHandshakeHandler())
        // .setAllowedOrigins(allowedOrigins).withSockJS().setInterceptors(httpSessionHandshakeInterceptor());

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(
            new ChannelInterceptor() {
                @Override
                public Message<?> preSend(Message<?> message, MessageChannel channel) {
                    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                        Authentication user = null; // ... ; // access authentication header(s)
                        accessor.setUser(user);
                    }
                    return message;
                }
            }
        );
    }

    @Bean
    public HandshakeInterceptor httpSessionHandshakeInterceptor() {
        final String X_AUTH_TOKEN = "x-auth-token";
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(
                ServerHttpRequest request,
                ServerHttpResponse response,
                WebSocketHandler wsHandler,
                Map<String, Object> attributes
            ) throws Exception {
                if (request instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                    attributes.put(IP_ADDRESS, servletRequest.getRemoteAddress());
                    HttpServletRequest httpServletRequest = servletRequest.getServletRequest();
                    HttpSession session = httpServletRequest.getSession();
                    attributes.put(SESSIONID, session.getId());

                    String token = httpServletRequest.getParameter(X_AUTH_TOKEN);
                    log.info("HTTP ParameterNames: " + ToStringBuilder.reflectionToString(httpServletRequest.getParameterNames()));
                }
                return true;
            }

            @Override
            public void afterHandshake(
                ServerHttpRequest request,
                ServerHttpResponse response,
                WebSocketHandler wsHandler,
                Exception exception
            ) {
                // log.info("ECCOLO
                // afterHandshake"+ToStringBuilder.reflectionToString(request.getHeaders()));
            }
        };
    }

    private DefaultHandshakeHandler defaultHandshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                Principal principal = request.getPrincipal();
                if (principal == null) {
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
                    principal = new AnonymousAuthenticationToken("WebsocketConfiguration", "anonymous", authorities);
                }
                return principal;
            }
        };
    }
}
