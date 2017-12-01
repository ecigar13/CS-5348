package studentClassification;
import java.util.ArrayList;
import java.util.List;

public class StudentDirectory implements Student{
private List<Student> studentList = new ArrayList<Student>();
	public StudentDirectory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void showTuition() {
		for(Student s: studentList)
			s.showTuition();
	}

	public void addStudent(Student s) {
		studentList.add(s);
	}
	
	public void removeStudent(Student s) {
		studentList.remove(s);
	}
}
