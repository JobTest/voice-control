package com.voice_control.repository;

import com.voice_control.domain.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends ReactiveMongoRepository<Player, String> {

}
