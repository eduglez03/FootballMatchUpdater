package app;

public class UserSubscriber implements Observer {
    private String username;

    public UserSubscriber(String username) {
        this.username = username;
    }

    @Override
    public void update() {
        System.out.println("----------------------------------------------------------------");
        System.out.println("Notificación para " + username + ":");
        System.out.println("Estado actual del partido: ");
        System.out.println("----------------------------------------------------------------");
    }
}