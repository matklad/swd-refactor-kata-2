package task2;

public class Rental {
  enum Type {
    REGULAR,
    NEW_RELEASE,
    CHILDRENS;
  }

  public Rental(Movie movie, int daysRented, Type type) {
    this.movie = movie;
    this.daysRented = daysRented;
    this.type = type;
  }

  public int getDaysRented() {
    return daysRented;
  }

  public Movie getMovie() {
    return movie;
  }

  private final Movie movie;
  private final int daysRented;
  private final Type type;

  public boolean isFrequentRenter() {
    return type == Type.NEW_RELEASE && getDaysRented() > 1;
  }

  public double calculate() {
    switch (type) {
      case REGULAR:
        return calculateRegularRental();
      case NEW_RELEASE:
        return calculateNewReleaseRental();
      case CHILDRENS:
        return calculateChildrenRental();

    }
    throw new IllegalStateException();
  }

  private double calculateChildrenRental() {
    double total = 1.5;
    if (getDaysRented() > 3)
      total += (getDaysRented() - 3) * 1.5;
    return total;
  }

  private double calculateNewReleaseRental() {
    return getDaysRented() * 3;
  }

  private double calculateRegularRental() {
    double total = 2;
    if (getDaysRented() > 2)
      total += (getDaysRented() - 2) * 1.5;
    return total;
  }
}
