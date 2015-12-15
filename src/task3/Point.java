package task3;

public class Point {
  private final int location;

  public int getLocation() {
    return location;
  }

  private int maxLocation;

  public int getMaxLocation() {
    return maxLocation;
  }

  public Point(int locationValue, int maxLocationValue) {
    location = locationValue;
    maxLocation = maxLocationValue;
  }

  public Point move(int delta) {
    int m = maxLocation + 1;
    int newLocation = ((location + delta) % m + m) % m;
    return new Point(newLocation, maxLocation);
  }

}