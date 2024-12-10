package app;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * Class to create the main GUI of the application
 */
public class GUI {
  private MatchNotifier matchNotifier;
  private MatchUpdater matchUpdater;
  private List<UserSubscriber> subscribers;
  private ScheduledExecutorService scheduler;
  private Map<String, JTextArea> userNotificationAreas;

  /**
   * Method to start the application
   */
  public void run() {
    // Initialize the scheduler to update the match results every minute
    scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.scheduleAtFixedRate(() -> matchUpdater.updateMatchResults(), 0, 1, TimeUnit.MINUTES);
  }

  /**
   * Constructor of the class
   */
  public GUI() {
    FootballAPIService apiService = new FootballAPIService();
    this.matchNotifier = new MatchNotifier();
    this.matchUpdater = new MatchUpdater(apiService, matchNotifier);
    this.subscribers = new ArrayList<>();
    this.userNotificationAreas = new HashMap<>();
    createMainInterface();
  }

  /**
   * Method to create the main interface of the application
   */
  private void createMainInterface() {
    JFrame mainFrame = new JFrame("Sistema de Notificaciones");
    mainFrame.setSize(400, 300);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setLayout(new GridLayout(4, 1));

    JLabel titleLabel = new JLabel("Sistema de notificaciones de partidos", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

    JButton liveMatchesButton = new JButton("Mostrar partidos en directo");
    JButton registerButton = new JButton("Registrarme");
    JButton exitButton = new JButton("Salir");

    // Action button for "Mostrar partidos en directo"
    liveMatchesButton.addActionListener(e -> showLiveMatches(mainFrame));

    // Action button for "Registrarme"
    registerButton.addActionListener(e -> registerUsers(mainFrame));

    // Action button for "Salir"
    exitButton.addActionListener(e -> {
      mainFrame.dispose(); // Close the main window
      System.exit(0);  // Exit the application
    });

    mainFrame.add(titleLabel);
    mainFrame.add(liveMatchesButton);
    mainFrame.add(registerButton);
    mainFrame.add(exitButton);

    mainFrame.setVisible(true);
  }

  /**
   * Method to register users in the application
   * @param parentFrame
   */
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
      run();
    });

    registerFrame.add(instructionLabel, BorderLayout.NORTH);
    registerFrame.add(userInputField, BorderLayout.CENTER);
    registerFrame.add(submitButton, BorderLayout.SOUTH);

    registerFrame.setVisible(true);
  }

  /**
   * Method to show the live matches in the application
   * @param parentFrame
   */
  private void showLiveMatches(JFrame parentFrame) {
    JFrame liveMatchesFrame = new JFrame("Partidos en directo");
    liveMatchesFrame.setSize(500, 500);
    liveMatchesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    liveMatchesFrame.setLayout(new BorderLayout(10, 10));

    // Create the table to show the live matches
    String[] columnNames = {"Equipo Local", "Goles Local", "Tiempo Juego", "Goles Visitante", "Equipo Visitante"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    JTable matchesTable = new JTable(tableModel);

    // Customize the table
    matchesTable.setDefaultEditor(Object.class, null);
    matchesTable.getTableHeader().setReorderingAllowed(false);
    matchesTable.setAutoCreateRowSorter(true);
    matchesTable.setShowGrid(true);
    matchesTable.setGridColor(Color.BLACK);
    matchesTable.setRowHeight(30);
    matchesTable.setFont(new Font("Arial", Font.PLAIN, 14));

    JTableHeader header = matchesTable.getTableHeader();
    header.setBackground(new Color(220, 220, 220));
    header.setFont(new Font("Arial", Font.BOLD, 14));
    header.setPreferredSize(new Dimension(0, 40));

    for (int i = 0; i < matchesTable.getColumnCount(); i++) {
      matchesTable.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
          Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
          if (column == 2) {
            setHorizontalAlignment(SwingConstants.CENTER);
            setText(value + " ''");
          } else {
            setHorizontalAlignment(SwingConstants.CENTER);
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

    JButton backButton = new JButton("Volver");
    backButton.setFont(new Font("Arial", Font.BOLD, 14));
    backButton.setBackground(new Color(70, 130, 180));
    backButton.setForeground(Color.BLACK);
    backButton.setFocusPainted(false);
    backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    backButton.addActionListener(e -> liveMatchesFrame.dispose());

    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new BorderLayout());
    JLabel titleLabel = new JLabel("Partidos en Directo", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    titleLabel.setForeground(new Color(70, 130, 180));
    headerPanel.add(titleLabel, BorderLayout.CENTER);

    liveMatchesFrame.add(headerPanel, BorderLayout.NORTH);
    liveMatchesFrame.add(scrollPane, BorderLayout.CENTER);
    liveMatchesFrame.add(backButton, BorderLayout.SOUTH);
    liveMatchesFrame.setVisible(true);
  }

  /**
   * Method to create the user window
   * @param username
   */
  private void createUserWindow(String username) {
    JFrame userFrame = new JFrame("Notificaciones - " + username);
    userFrame.setSize(400, 300);
    userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JTextArea notificationArea = new JTextArea();
    notificationArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(notificationArea);

    userFrame.add(scrollPane);
    userFrame.setVisible(true);

    // Create a new subscriber for this user and add it to the list of subscribers
    UserSubscriber subscriber = new UserSubscriber(username, userNotificationAreas) {
      @Override
      public void update(Match match) { // Update the notification area with the new match
        super.update(match);
      }
    };
    subscribers.add(subscriber);
    matchNotifier.addObserver(subscriber);

    // Save the notification area for this user
    userNotificationAreas.put(username, notificationArea);
  }
}