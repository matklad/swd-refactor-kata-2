package task1;

import org.junit.Test;

import static org.junit.Assert.*;

public class TelemetryDiagnosticControlsTest {
  @Test
  public void testCanSetDiagnosticMessage() throws Exception {
    TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls();
    controls.setDiagnosticInfo("Hello, World!");
    assertEquals("Hello, World!", controls.getDiagnosticInfo());
  }

  @Test
  public void testDiagnosticsCanBeNull() throws Exception {
    TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls();
    controls.setDiagnosticInfo(null);
    assertNull(controls.getDiagnosticInfo());
  }

  @Test
  public void testCheckTransmission() throws Exception {
    TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls();
    controls.checkTransmission();
  }

  @Test(expected = TelemetryException.class)
  public void testThrowsWhenConnectingToBrokenClient() throws Exception {
    TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls(new TelemetryClient() {
      @Override
      public boolean getOnlineStatus() {
        return false;
      }
    });

    controls.checkTransmission();
  }

  @Test
  public void testClearsDiagnosticMessage() throws Exception {
    TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls(new TelemetryClient() {
      @Override
      public boolean getOnlineStatus() {
        return false;
      }
    });

    controls.setDiagnosticInfo("should be cleared");
    boolean thrown = false;
    try {
      controls.checkTransmission();
    } catch (TelemetryException e) {
      thrown = true;
    }
    assertTrue(thrown);
    assertEquals("", controls.getDiagnosticInfo());
  }

  @Test
  public void testMakesRetry() throws Exception {
    class TestTelemetryClient implements TelemetryClient {
      int retries = 0;
      String message = "";

      @Override
      public boolean getOnlineStatus() {
        return retries > 2;
      }

      @Override
      public void connect(String telemetryServerConnectionString) {
        retries++;
      }

      @Override
      public void send(String message) {
        this.message = message;
      }
    }
    TestTelemetryClient client = new TestTelemetryClient();
    TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls(client);

    controls.checkTransmission();

    assertEquals("AT#UD", client.message);
  }

  @Test(expected = TelemetryException.class)
  public void testVerifiesConnection() throws Exception {
    class TestTelemetryClient implements TelemetryClient {
      boolean isOnline = false;

      @Override
      public boolean getOnlineStatus() {
        isOnline = !isOnline;
        return isOnline;
      }
    }
    TestTelemetryClient client = new TestTelemetryClient();
    TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls(client);

    controls.checkTransmission();
  }

  @Test
  public void testConnectionProtocol() throws Exception {
    class TestTelemetryClient implements TelemetryClient {
      String state = "connected";
      int nMethodCalls = 0;

      @Override
      public void disconnect() {
        assertEquals("connected", state);
        state = "disconnected";
        nMethodCalls++;
      }

      @Override
      public boolean getOnlineStatus() {
        return "connected".equals(state);
      }

      @Override
      public void connect(String telemetryServerConnectionString) {
        assertEquals("disconnected", state);
        state = "connected";
        nMethodCalls++;
      }

      @Override
      public void send(String message) {
        assertEquals("connected", state);
        state = "send";
        nMethodCalls++;
      }

      @Override
      public String receive() {
        assertEquals("send", state);
        state = "received";
        nMethodCalls++;
        return "You have a message";
      }
    }

    TestTelemetryClient client = new TestTelemetryClient();
    TelemetryDiagnosticControls controls = new TelemetryDiagnosticControls(client);
    controls.checkTransmission();

    assertEquals(client.state, "received");
    assertEquals(4, client.nMethodCalls);
    assertEquals("You have a message", controls.getDiagnosticInfo());
  }
}
