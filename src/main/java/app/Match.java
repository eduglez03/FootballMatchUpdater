package app;

public class Match {

    private String homeTeam;
    private String awayTeam;
    private int homeGoals;
    private int awayGoals;
    private String matchId;
    private boolean isFinished; // Nuevo campo para almacenar si el partido ha finalizado
    private String changeMessage;

    public Match(String homeTeam, String awayTeam, int homeGoals, int awayGoals, String homeLogo, String awayLogo, String matchId, int time, boolean isFinished) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.matchId = matchId;
        this.isFinished = isFinished; // Inicializar estado
        this.changeMessage = ""; // Inicialización vacía

    }
}
