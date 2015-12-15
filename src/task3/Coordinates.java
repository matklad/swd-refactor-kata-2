package task3;

import java.util.ArrayList;
import java.util.List;

public class Coordinates {
  private Point x;
  private Point y;
  private Direction direction;
  private List<Obstacle> obstacles;

  public void setPoint(Point p, int axis) {
    if (axis == 0) {
      x = p;
      return;
    } else if (axis == 1) {
      y = p;
      return;
    }
    throw new IllegalArgumentException();
  }

  public Point getPoint(int axis) {
    if (axis == 0) {
      return x;
    } else if (axis == 1) {
      return y;
    }
    throw new IllegalArgumentException();
  }


  public Point getX() {
    return x;
  }

  public Point getY() {
    return y;
  }

  public void setDirection(Direction value) {
    direction = value;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setObstacles(List<Obstacle> value) {
    obstacles = new ArrayList<>(value);
  }

  public Coordinates(Point x,
                     Point y,
                     Direction direction,
                     List<Obstacle> obstacles) {
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.obstacles = new ArrayList<>(obstacles);
  }

  @Override
  public String toString() {
    throw new UnsupportedOperationException();
  }

  public boolean hasObstacleAt(Point px, Point py) {
    return obstacles.stream()
        .anyMatch((it) -> it.x == px.getLocation() && it.y == py.getLocation());
  }
}