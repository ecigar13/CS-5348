package studentClassification;

public class Undergraduate implements Student {
	protected double tuition;
	protected int id;
	protected String name;
	protected double gpa;

	public Undergraduate(int id, String name, double gpa) {
		this.id = id;
		this.name = name;
		this.gpa = gpa;
		tuition = 47000.0;
	}

	@Override
	public void showTuition() {
		System.out.println(id + " " + name +" "+ gpa + " "+tuition);
	}

}
