package mainPackage;


public class Context {
private Strategy strategy;

	public Context(Strategy strategy) {
	this.strategy=strategy;
	}

	public int executeStrategy(Customer cust, int rentMoreThanOneType) {
		return strategy.calculateRentalPoint(cust, rentMoreThanOneType); //perform the operation
	}
}
