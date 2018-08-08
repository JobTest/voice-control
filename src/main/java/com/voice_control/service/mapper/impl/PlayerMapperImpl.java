package com.voice_control.service.mapper.impl;

import com.voice_control.domain.Player;
import com.voice_control.service.mapper.PlayerMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapperImpl implements PlayerMapper {

    @Override
    public String toJson(Player player) {
        return new Gson().toJson(player, Player.class);
    }

    @Override
    public Player fromJson(String json) {
        return new Gson().fromJson(json, Player.class);
    }
}
