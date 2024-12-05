package app;

import java.util.Map;

public class MatchUpdater {
  private final FootballAPIService apiService;
  private final MatchNotifier matchNotifier;
  private Map<String, Match> ongoingMatches;

  public MatchUpdater(FootballAPIService apiService, MatchNotifier matchNotifier) {
    this.apiService = apiService;
    this.matchNotifier = matchNotifier;
    this.ongoingMatches = apiService.fetchMatchResults();  // Inicializar con los resultados iniciales
  }







}
