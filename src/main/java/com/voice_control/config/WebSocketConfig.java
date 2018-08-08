package com.voice_control.config;

import com.voice_control.domain.enumeration.PlayerState;
import com.voice_control.service.GameEngineService;
import com.voice_control.service.mapper.PlayerMapper;
import com.voice_control.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
public class WebSocketConfig {

    @Autowired
    private GameEngineService gameEngineService;

    @Autowired
    private PlayerMapper playerMapper;

    @Value("/${game.ws-host}")
    private String webSocketHostName;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public HandlerMapping getUrlHandlerMapping() {
        Map<String, WebSocketHandler> socketHandlers = new HashMap<>();

        gameEngineService.findByStates(PlayerState.START_ROUND, PlayerState.PLAY, PlayerState.STOP_ROUND)
                .map(player -> {
                        String urlSocketChanel = webSocketHostName + "/" + player.getId();

                        socketHandlers.put(urlSocketChanel,
                                socketSession -> socketSession.send( Flux.<String>generate(sink -> {
                                            Player monoPlayer = gameEngineService.findById(player.getId())
                                                    .block();

                                            switch (monoPlayer.getState()) {
                                                case PLAY:
                                                    sink.next(playerMapper.toJson(monoPlayer));
                                                    break;
                                                case STOP_ROUND:
                                                    sink.next(playerMapper.toJson(monoPlayer));
                                                    break;
                                                case START_ROUND:
                                                    sink.complete();
                                                    break; } })
                                        .map( socketSession::textMessage )
                                        .delayElements(Duration.ofSeconds(1))));
                        return player; })
                .then()
                .block();

        SimpleUrlHandlerMapping urlHandlerMapping = new SimpleUrlHandlerMapping();
        urlHandlerMapping.setUrlMap(socketHandlers);
        urlHandlerMapping.setOrder(1);
        return urlHandlerMapping;
    }

    @Bean
    public WebSocketHandlerAdapter getWebSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
