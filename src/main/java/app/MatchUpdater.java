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

  // Método que se ejecuta cada minuto para obtener nuevos resultados y comparar
  public void updateMatchResults() {
    // Obtener los resultados más recientes de la API
    Map<String, Match> newMatches = apiService.fetchMatchResults();

    // Comparar los nuevos resultados con los antiguos y notificar si hay cambios
    compareAndNotifyMatches(newMatches);
  }

  // Método para comparar los partidos nuevos con los antiguos y notificar a los usuarios
  private void compareAndNotifyMatches(Map<String, Match> newMatches) {
    for (Map.Entry<String, Match> entry : newMatches.entrySet()) {
      Match newMatch = entry.getValue();
      Match oldMatch = ongoingMatches.get(entry.getKey());

      if (oldMatch == null || hasMatchChanged(newMatch, oldMatch)) {
        ongoingMatches.put(entry.getKey(), newMatch);  // Actualizamos el partido

        // Notificar a los usuarios sobre el cambio
        matchNotifier.notifyChange(newMatch);
      }
    }
  }

  // Getter para obtener los partidos en directo
  public Map<String, Match> getLiveMatches() {
    return ongoingMatches;
  }

  private boolean hasMatchChanged(Match newMatch, Match oldMatch) {
    boolean changed = false;
    StringBuilder changeMessage = new StringBuilder();

    // Comparar goles de equipo local
    if (newMatch.getHomeGoals() != oldMatch.getHomeGoals()) {
      changeMessage.append("El equipo local ").append(newMatch.getHomeTeam())
              .append(" ha marcado gol en el minuto ").append(newMatch.getTime()).append("\n");
      changed = true;
    }
    else if (newMatch.getAwayGoals() != oldMatch.getAwayGoals()) {  // Comparar goles de equipo visitante
      changeMessage.append("El equipo visitante ").append(newMatch.getAwayTeam())
              .append(" ha marcado gol en el minuto ").append(newMatch.getTime()).append("\n");
      changed = true;
    }
    else if (newMatch.getTime() == 45 && oldMatch.getTime() != 45) { // Verificar si el partido está en el descanso (minuto 45)
      changeMessage.append("Finaliza el primer tiempo entre ")
              .append(newMatch.getHomeTeam()).append(" y ").append(newMatch.getAwayTeam())
              .append("\n");
      changed = true;
    }
    else if (newMatch.isFinished() && !oldMatch.isFinished()) { // Verificar si el partido ha finalizado
      changeMessage.append("El partido entre ").append(newMatch.getHomeTeam())
              .append(" y ").append(newMatch.getAwayTeam()).append(" ha finalizado.\n");
      changed = true;
    }
    else if (newMatch.getTime() == 46 && oldMatch.getTime() == 45) { // Verificar si el partido ha comenzado el segundo tiempo
      changeMessage.append("Comienza el segundo tiempo entre ")
              .append(newMatch.getHomeTeam()).append(" y ").append(newMatch.getAwayTeam())
              .append("\n");
      changed = true;
    } else if (newMatch.getTime() == 1) {
      changeMessage.append("Comienza el partido entre ")
              .append(newMatch.getHomeTeam()).append(" y ").append(newMatch.getAwayTeam())
              .append("\n");
      changed = true;
    }

    // Si hubo un cambio, establecer el mensaje
    if (changed) {
      newMatch.setChangeMessage(changeMessage.toString());
    }

    return changed;
  }
}