package callbackMessages;

@FunctionalInterface
public interface MessageCallbacks {
    void send(String message);
}
