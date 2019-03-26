/**
 * A customer class for the HW4 assignment, it holds the cutomer name, quantity of items and the price
 * the customer is willing to pay for an item.
 * @author Daryl
 *
 */

public class Customer {
    String name;
    double price;
    int bidTime;
    int quantity;


    // cretes and empty constructor for the Customer class...
    Customer() {

    }

    Customer(int time, double price) {
        this.bidTime = time;

    }

    //constructor used to take information about a customer and their order.
    Customer(int bidTime, String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.bidTime = bidTime;
    }


    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
    public int getBidtime(int t) {
        t = convertToMinutes(t);
        int y = Integer.parseInt(timeRepresentation(t));
        return y;
    }

    //used for time representation
    public static String timeRepresentation (int time) {
        if (time == 0) {
            return "0000";
        } else if (time < 10) {
            return "000" + time;
        } else if (time < 100) {
            return "00" + time;
        } else if (time < 1000) {
            return "0" + time;
        } return "" + time;
    }

    //used for time representation
    public static int convertToMinutes(int t) {
        return ((t/100)*60) + (t%100);
    }
    public static int addHoursMinutes (int t, int bidDurationTime) {
        int addition = convertToMinutes(t) + bidDurationTime; 
        int converted = ((addition/60)*100) + (addition%60); 
        if (converted >= 2400) {
            return converted - 2400;
        } else {
            return converted;
        }
    }

    //used for time representation
    public static int substractHoursMinutes (int t1, int t2) {
        int substraction = Math.abs(convertToMinutes(t1) - convertToMinutes(t2));
        return ((substraction/60)*100) + (substraction%60);
    }
    @Override
    public String toString() {

        return (name + " " + price+"0"+" " +quantity).trim();
    }
}
