import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class extends the BasicManager class.
 * it has manager with name, SIN, and Employee ID and a list of students
 */
public class Manager extends BasicManager {

    /**
     * a lis tof all the studem=nt of the manager
     */
    private ArrayList<Student> students;

    /**
     * constructor to initialize the class
     * @param aname name of the manager
     * @param ssn social insurance number of the manager
     * @param employeeID employee ID of the manager
     */
    public Manager(String aname, int ssn, String employeeID){

        super(aname,ssn,employeeID);
        students = new ArrayList<Student>();
    }

    /**
     * add student to the list
     * @param s student needed to added
     */
    public void addStudent(Student s){
        if (hasStudent(s.getNSID()))
            throw new IllegalStateException("Student " + s.getNSID()+ " is already added " + getName());
        students.add(s);
    }

    /**
     * Remove the student from the list of this NSID
     * @param NSID of the student needed to remove
     */
    public void removeStudent(String NSID){
        if (!hasStudent(NSID))
            throw new NoSuchElementException("There is no student with NSID " + NSID + " to this manager");

        for (int i = 0; i< students.size(); i++){
            if(students.get(i).getNSID().equals(NSID)){
                students.remove(i);
            }
        }
    }

    /**
     * checks to see if a Student with NSID is under the manager’s care.
     * @param NSID of the student to check
     * @return true if student is assigned to maanger, false otherwise
     */
    public boolean hasStudent(String NSID){
        for(int i = 0; i< students.size(); i++){
            if(students.get(i).getNSID().equals(NSID)){
                return true;
            }
        }
        return false;
    }

    /**
     * returns a string representation of all the information about the manager in a form suitable for printing
     * @return a string representing all the information about the manager
     */
    public String toString(){
        String output = super.toString();
        output += "Assigned Students: ";

        if(!students.isEmpty()) {
            for (int i = 0; i < students.size(); i++) {
                output +=  students.get(i).getNSID() + ", ";
            }
        }else{
            output += "No student assigned.";
        }
        return output;
    }

    /**
     * a main method to test all the features
     */
    public static void main(String[] args) {

        //test cases for all the methods
        int numErrors = 0;
        String mName, mEN, sName, sNSID;
        int mSIN,sSIN;

        Student s2;
        Manager m2;

        mName = "Harry";
        mSIN = 8585;
        mEN = "122";
        m2 = new Manager(mName,mSIN,mEN);

        if(m2.students.size() != 0){
            System.out.println("Error in the constructor for the manager");
            numErrors++;
        }

        sName = "Nevil";
        sSIN = 111;
        sNSID = "abc123";
        s2 = new Student(sName, sSIN, sNSID);
        m2.addStudent(s2);
        if(!m2.hasStudent(sNSID)){
            System.out.println("Error in either hasStudent or addStudent");
            numErrors++;
        }

        sName = "Rickey";
        sSIN = 112;
        sNSID = "dbc123";
        s2 = new Student(sName, sSIN, sNSID);
        m2.addStudent(s2);
        if(!m2.hasStudent(sNSID)){
            System.out.println("Error in either hasStudent or addStudent");
            numErrors++;
        }

        m2.removeStudent(sNSID);
        if(m2.students.size() != 1){
            System.out.println("Error in remove student");
            numErrors++;
        }

        String output = "\nName: " + mName + "\nSSN: " + mSIN + "\nEmployee Id: " + mEN + "\nAssigned Students: " + "abc123" + ", ";
        if(!m2.toString().equals(output)){
            System.out.println("Error in toString method");
            numErrors++;
        }
        System.out.println("The number of errors found is " + numErrors);
    }
}
