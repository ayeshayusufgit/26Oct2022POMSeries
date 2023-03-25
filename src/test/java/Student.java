
public class Student {
	int roll;
	String name;
	DOB db;

	void displayStudentDetails() {
		System.out.println(roll+" "+name+" "+db);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student s = new Student();
		s.roll = 10;
		s.name="Ayesha";
		s.db = new DOB();
		//Student.DOB db=new Student.DOB();
		
		s.db.dd = 05;
		s.db.mm = 12;
		s.db.yyyy = 2000;
		s.displayStudentDetails();
		s.db.displayDOB();
		
		
		Student[] studArray = new Student[10];
		studArray[0].roll = 10;
		studArray[0].name="Ayesha";
		studArray[0].db = new DOB();
		//Student.DOB db=new Student.DOB();
		
		studArray[0].db.dd = 05;
		studArray[0].db.mm = 12;
		studArray[0].db.yyyy = 2000;
		studArray[0].displayStudentDetails();
		studArray[0].db.displayDOB();
	}

}
