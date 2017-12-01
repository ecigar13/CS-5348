import studentClassification.Sophomore;
import studentClassification.Freshmen;
import studentClassification.GraduateFirstYear;
import studentClassification.GraduateSecondYear;
import studentClassification.Junior;
import studentClassification.Senior;
import studentClassification.StudentDirectory;
import studentClassification.Undergraduate;

public class tuition {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GraduateFirstYear s1 = new GraduateFirstYear(1, "Khalid", 4.0);
		GraduateFirstYear s2 = new GraduateFirstYear(1, "Farhan", 4.0);
		StudentDirectory gradDir1 = new StudentDirectory();
		gradDir1.addStudent(s1);
		gradDir1.addStudent(s2);
		
		GraduateSecondYear s5 = new GraduateSecondYear(1, "Ambili", 3.2);
		GraduateSecondYear s6 = new GraduateSecondYear(1, "Smita", 3.0);
		StudentDirectory gradDir2 = new StudentDirectory();
		gradDir2.addStudent(s5);
		gradDir2.addStudent(s6);
		
		StudentDirectory gradDir = new StudentDirectory();
		gradDir.addStudent(gradDir1);
		gradDir.addStudent(gradDir2);

		Freshmen s3 = new Freshmen(1, "Eline", 2.0);
		Sophomore s4 = new Sophomore(1, "Ling", 1.0);
		StudentDirectory ugradDir1 = new StudentDirectory();
		ugradDir1.addStudent(s3);
		ugradDir1.addStudent(s4);

		Junior s7 = new Junior(1, "Marissa", 3.5);
		Senior s8 = new Senior(1, "Meredith", 3.9);
		StudentDirectory ugradDir2 = new StudentDirectory();
		ugradDir2.addStudent(s7);
		ugradDir2.addStudent(s8);
		
		StudentDirectory ugradDir = new StudentDirectory();
		ugradDir.addStudent(ugradDir1);
		ugradDir.addStudent(ugradDir2);
		
		StudentDirectory studentDir = new StudentDirectory();
		studentDir.addStudent(gradDir);
		studentDir.addStudent(ugradDir);
		studentDir.showTuition();
	}

}
