package com.voice_control.service.mapper;

import com.voice_control.domain.Player;

public interface PlayerMapper {

    String toJson(Player player);

    Player fromJson(String json);
}
