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
    String result = "Rental Record for " + getName() + "\n";

    for (Rental each : rentals) {
      result += "\t" + each.getMovie().getTitle() +
          "\t" + String.valueOf(each.calculate()) +
          "\n";
    }

    result += "You owed " + String.valueOf(getTotalAmount()) + "\n";
    result += "You earned " + String.valueOf(getFrequentRenterPoints()) + " frequent renter points\n";

    return result;
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
