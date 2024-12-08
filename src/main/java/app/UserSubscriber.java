package app;

import java.util.Map;
import javax.swing.JTextArea;

public abstract class UserSubscriber implements Observer {
    private String username;
    private Map<String, JTextArea> userNotificationAreas;

    public UserSubscriber(String username, Map<String, JTextArea> userNotificationAreas) {
        this.username = username;
        this.userNotificationAreas = userNotificationAreas;
    }

    @Override
    public void update(Match match) {
        // Obtener el área de notificaciones asociada a este usuario
        JTextArea notificationArea = userNotificationAreas.get(username);
        if (notificationArea != null) {
            // Agregar mensaje de cambio al área de notificación
            notificationArea.append(match.getChangeMessage() + "\n");
        } else {
            System.err.println("No se encontró el área de notificaciones para el usuario: " + username);
        }
    }

    public String getUsername() {
        return username;
    }
}


