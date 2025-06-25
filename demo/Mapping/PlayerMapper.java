package com.example.demo.Mapping;

import com.example.demo.Core.Player;
import com.example.demo.Data.PlayerData;

public class PlayerMapper {

    public static PlayerData toData(Player player) {
        return new PlayerData(
                player.getName(),
                player.getJob(),
                player.getScore(),
                player.getGameLevel(),
                player.getProfilePicturePath()
        );
    }

    public static Player toDomain(PlayerData data) {
        Player player = new Player(
                data.getName(),
                data.getJob(),
                data.getProfilePicturePath()
        );

        player.setScore(data.getScore());
        player.setGameLevel(data.getGameLevel());

        return player;
    }
}
