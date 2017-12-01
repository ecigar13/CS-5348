package studentClassification;

public class GraduateSecondYear extends GraduateFirstYear {

	public GraduateSecondYear(int id, String name, double gpa) {
		super(id, name, gpa);
		tuition = 65000.0;
	}

	@Override
	public void showTuition() {
		System.out.println(id + " " + name +" "+ gpa + " "+tuition + "<--- tuition went up");
	}
}
