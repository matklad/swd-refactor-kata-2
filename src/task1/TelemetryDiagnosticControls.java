package task1;

public class TelemetryDiagnosticControls {
  private final String DiagnosticChannelConnectionString = "*111#";

  private final TelemetryClient telemetryClient;
  private String diagnosticInfo = "";

  public TelemetryDiagnosticControls(TelemetryClient telemetryClient) {
    this.telemetryClient = telemetryClient;
  }

  public TelemetryDiagnosticControls() {
    telemetryClient = new StubTelemetryClient();
  }

  public String getDiagnosticInfo() {
    return diagnosticInfo;
  }

  public void setDiagnosticInfo(String diagnosticInfo) {
    this.diagnosticInfo = diagnosticInfo;
  }

  public void checkTransmission() throws TelemetryException {
    diagnosticInfo = "";

    telemetryClient.disconnect();

    int retryLeft = 3;
    while (!telemetryClient.getOnlineStatus() && retryLeft > 0) {
      telemetryClient.connect(DiagnosticChannelConnectionString);
      retryLeft -= 1;
    }

    if (!telemetryClient.getOnlineStatus()) {
      throw new TelemetryException("Unable to connect.");
    }

    telemetryClient.send(StubTelemetryClient.DIAGNOSTIC_MESSAGE);
    diagnosticInfo = telemetryClient.receive();
  }
}