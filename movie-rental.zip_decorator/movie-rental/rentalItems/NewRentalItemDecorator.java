package rentalItems;

public class NewRentalItemDecorator extends RentalItem {

	public NewRentalItemDecorator() {
	}

	public NewRentalItemDecorator(int id, String title) {
		super(id, title);
		_multiplier = 3.0;
		_newItem = true;
		// set_pointMultiplier(2);
	}

	public double calculatePrice(int day) {
		return day * _multiplier;
	}
}
