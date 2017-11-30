
public class Customer {
	public Customer() {
		super();
	}

	private int _custId;
	private String _firstName;
	private String _midName;
	private String _lastName;
	private int _frequentRenterPoint;

	public Customer(int custId, String firstName, String midName, String lastName, int point) {
		_custId = custId;
		_firstName = firstName;
		_midName = midName;
		_lastName = lastName;
		_frequentRenterPoint = point;
	}

	public void add_frequentRenterPoint(int day) {
		_frequentRenterPoint += day;
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
}
