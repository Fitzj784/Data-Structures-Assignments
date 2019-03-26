/*
  Author: Daryl George
  Email: dgeorge2015@my.fit.edu
  Course: CSE2010
  Section: E2
  Description: Creating a program to handle bidding at random times and to sell items to the highest bidder at a
  randomly selected time (Ebay simulation)
  for maximum profit.
 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HW4 {

    static Double updatedprice = 0.0; // used to keep the minimum updated sales price (global variable)

    // the main method for running the tasks for the output in the Console...
    public static void main(final String... args) throws FileNotFoundException {
        //long startTime = System.currentTimeMillis( );
        ArrayList<String> queries = new ArrayList<String>(); // list of queries used to get output
        processQuery(args[0], queries); // used to get each command from the query
        //AdaptableHeap used to store customer information.
        HeapAdaptablePriorityQueue<Double, Customer> bidding = new HeapAdaptablePriorityQueue<Double,Customer>(); 
        answerQuery(queries, bidding); // responds to the query commands.
        //long endTime = System.currentTimeMillis( );
        //long elapsed = endTime - startTime;
        // System.out.println(elapsed/1000.0);
    }

    /**
     * Reads the query files and uses creats an arraylist to be read for the console...
     * @param filename
     * @param q
     * @throws FileNotFoundException
     */
    public static void processQuery(String filename, ArrayList<String> q) throws FileNotFoundException {
        final File file = new File (filename);
        final Scanner sc = new Scanner (file);
        while (sc.hasNextLine()) {
            q.add(sc.nextLine()); // adding the lines of text to the arraylist.
        }
        sc.close();
    }

    /**
     * Method takes in the queries from command line and executes the specific command with the other methods for the
     * correct output.
     * @param mlist
     * @param b
     * @param cust
     */
    public static void answerQuery(ArrayList<String> mlist, HeapAdaptablePriorityQueue<Double, Customer> b) {
        /*
         * takes files from the fileRead method and splits the line for commands and input for customers bidding
         * then returns the requested output.
         */
        for(int i = 0; i < mlist.size(); i++) {
            String lines = mlist.get(i); //splits the arraylist into lines.
            String[] command = lines.split(" "); // array to store each command.


            if (command[0].equals("EnterBid")) {
                System.out.print(command[0] + " " + command[1] + " "+ command[2]+ " "+ command[3] + " "+ command[4]);
                if (customerCheck(b, command[2])) {
                    System.out.print(" "+ "ExistingCustomerError"); // prints error if similar user is found.
                }
                else {
                    Customer bid = new Customer(Integer.parseInt(command[1]), command[2], Double.parseDouble(command[3]),
                            Integer.parseInt(command[4]));
                    b.insert(bid.getPrice(), bid);
                    System.out.println();
                }
            }
            else if (command[0].equals("RemoveBid")) {
                System.out.print(command[0] + " "+ command[1] + " "+ command[2]);
                removeBid(b,command[2]);
                System.out.println();
            }
            else if(command[0].equals("ChangeBid")) {
                System.out.print(command[0] +" "+ command[1] +" "+ command[2] +" "+ command[3] +" "+ command[4]);
                changebid(b,command[2], Double.parseDouble(command[3]), Integer.parseInt(command[4]));
                System.out.println();
            }
            else if(command[0].equals("UpdateMinimumAcceptablePrice")) { //not working yet...
                System.out.print(command[0] + " " + command[1] + " "+command[2]);
                updateMin(Double.parseDouble(command[2]));
                System.out.println();
            }
            else if(command[0].equals("DisplayHighestBid")) {
                System.out.print(command[0] +" "+ command[1]);
                displayHighestBid(b,Integer.parseInt(command[1]));
                System.out.println();
            }
            else if(command[0].equals("SellOneItem")) {
                System.out.print(command[0] + " "+ command[1] );
                sellItem(b);
                System.out.println();
            }
        }
    }

    /**
     * This method will check to see if the customer has already been added in order to check for duplicate error.
     * @param bid
     * @param name
     * @return
     */
    public static boolean customerCheck(HeapAdaptablePriorityQueue<Double, Customer> bid, String name) {
        for (Entry<Double, Customer> e : bid.getHeap()) { //using an enhanced for loop it iterate through the list so a comparison can be made.
            if (e.getValue().getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method will remove a bid for a specific person at a specific time.
     * @param bid
     * @param name
     */
    public static void removeBid(HeapAdaptablePriorityQueue<Double, Customer> bid, String name) {
        if (!customerCheck(bid, name)) {
            System.out.print(" "+ "NoCustomerError"); // prints error if no user is found.
        }
        else {
            for(Entry<Double, Customer> e : bid.getHeap()) {
                if (e.getValue().getName().equals(name)) {
                    bid.remove(e);
                    return;
                }
            }
        }
    }

    /**
     * Changes the price or quantity or both at a requested time...
     * @param bid
     * @param name
     * @param num
     * @param num2
     */
    public static void changebid(HeapAdaptablePriorityQueue<Double, Customer> bid, String name, Double num, int num2) {
        if (!customerCheck(bid, name)) {
            System.out.print(" "+ "NoCustomerError"); // prints error if no user is found.
        }
        else {
            for(Entry<Double, Customer> e : bid.getHeap()) {
                bid.min();
                if (e.getValue().getName().equals(name)) {
                    bid.replaceKey(e, num);
                    e.getValue().price = num;
                    e.getValue().quantity = num2;
                    //replaces the key value(price) of the user on request.
                    // System.out.printf(" %.2f", Double.parseDouble(e.getKey().toString()));
                }

            }
        }
    }

    /**
     * This method will be used to display the highest bid...
     * @param bid
     * @param time
     */
    @SuppressWarnings("unused")
    public static void displayHighestBid(HeapAdaptablePriorityQueue<Double, Customer> bid, int time) { 
        //uses the heap which is returned as an arraylist to get the value of the first entry which has the highest key.
        for (int i = 0; i < bid.getHeap().size(); i++) { // yes i know this is unnecessary, was just easier on time.
            System.out.print(" "+ bid.getHeap().get(0).getValue().name + " " + bid.getHeap().get(0).getValue().price+"0"+
                    " "+ bid.getHeap().get(0).getValue().quantity);
            return;
        }
    }

    /**
     * Method used to display minimum acceptable value...
     * @param bid
     */
    public static void updateMin(Double t) {
        updatedprice = t; // global variable stores the value of the updatedminimum and is changed as a new entry is made.
    }

    /**
     * Handles the sale of an item when a random time is given by the sellers.
     * @param bid
     */
    public static void sellItem(HeapAdaptablePriorityQueue<Double, Customer> bid) {
        bid.min();
        if (bid.isEmpty()) {
            System.out.print(" "+ "NoBids"); //output for empty list when trying to sell
        }
        if (!bid.isEmpty()) {
            if (updatedprice > bid.getHeap().get(0).getValue().price) {
                System.out.print(" "+"HighestBiddingPriceIsTooLow"); // output when the highest bid is too low for a sale.
            }
            else {
                System.out.print(" "+ bid.getHeap().get(0).getValue().name +" "+bid.getHeap().get(0).getValue().price+"0"); //output statement for selling an item.
                bid.getHeap().get(0).getValue().quantity = bid.getHeap().get(0).getValue().quantity - 1 ;
                if (bid.getHeap().get(0).getValue().quantity == 0) {
                    removeBid(bid, bid.getHeap().get(0).getValue().name);
                }
            }
        }
    }
}

