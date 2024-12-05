package app;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FootballAPIService implements APIService{
  private static final String API_URL = "https://v3.football.api-sports.io/fixtures?live=all";
  private static final String API_KEY = "1da0a4f3da1938f5901dae68c725194f"; // Tu clave API

  @Override
  public Map<String, Match> fetchMatchResults() {
    try {
      URL url = new URL(API_URL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestProperty("x-rapidapi-key", API_KEY);
      connection.setRequestProperty("x-rapidapi-host", "v3.football.api-sports.io");
      connection.setRequestMethod("GET");

      // Verificar el código de respuesta HTTP
      int responseCode = connection.getResponseCode();
      if (responseCode != 200) {
        System.out.println("Error al obtener los resultados. Código de error: " + responseCode);
        return new HashMap<>();
      }

      Scanner scanner = new Scanner(connection.getInputStream());
      StringBuilder result = new StringBuilder();
      while (scanner.hasNext()) {
        result.append(scanner.nextLine());
      }
      scanner.close();

      // Parsear y devolver los resultados en un Map
      return parseMatchResults(result.toString());
    } catch (IOException e) {
      e.printStackTrace();
      return new HashMap<>();
    }
  }

  @Override
  public Map<String, Match> parseMatchResults(String json) {
    System.out.println("JSON Response: " + json);

    JsonObject response = JsonParser.parseString(json).getAsJsonObject();
    JsonArray fixtures = response.getAsJsonArray("response");

    Map<String, Match> ongoingMatches = new HashMap<>();

    for (var fixtureElement : fixtures) {
      JsonObject fixture = fixtureElement.getAsJsonObject();
      JsonObject teams = fixture.getAsJsonObject("teams");
      JsonObject goals = fixture.getAsJsonObject("goals");
      JsonObject status = fixture.has("status") ? fixture.getAsJsonObject("status") : null;
      JsonObject fixtureStatus = fixture.getAsJsonObject("fixture").getAsJsonObject("status");
      JsonObject fixtureDetails = fixture.getAsJsonObject("fixture");

      String homeTeam = teams.has("home") && teams.getAsJsonObject("home").has("name")
              ? teams.getAsJsonObject("home").get("name").getAsString() : "Unknown";

      String awayTeam = teams.has("away") && teams.getAsJsonObject("away").has("name")
              ? teams.getAsJsonObject("away").get("name").getAsString() : "Unknown";

      String matchId = fixtureDetails.has("id") ? fixtureDetails.get("id").getAsString() : "Unknown";

      // Obtener el minuto actual del partido
      int timeElapsed = fixtureStatus.has("elapsed") && !fixtureStatus.get("elapsed").isJsonNull()
              ? fixtureStatus.get("elapsed").getAsInt()
              : 0;

      boolean isFinished = status != null && status.has("long") && status.get("long").getAsString().equals("Finished");

      int homeGoals = getGoals(goals, "home");
      int awayGoals = getGoals(goals, "away");

      Match match = new Match(homeTeam, awayTeam, homeGoals, awayGoals, matchId, timeElapsed, isFinished);
      ongoingMatches.put(matchId, match);
    }
    return ongoingMatches;
  }

  // Método para obtener los goles de un equipo
  private int getGoals(JsonObject goals, String teamType) {
    try {
      if (goals.has(teamType) && !goals.get(teamType).isJsonNull()) {
        return goals.getAsJsonPrimitive(teamType).getAsInt();
      } else {
        return 0;
      }
    } catch (Exception e) {
      System.out.println("Error al procesar los goles para el equipo " + teamType);
      return 0;
    }
  }
}