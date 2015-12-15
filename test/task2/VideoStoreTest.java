package task2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VideoStoreTest
{

    @Before
    public void setUp ()  {
        customer = new Customer("Fred");
    }

    @Test
    public void testSingleNewReleaseStatement () {
        customer.addRental (new Rental(new Movie("The Cell"), 3, Rental.Type.NEW_RELEASE));
        assertEquals ("Rental Record for Fred\n\tThe Cell\t9.0\nYou owed 9.0\nYou earned 2 frequent renter points\n", customer.statement ());
    }

    @Test
    public void testDualNewReleaseStatement () {
        customer.addRental (new Rental (new Movie ("The Cell"), 3, Rental.Type.NEW_RELEASE));
        customer.addRental (new Rental (new Movie ("The Tigger Movie"), 3, Rental.Type.NEW_RELEASE));
        assertEquals ("Rental Record for Fred\n\tThe Cell\t9.0\n\tThe Tigger Movie\t9.0\nYou owed 18.0\nYou earned 4 frequent renter points\n", customer.statement ());
    }

    @Test
    public void testSingleChildrensStatement () {
        customer.addRental (new Rental (new Movie ("The Tigger Movie"), 3, Rental.Type.CHILDRENS));
        assertEquals ("Rental Record for Fred\n\tThe Tigger Movie\t1.5\nYou owed 1.5\nYou earned 1 frequent renter points\n", customer.statement ());
    }

    @Test
    public void testMultipleRegularStatement () {
        customer.addRental (new Rental (new Movie ("Plan 9 from Outer Space"), 1, Rental.Type.REGULAR));
        customer.addRental (new Rental (new Movie ("8 1/2"), 2, Rental.Type.REGULAR));
        customer.addRental (new Rental (new Movie ("Eraserhead"), 3, Rental.Type.REGULAR));

        assertEquals ("Rental Record for Fred\n\tPlan 9 from Outer Space\t2.0\n\t8 1/2\t2.0\n\tEraserhead\t3.5\nYou owed 7.5\nYou earned 3 frequent renter points\n", customer.statement ());
    }

    private Customer customer;
}
