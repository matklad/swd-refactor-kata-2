package task1;

public interface TelemetryClient {
  default boolean getOnlineStatus() {
    return true;
  }

  default void connect(String telemetryServerConnectionString) {
  }

  default void disconnect() {
  }

  default void send(String message) {
  }

  default String receive() {
    return "";
  }
}