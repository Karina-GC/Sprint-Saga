package com.example.demo.Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import com.example.demo.Core.Player;
import com.example.demo.Core.Leaderboard;
import com.example.demo.Core.Submission;
import com.example.demo.Mapping.PlayerMapper;
import com.example.demo.Mapping.SubmissionMapper;

public class JsonUtils {

    private static final Gson gson = new GsonBuilder() 
            .setPrettyPrinting()
            .create();               // Erstellt ein Gson-Objekt

    // --- GENERISCHE METHODEN ZUM SPEICHERN UND LADEN ---
    // Speichert eine generische Liste (Collection) als JSON in eine Datei
    public static <T> void save(Collection<T> data, String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
            System.out.println("Daten als JSON gespeichert: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lädt eine generische Liste (z. B. List<Player>) aus einer JSON-Datei
    public static <T> List<T> loadList(String filePath, Class<T> clazz) {
        try (Reader reader = new FileReader(filePath)) {
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // --- PLAYERDATA ---
    // Speichert ein einzelnes PlayerData-Objekt (DTO) als JSON-Datei
    public static void savePlayerData(PlayerData data, String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
            System.out.println("PlayerData gespeichert: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lädt ein PlayerData-Objekt aus Datei zurück
    public static PlayerData loadPlayerData(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, PlayerData.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Player
    // Wandelt einen Player in PlayerData um und speichert ihn
    public static void savePlayer(Player player, String filePath) {
        PlayerData data = PlayerMapper.toData(player);
        savePlayerData(data, filePath);
    }

    // Lädt PlayerData und wandelt es in ein Player-Objekt zurück
    public static Player loadPlayer(String filePath) {
        PlayerData data = loadPlayerData(filePath);
        return data != null ? PlayerMapper.toDomain(data) : null;
    }

    // --- LEADERBOARD ---
    // Speichert die Liste der Spieler aus dem Leaderboard in eine JSON-Datei
    public static void saveLeaderboard(Leaderboard leaderboard, String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(leaderboard.getPlayers(), writer);
            System.out.println("Rangliste als JSON gespeichert: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Lädt eine JSON-Datei mit Spielern und füllt damit das Leaderboard neu
    public static void loadLeaderboard(Leaderboard leaderboard, String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Player>>() {}.getType();
            List<Player> loadedPlayers = gson.fromJson(reader, listType);

            leaderboard.clear();
            for (Player p : loadedPlayers) {
                leaderboard.addPlayer(p);
            }

            System.out.println("Rangliste geladen: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- SUBMISSIONS ---
    // Wandelt eine Liste von Submission in SubmissionData um und speichert sie
    public static void saveSubmissions(List<Submission> submissions, String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            List<SubmissionData> submissionDataList = SubmissionMapper.toDataList(submissions);
            gson.toJson(submissionDataList, writer);
            System.out.println("Submissions als JSON gespeichert: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Lädt eine Liste von SubmissionData aus Datei und wandelt sie zurück in Submission
    public static List<Submission> loadSubmissions(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<SubmissionData>>() {}.getType();
            List<SubmissionData> submissionDataList = gson.fromJson(reader, listType);
            return SubmissionMapper.toCoreList(submissionDataList);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
