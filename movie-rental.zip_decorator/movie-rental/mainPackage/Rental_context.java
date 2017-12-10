package mainPackage;

import java.util.ArrayList;

import rentalItems.RentalItem;
import utilities.Pair;

import java.io.*;

public class Rental_context {
	private Customer _cust;
	private ArrayList<Pair<RentalItem, Integer>> _rentals;
	RentalItem _strategy;
	
	

	double _total;
	int _rentMoreThanOneType;

	public Rental_context(Customer cust) {
		_cust = cust;
		_total = 0.0;
		_rentals = new ArrayList<Pair<RentalItem, Integer>>();
		_rentMoreThanOneType = 0;
	}

	public void addRental(Pair<RentalItem, Integer> item) {
		if (_rentals.size() > 0)
			if (item.getL().getClass() != _rentals.get(_rentals.size() - 1).getL().getClass())
				_rentMoreThanOneType++;
		_rentals.add(item);

	}


	public void calculateAll() {
		for (int i = 0; i < _rentals.size(); i++) {
			Pair<RentalItem, Integer> temp1 = _rentals.get(i);
			_total += temp1.getL().calculatePrice(temp1.getR());
			int temp = temp1.getL().calculateRentalPoint(_cust, _rentMoreThanOneType);

			_cust.add_frequentRenterPoint(temp);
			System.out.println(_cust.get_frequentRenterPoint());
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
	 * 
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
	public RentalItem getStrategy() {
		return _strategy;
	}
	public void setStrategy(final RentalItem item) {
		_strategy = item;
	}
}