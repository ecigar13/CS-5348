
import java.util.ArrayList;

import utilities.Pair;

import java.io.*;

public class Rental {
	private Customer _cust;
	private ArrayList<Pair<RentalItem, Integer>> _rentals;
	double _total;

	public Rental(Customer cust) {
		_cust = cust;
		_total = 0.0;
		_rentals = new ArrayList<Pair<RentalItem, Integer>>();
	}

	public void addRental(Pair<RentalItem, Integer> item) {
		_rentals.add(item);
	}

	public void addPoint(Pair<RentalItem, Integer> temp) {
		if (temp.getR() > 1)
			_cust.add_frequentRenterPoint(1);
	}

	public void calculateAll() {
		for (int i = 0; i < _rentals.size(); i++) {
			Pair<RentalItem, Integer> temp1 = _rentals.get(i);
			
			_total += temp1.getL().calculatePrice(temp1.getR());
			_cust.add_frequentRenterPoint(1);
			
			if (temp1.getL().isNew() && temp1.getR().intValue()> 1) {
				_cust.add_frequentRenterPoint(1);
			}
		}
	}

	public void printStatement() {
		for (int i = 0; i < _rentals.size(); i++) {
			Pair<RentalItem, Integer> temp = _rentals.get(i);
			printItem(temp);
		}
	}
	
	public void printHtmlStatement() {
		for (int i = 0; i < _rentals.size(); i++) {
			Pair<RentalItem, Integer> temp = _rentals.get(i);
			printItem(temp);
			newLine();
		}
	}

	/**
	 * This program prints the movie title, value and price.
	 * @param temp
	 */
	public void printItem(Pair<RentalItem, Integer> temp) {
		System.out.println(temp.getL().get_title() + "\t\t\t\t\t" + temp.getR().intValue() + "\t\t\t\t\t"
				+ temp.getL().calculatePrice(temp.getR().intValue()));
	}

	public void printReceipt() {

		System.out.println("Amount owed is " + Math.ceil(_total * 100) / 100.0);
		System.out.println("You earned " + _cust.get_frequentRenterPoint() + " frequent renter points.");

	}

	/*
	 * This prints the receipt to the html file.
	 */
	public void print(String fileName) throws FileNotFoundException {
		PrintStream o = new PrintStream(new File(fileName));
		PrintStream console = System.out;
		System.setOut(o);

		System.out.println("<html><body>");
		calculateAll();
		printHtmlStatement();
		newLine();
		printReceipt();
		
		System.out.println("Customer: ");
		_cust.printCustomer();
		System.out.println("</body></html>");

		System.setOut(console);
		o.close();
	}
	
	/*
	 * Add <br/> tag in html file
	 */
	private void newLine() {
		System.out.println("<br/>");
	}
	public Customer get_cust() {
		return _cust;
	}

	public void set_cust(Customer _cust) {
		this._cust = _cust;
	}

	public ArrayList<Pair<RentalItem, Integer>> get_rentals() {
		return _rentals;
	}

	public void set_rentals(ArrayList<Pair<RentalItem, Integer>> _rentals) {
		this._rentals = _rentals;
	}

	public double get_total() {
		return _total;
	}

	public void set_total(double _total) {
		this._total = _total;
	}
}