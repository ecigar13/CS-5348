
public class ChildrenMovieDecorator extends MovieDecorator {

	public ChildrenMovieDecorator(int id, String title, int priceCode) {
		super(id, title);
		// TODO Auto-generated constructor stub
		_dayLimit=3;
		_price = 1.5;
	}

	public double calculatePrice(int day) {
		if(day >_dayLimit)
			return _price + (day - _dayLimit) * _multiplier;
		else return _price;
		
	}
}
