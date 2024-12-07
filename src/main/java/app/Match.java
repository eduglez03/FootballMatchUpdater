package app;

public class Match {
    private String homeTeam;
    private String awayTeam;
    private int homeGoals;
    private int awayGoals;
    private String matchId;
    private boolean isFinished; // Nuevo campo para almacenar si el partido ha finalizado
    private String changeMessage;
    private int time; // Nuevo campo para almacenar el minuto del partido
    private String homeLogo;
    private String awayLogo;

    public Match(String homeTeam, String awayTeam, int homeGoals, int awayGoals, String homeLogo, String awayLogo, String matchId, int time, boolean isFinished) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.matchId = matchId;
        this.isFinished = isFinished; // Inicializar estado
        this.changeMessage = ""; // Inicialización vacía
        this.time = time; // Inicializar minuto
        this.homeLogo = homeLogo;
        this.awayLogo = awayLogo;
    }

    // Getters y Setters
    public boolean isFinished() { return isFinished; }

    public int getTime() { return time; }

    public void setFinished(boolean finished) { isFinished = finished; }

    // Getters
    public String getHomeTeam() { return homeTeam; }

    public String getAwayTeam() { return awayTeam; }

    public int getHomeGoals() { return homeGoals; }

    public int getAwayGoals() { return awayGoals; }

    public String getMatchId() { return matchId; }

    public String getChangeMessage() { return changeMessage; }

    public String getHomeLogo() { return homeLogo; }

    public String getAwayLogo() { return awayLogo; }

    // Setters
    public void setHomeGoals(int homeGoals) { this.homeGoals = homeGoals; }

    public void setChangeMessage(String changeMessage) { this.changeMessage = changeMessage; }

    public void setAwayGoals(int awayGoals) { this.awayGoals = awayGoals; }

    public void setHomeTeam(String homeTeam) { this.homeTeam = homeTeam; }

    public void setAwayTeam(String awayTeam) { this.awayTeam = awayTeam; }

    public void setMatchId(String matchId) { this.matchId = matchId; }

    // Método toString() para mostrar información del partido
    public String toString() {
        return String.format("%s %d - %d %s", homeTeam, homeGoals, awayGoals, awayTeam);
    }
}