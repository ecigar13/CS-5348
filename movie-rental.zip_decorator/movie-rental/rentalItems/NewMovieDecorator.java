package rentalItems;

public class NewMovieDecorator extends NewRentalItemDecorator {

	public NewMovieDecorator() {
		
	}
	public NewMovieDecorator(int id, String title) {
		super(id, title);
		// no change to calculation because we only deal with movie. 
		//But we can implement other kinds of rental item functions as decorators.
	}

}
