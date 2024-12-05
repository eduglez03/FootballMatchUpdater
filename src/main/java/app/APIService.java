package app;

import java.util.Map;

public interface APIService {
  Map<String, Match> fetchMatchResults();
  Map<String, Match> parseMatchResults(String json);
}