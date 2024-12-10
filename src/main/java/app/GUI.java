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

    private void createMainInterface() {
        JFrame mainFrame = new JFrame("Sistema de Notificaciones");
        mainFrame.setSize(400, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(4, 1));  // Aumentamos las filas para incluir el nuevo bot�n

        JLabel titleLabel = new JLabel("Sistema de notificaciones de partidos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JButton liveMatchesButton = new JButton("Mostrar partidos en directo");
        JButton registerButton = new JButton("Registrarme");
        JButton exitButton = new JButton("Salir");  // Nuevo bot�n "Salir"

        // Acci�n para el bot�n de "Mostrar partidos en directo"
        liveMatchesButton.addActionListener(e -> showLiveMatches(mainFrame));

        // Acci�n para el bot�n de "Registrarme"
        registerButton.addActionListener(e -> registerUsers(mainFrame));

        // Acci�n para el bot�n de "Salir"
        exitButton.addActionListener(e -> {
            // Cerrar la ventana principal y terminar el programa
            mainFrame.dispose();
            System.exit(0);  // Finaliza la ejecuci�n del programa
        });

        // A�adir los componentes a la ventana
        mainFrame.add(titleLabel);
        mainFrame.add(liveMatchesButton);
        mainFrame.add(registerButton);
        mainFrame.add(exitButton);  // A�adir el bot�n "Salir"

        mainFrame.setVisible(true);
    }

    private void registerUsers(JFrame parentFrame) {
        JFrame registerFrame = new JFrame("Registrar usuarios");
        registerFrame.setSize(250, 200);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setLayout(new BorderLayout());

        JLabel instructionLabel = new JLabel("Introduce los nombres de los usuarios (separados por comas):");
        JTextField userInputField = new JTextField();

        JButton submitButton = new JButton("Registrar");
        submitButton.addActionListener(e -> {
            String[] usernames = userInputField.getText().split(",");
            for (String username : usernames) {
                createUserWindow(username.trim());
            }
            registerFrame.dispose();
            run(); // Iniciar el sistema de actualizaciones si no est� en ejecuci�n
        });

        registerFrame.add(instructionLabel, BorderLayout.NORTH);
        registerFrame.add(userInputField, BorderLayout.CENTER);
        registerFrame.add(submitButton, BorderLayout.SOUTH);

        registerFrame.setVisible(true);
    }