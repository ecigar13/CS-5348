package studentClassification;

public class Junior extends Sophomore {

	public Junior(int id, String name, double gpa) {
		super(id, name, gpa);
		// TODO Auto-generated constructor stub
		tuition = 45000.0;
	}

	@Override
	public void showTuition() {
		System.out.println(id + " " + name +" "+ gpa + " "+tuition + "<--- tuition went down");
	}

}
