package app;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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

    public void run() {
        // Iniciar el sistema: actualizar resultados cada minuto
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> matchUpdater.updateMatchResults(), 0, 1, TimeUnit.MINUTES);
    }

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

    private void showLiveMatches(JFrame parentFrame) {
        JFrame liveMatchesFrame = new JFrame("Partidos en directo");
        liveMatchesFrame.setSize(500, 500);  // Aumentar el tama�o para mejorar la disposici�n
        liveMatchesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        liveMatchesFrame.setLayout(new BorderLayout(10, 10)); // Espaciado entre los componentes

        // Crear los datos de la tabla
        String[] columnNames = {"Equipo Local", "Goles Local", "Tiempo Juego", "Goles Visitante", "Equipo Visitante"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable matchesTable = new JTable(tableModel);

        // Configurar la tabla
        matchesTable.setDefaultEditor(Object.class, null);  // Desactivar edici�n de celdas
        matchesTable.getTableHeader().setReorderingAllowed(false); // Desactivar reordenamiento de columnas
        matchesTable.setAutoCreateRowSorter(true); // Para ordenar las filas
        matchesTable.setShowGrid(true); // Mostrar divisiones entre filas y columnas
        matchesTable.setGridColor(Color.BLACK); // Color de las divisiones
        matchesTable.setRowHeight(30); // Ajustar altura de las filas
        matchesTable.setFont(new Font("Arial", Font.PLAIN, 14)); // Fuente m�s grande para las filas

        // Personalizar la fila de encabezados
        JTableHeader header = matchesTable.getTableHeader();
        header.setBackground(new Color(220, 220, 220)); // Color de fondo del encabezado
        header.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente en negrita y tama�o
        header.setPreferredSize(new Dimension(0, 40)); // Aumentar altura del encabezado

        // Alineaci�n de las columnas
        for (int i = 0; i < matchesTable.getColumnCount(); i++) {
            matchesTable.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (column == 2) { // Columna "Tiempo Juego"
                        setHorizontalAlignment(SwingConstants.CENTER);  // Centrar la columna
                        setText(value + " ''");  // Agregar el "min" al valor
                    } else {
                        setHorizontalAlignment(SwingConstants.CENTER); // Centrar las otras columnas
                    }
                    return c;
                }
            });
        }

        // Agregar los partidos a la tabla
        Map<String, Match> liveMatches = matchUpdater.getLiveMatches();
        for (Match match : liveMatches.values()) {
            Object[] row = {
                    match.getHomeTeam(),
                    match.getHomeGoals(),
                    match.getTime(),
                    match.getAwayGoals(),
                    match.getAwayTeam()
            };
            tableModel.addRow(row);
        }

        JScrollPane scrollPane = new JScrollPane(matchesTable);

        // Bot�n de "Volver" estilizado
        JButton backButton = new JButton("Volver");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));  // Fuente m�s grande y en negrita
        backButton.setBackground(new Color(70, 130, 180));  // Color de fondo del bot�n
        backButton.setForeground(Color.BLACK);  // Color del texto
        backButton.setFocusPainted(false);  // Eliminar el foco del bot�n
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // A�adir relleno
        backButton.addActionListener(e -> liveMatchesFrame.dispose());

        // Crear un JPanel para el encabezado y centrarlo
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Partidos en Directo", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));  // Fuente m�s grande y en negrita
        titleLabel.setForeground(new Color(70, 130, 180));  // Color del texto del t�tulo
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // A�adir componentes al frame
        liveMatchesFrame.add(headerPanel, BorderLayout.NORTH);  // T�tulo en el norte
        liveMatchesFrame.add(scrollPane, BorderLayout.CENTER); // Tabla en el centro
        liveMatchesFrame.add(backButton, BorderLayout.SOUTH);  // Bot�n en el sur

        liveMatchesFrame.setVisible(true);
    }

    private void createUserWindow(String username) {
        JFrame userFrame = new JFrame("Notificaciones - " + username);
        userFrame.setSize(400, 300);
        userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea notificationArea = new JTextArea();
        notificationArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(notificationArea);

        userFrame.add(scrollPane);
        userFrame.setVisible(true);

        // Crear y registrar al usuario como suscriptor
        UserSubscriber subscriber = new UserSubscriber(username, userNotificationAreas) {
            @Override
            public void update(Match match) {
                super.update(match);
            }
        };
        subscribers.add(subscriber);
        matchNotifier.addObserver(subscriber);

        // Guardar el �rea de notificaci�n para este usuario
        userNotificationAreas.put(username, notificationArea);
    }
}