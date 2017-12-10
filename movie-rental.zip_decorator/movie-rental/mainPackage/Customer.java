package mainPackage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import utilities.*;

public class Customer implements Observer{
	public Customer() {
		super();
	}

	private int _custId;
	private String _firstName;
	private String _midName;
	private String _lastName;
	private int _age;
	private int _frequentRenterPoint;
	private String toEmail = "dummyEmail@gmail.com";   //this can be part of the constructor

	public Customer(int custId, String firstName, String midName, String lastName, int point, int age) {
		_custId = custId;
		_firstName = firstName;
		_midName = midName;
		_lastName = lastName;
		_frequentRenterPoint = point;
		_age = age;
	}

	public void saveCust(String fileName) throws FileNotFoundException {
		PrintStream o = new PrintStream(new File(fileName));
		PrintStream console = System.out;
		System.setOut(o);

		System.out.println(_custId);
		System.out.println(_firstName);
		System.out.println(_midName);
		System.out.println(_lastName);
		System.out.println(_frequentRenterPoint);
		System.out.println(_age);
		System.setOut(console);
		o.close();
	}
	
	public void add_frequentRenterPoint(int point) {
		_frequentRenterPoint += point;
	}
	
	public void printCustomer() {
		System.out.println(_firstName + " "+ _midName + " "+_lastName);
		System.out.println("Frequent Renter Point: "+ _frequentRenterPoint);
	}

	public int get_custId() {
		return _custId;
	}

	public void set_custId(int _custId) {
		this._custId = _custId;
	}

	public String get_firstName() {
		return _firstName;
	}

	public void set_firstName(String _firstName) {
		this._firstName = _firstName;
	}

	public String get_midName() {
		return _midName;
	}

	public void set_midName(String _midName) {
		this._midName = _midName;
	}

	public String get_lastName() {
		return _lastName;
	}

	public void set_lastName(String _lastName) {
		this._lastName = _lastName;
	}

	public int get_frequentRenterPoint() {
		return _frequentRenterPoint;
	}

	public void set_frequentRenterPoint(int _frequentRenterPoint) {
		this._frequentRenterPoint = _frequentRenterPoint;
	}

	@Override
	/*
	 * This method gets the email from the customer and email to alert them that their favorite movie is available
	 * This is the observer/obervable design pattern.
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		Emailer mail = new Emailer(toEmail,"fromEmail@gmail.com", "localhost" ,arg);
		mail.sendEmail();
	}

	/**
	 * @return the _age
	 */
	public int get_age() {
		return _age;
	}

	/**
	 * @param _age the _age to set
	 */
	public void set_age(int _age) {
		this._age = _age;
	}
}
