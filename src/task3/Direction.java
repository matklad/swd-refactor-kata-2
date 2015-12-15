package task3;

public enum Direction {
  NORTH(0),
  EAST(1),
  SOUTH(2),
  WEST(3);
  public final int value;

  Direction(int value) {
    this.value = value;
  }

  static Direction valueOf(int ord) {
    ord = (ord % 4 + 4) % 4;
    for (Direction direction : Direction.values()) {
      if (direction.value == ord) {
        return direction;
      }
    }
    throw new IllegalStateException();
  }

  char letter() {
    switch (this) {
      case NORTH:
        return 'N';
      case EAST:
        return 'E';
      case SOUTH:
        return 'S';
      case WEST:
        return 'W';
    }
    throw new IllegalStateException();
  }
}
