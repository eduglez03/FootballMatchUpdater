package com.manning.junitbook;

import java.util.ArrayList;
import java.util.List;

// Clase que notifica a los observadores actua como sujeto que esta pendeinte de los cambios
public class MatchNotifier implements Subject {
    private List<UserSubscriber> observers = new ArrayList<>();

    // Método para añadir observadores
    @Override
    public void addObserver(UserSubscriber observer) {
        observers.add(observer);
    }

    // Método para eliminar observadores
    @Override
    public void removeObserver(UserSubscriber observer) {
        observers.remove(observer);
    }

    // Método para notificar cambios
    @Override
    public void notifyChange(Match match) {
        for (UserSubscriber observer : observers) {
            observer.update(match);
        }
    }
}