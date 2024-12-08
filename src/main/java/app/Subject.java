package app;

// Interfaz para el Sujeto
public interface Subject {
    void addObserver(UserSubscriber observer);
    void removeObserver(UserSubscriber observer);
    void notifyChange(Match match);
}