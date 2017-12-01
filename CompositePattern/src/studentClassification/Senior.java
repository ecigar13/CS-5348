package studentClassification;

public class Senior extends Junior{

	public Senior(int id, String name, double gpa) {
		super(id, name, gpa);
		// TODO Auto-generated constructor stub
		tuition = 40000.0;
	}

	@Override
	public void showTuition() {
		System.out.println(id + " " + name +" "+ gpa + " "+tuition + "<--- tuition went down");
	}

}
