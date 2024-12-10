package app;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class GUI {
    private MatchNotifier matchNotifier;
    private MatchUpdater matchUpdater;
    private List<UserSubscriber> subscribers;
    private ScheduledExecutorService scheduler;
    private Map<String, JTextArea> userNotificationAreas;

    public GUI() {
        // Inicializar servicios
        FootballAPIService apiService = new FootballAPIService();
        this.matchNotifier = new MatchNotifier();
        this.matchUpdater = new MatchUpdater(apiService, matchNotifier);
        this.subscribers = new ArrayList<>();
        this.userNotificationAreas = new HashMap<>();
        createMainInterface();
    }