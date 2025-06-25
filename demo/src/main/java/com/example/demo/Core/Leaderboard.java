package com.example.demo.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Leaderboard {

    private final List<Player> players;

    public Leaderboard() {
        this.players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
            sortByLevel();
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void clear() {
        players.clear();
    }

    public void sortByLevel() {
        Collections.sort(players, Comparator
                .comparingInt(Player::getGameLevel).reversed()
                .thenComparingInt(Player::getScore).reversed());
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public Player getTopPlayer() {
        return players.isEmpty() ? null : players.get(0);
    }

     
     // Erstellt einen formatierten String zur Darstellung der Rangliste.
    public String displayLeaderboard() {
        StringBuilder sb = new StringBuilder("Rangliste:\n");
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            sb.append(String.format("Rang %d: %s (Level %d, Punkte %d)\n",
                    i + 1, p.getName(), p.getGameLevel(), p.getScore()));
        }
        return sb.toString();
    }
}