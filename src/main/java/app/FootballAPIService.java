package app;

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










}
