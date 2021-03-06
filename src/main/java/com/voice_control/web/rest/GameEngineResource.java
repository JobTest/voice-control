package com.voice_control.web.rest;

import java.net.URISyntaxException;

import com.google.gson.JsonSyntaxException;
import com.voice_control.domain.Player;
import com.voice_control.domain.enumeration.PlayerState;
import com.voice_control.service.GameEngineService;
import com.voice_control.service.mapper.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller for GameEngine.
 */
@RestController
@RequestMapping(value="/api")
public class GameEngineResource {

	private static final Logger logger = LoggerFactory.getLogger(GameEngineResource.class);

	@Autowired
	private GameEngineService gameEngineService;

	@Autowired
	private PlayerMapper playerMapper;

	@PostMapping("/players")
	public Mono<ResponseEntity<Player>> registrationPlayer(@RequestBody Player player) {
		return gameEngineService.save(player)
				.map(registrationPlayer -> new ResponseEntity<>(registrationPlayer, HttpStatus.CREATED))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/players")
	public Flux<Player> getPlayerAll() {
		return gameEngineService.findAll();
	}

	@GetMapping(path = "/game-round/{playerId}")
	public ResponseEntity<Flux<String>> getConnectToGameRound(@PathVariable String playerId, @RequestParam String jsonPlayer) {
		logger.info("player ID = {}; json Player: {};", playerId, jsonPlayer);

		try {
			Player player = playerMapper.fromJson("{" + jsonPlayer + "}");
			Flux<String> connectToGameRound = gameEngineService.getConnectToGameRound(playerId, player);
			return new ResponseEntity<>(connectToGameRound, HttpStatus.OK);
		} catch (URISyntaxException | IllegalAccessException | JsonSyntaxException ex) {
			logger.error(ex.getLocalizedMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/game-round")
	public Flux<Player> getActualGameRound() {
		return gameEngineService.findByStates(PlayerState.PLAY);
	}
}