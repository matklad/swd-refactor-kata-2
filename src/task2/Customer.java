package task2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Customer {
  private final String name;
  private final List<Rental> rentals = new ArrayList<>();

  public Customer(String name) {
    this.name = name;
  }

  public void addRental(Rental rental) {
    rentals.add(rental);
  }

  private String getName() {
    return name;
  }

  public String statement() {
    StringBuilder result = new StringBuilder();
    result.append("Rental Record for ").append(getName())
        .append("\n");

    for (Rental each : rentals) {
      result
          .append("\t").append(each.getMovie().getTitle())
          .append("\t").append(String.valueOf(each.calculate()))
          .append("\n");
    }

    result.append("You owed ")
        .append(String.valueOf(getTotalAmount())).append("\n");

    result.append("You earned ")
        .append(String.valueOf(getFrequentRenterPoints())).append(" frequent renter points\n");

    return result.toString();
  }

  private double getTotalAmount() {
    return rentals.stream()
        .collect(Collectors.summingDouble(Rental::calculate));
  }

  private int getFrequentRenterPoints() {
    return (int) rentals.stream()
        .filter(Rental::isFrequentRenter)
        .count() + rentals.size();
  }
}
