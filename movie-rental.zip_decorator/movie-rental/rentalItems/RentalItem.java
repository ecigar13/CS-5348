package rentalItems;

import mainPackage.Strategy;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import mainPackage.*;

public class RentalItem extends Observable implements Strategy {

	public RentalItem() {
		super();
	}

	private int _id;
	private String _title;
	private int _priceCode;

	protected double _multiplier = 1.5;
	protected int _pointMultiplier;
	protected int _dayLimit;
	protected double _price;
	protected boolean _newItem = false;
	protected ArrayList<Observer> users = new ArrayList<Observer>();

	/*
	 * This function will be changed if we use the decorators.
	 */
	public double calculatePrice(int day) {
		if (day > 2)
			return _price + _priceCode + (day - 2) * _multiplier;
		else
			return _price;
	}

	public RentalItem(int id, String title) {
		_id = id;
		_title = title;

		_pointMultiplier = 1;
		_price = 2;

	}

	public int calculateRentalPoint(Customer cust, int rentMoreThanOneType) {
		boolean criteria1 = (cust.get_age() >= 18 && cust.get_age() <= 22);
		boolean criteria2 = criteria1 && this.getClass() == (new NewMovieDecorator().getClass());
		if(criteria2 || rentMoreThanOneType!=0)
			return 2;
		else return 1;
	}

	
	public int get_id() {
		return _id;
	}

	public boolean isNew() {
		return _newItem;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public int get_priceCode() {
		return _priceCode;
	}

	public void set_priceCode(int _priceCode) {
		this._priceCode = _priceCode;
	}

	public double get_multiplier() {
		return _multiplier;
	}

	public void set_multiplier(double _multiplier) {
		this._multiplier = _multiplier;
	}

	public int get_pointMultiplier() {
		return _pointMultiplier;
	}

	public void set_pointMultiplier(int _pointMultiplier) {
		this._pointMultiplier = _pointMultiplier;
	}

	public int _dayLimit() {
		return _dayLimit;
	}

	public void set_dayLimit(int _dayLimit) {
		this._dayLimit = _dayLimit;
	}

}
