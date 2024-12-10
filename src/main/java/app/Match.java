package app;

/**
 * Class to represent a football match
 */
public class Match {
  private String homeTeam; // Home team name
  private String awayTeam; // Away team name
  private int homeGoals; // Home team goals
  private int awayGoals; // Away team goals
  private String matchId; // Unique match identifier
  private boolean isFinished; // If the match is finished
  private String changeMessage; // Change message
  private int time; // Elapsed time

  /**
   * Constructor for the Match class
   *
   * @param homeTeam  Home team name
   * @param awayTeam  Away team name
   * @param homeGoals Home team goals
   * @param awayGoals Away team goals
   * @param matchId   Unique match identifier
   * @param time      Elapsed time
   * @param isFinished If the match is finished
   */
  public Match(String homeTeam, String awayTeam, int homeGoals, int awayGoals, String matchId, int time, boolean isFinished) {
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
    this.homeGoals = homeGoals;
    this.awayGoals = awayGoals;
    this.matchId = matchId;
    this.isFinished = isFinished;
    this.changeMessage = "";
    this.time = time;
  }

  // Getters
  public boolean isFinished() { return isFinished; }
  public int getTime() { return time; }
  public String getHomeTeam() { return homeTeam; }
  public String getAwayTeam() { return awayTeam; }
  public int getHomeGoals() { return homeGoals; }
  public int getAwayGoals() { return awayGoals; }
  public String getMatchId() { return matchId; }
  public String getChangeMessage() { return changeMessage; }

  // Setters
  public void setHomeGoals(int homeGoals) { this.homeGoals = homeGoals; }
  public void setFinished(boolean finished) { isFinished = finished; }
  public void setChangeMessage(String changeMessage) { this.changeMessage = changeMessage; }
  public void setAwayGoals(int awayGoals) { this.awayGoals = awayGoals; }
  public void setHomeTeam(String homeTeam) { this.homeTeam = homeTeam; }
  public void setAwayTeam(String awayTeam) { this.awayTeam = awayTeam; }
  public void setMatchId(String matchId) { this.matchId = matchId; }

  /**
   * Method to return a string representation of the match
   *
   * @param homeGoals Home team goals
   * @param awayGoals Away team goals
   * @param time      Elapsed time
   */
  public String toString() { return String.format("%s %d - %d %s", homeTeam, homeGoals, awayGoals, awayTeam); }
}