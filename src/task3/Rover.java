package task3;

public class Rover {
  private final Coordinates coordinates;
  private boolean collided = false;

  public Rover(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void receiveSingleCommand(char command) {
    command = Character.toUpperCase(command);
    switch (command) {
      case 'F':
        move(MoveDirection.FORWARD);
        break;
      case 'B':
        move(MoveDirection.BACKWARDS);
        break;
      case 'L':
        rotate(RotateDirection.LEFT);
        break;
      case 'R':
        rotate(RotateDirection.RIGHT);
        break;
      default:
        throw new IllegalArgumentException();
    }
  }

  private void rotate(RotateDirection direction) {
    if (collided) {
      return;
    }

    Direction old_direction = coordinates.getDirection();
    int r = direction == RotateDirection.LEFT ? -1 : 1;
    Direction new_direction = Direction.valueOf(old_direction.value + r);
    coordinates.setDirection(new_direction);
  }

  public void receiveCommands(String commands) {
    for (char c : commands.toCharArray()) {
      receiveSingleCommand(c);
    }
  }

  public String getPosition() {
    if (collided) {
      return " NOK";
    }
    return getCoordinates().getX().getLocation() +
        " X " +
        getCoordinates().getY().getLocation() + " " +
        getCoordinates().getDirection().letter();
  }

  enum MoveDirection {
    FORWARD,
    BACKWARDS
  }

  enum RotateDirection {
    LEFT,
    RIGHT,
  }

  private void move(MoveDirection direction) {
    if (collided) {
      return;
    }
    int inv = -1;
    int axis = 0;
    switch (coordinates.getDirection()) {
      case EAST:
        inv = 1;
        // fall through
      case WEST:
        axis = 0;
        break;
      case NORTH:
        inv = 1;
        // fall through
      case SOUTH:
        axis = 1;
    }

    Point p = coordinates.getPoint(axis);
    int delta = direction == MoveDirection.FORWARD ? 1 : -1;
    Point newPoint = p.move(delta * inv);
    Point px = axis == 0 ? newPoint : coordinates.getPoint(0);
    Point py = axis == 1 ? newPoint : coordinates.getPoint(1);
    if (coordinates.hasObstacleAt(px, py)) {
      collided = true;
      return;
    }
    coordinates.setPoint(newPoint, axis);
  }
}
