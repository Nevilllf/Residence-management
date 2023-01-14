
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This class extends the Person class
 * It has an additional list of managers and bed label with the initial value of -1 if not asssign
 */
public class Student extends Person {

    /**
     *the instance integer variable for bed label
     */
    private int bedLabel;

    /***
     * a list of managers of the student
     */
    private ArrayList<Manager> managers;

    /**
     * variable NSID for the student's ID, initially value of null
     */
    private String NSID;

    /**
     * constructor to initialize all the instance variable
     * @param aname name of the student
     * @param ssn social security number of the student
     * @param aNSID NSID of the student
     */
    public Student(String aname, int ssn, String aNSID) {
        super(aname, ssn);

        bedLabel = -1;
        NSID = aNSID;
        managers = new ArrayList<Manager>();
    }

    /**
     * returns the NSID of the student
     * @return the NSID of the student
     */
    public String getNSID() {
        return NSID;
    }

    /**
     * returns the bed label of the bed occupied by the student
     * @return bed label
     */
    public int getBedLabel() {
        return bedLabel;
    }

    /**
     * assign the bed label tp the student
     * @param bedLabel integer of the bed label
     */
    public void setBedLabel(int bedLabel) {
        this.bedLabel = bedLabel;
    }

    /**
     * add manager to the list
     * @param m manager that will be added
     */
    public void addManager(Manager m) {
        if (hasManager(m.getEmployeeId()))
            throw new IllegalStateException(m.getName()+ " is already a manager for this student");
        managers.add(m);
    }

    /**
     * Removes the manager from the list
     * @param employeeID employeeID of the manager needed to remove
     */
    public void removeManager(String employeeID) {
        if (!this.hasManager(employeeID))
            throw new NoSuchElementException(employeeID + " is not a manager for this student");
        for (int i = 0; i < managers.size(); i++) {
            if (managers.get(i).getEmployeeId().equals(employeeID)) {
                managers.remove(i);
            }
        }
    }

    /**
     * hecks to see if a Manager with employeeId is assigned to the student.
     * @param employeeID of the manager to check
     * @return true if student is assign to that manager, false otherwise
     */
    public boolean hasManager(String employeeID) {
        for (int i = 0; i < managers.size(); i++) {
            if (managers.get(i).getEmployeeId().equals(employeeID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns a string representing all the information about student
     * @return a string of all information of the student
     */
    public String toString() {
        String output = super.toString();
        output += "NSID: " + this.NSID;
        if (bedLabel != -1) {
            output += "\nBed: " + bedLabel;
        } else {
            output += "\nBed : not assigned";
        }
        for (int i = 0; i < managers.size(); i++) {
            output += "\nManagers: " + managers.get(i).getEmployeeId() + ", ";
        }
        return output;
    }

    /**
     * a main method to test all the features
     * @param args
     */
    public static void main(String[] args) {

        //testing all the methods
        int numErrors =0;
        String sName, sNSID, mName, mEN;
        int bedLabel, sSIN, mSIN;

        Student s1;
        Manager m1;

        sName = "Nevil";
        sSIN = 123123;
        sNSID = "111";
        bedLabel = 3;

        s1 = new Student(sName, sSIN, sNSID);

        if (s1.managers.size() != 0) {
            System.out.println("constructor for student is not working for student ");
            numErrors++;
        }

        if (s1.getBedLabel() != -1) {
            System.out.println("constructor for student is not working for student ");
            numErrors++;
        }

        s1.setBedLabel(bedLabel);
        if (s1.getBedLabel() != bedLabel) {
            System.out.println("Initial constructor for student is not working for student ");
            numErrors++;
        }

        mName = "Alex";
        mSIN = 2222;
        mEN = "555";

        m1 = new Manager(mName, mSIN, mEN);

        s1.addManager(m1);
        if (!s1.hasManager(mEN)) {
            System.out.println("Error in either hasManager or addManager method! 1");
            numErrors++;
        }
        if (s1.managers.size() != 1) {
            System.out.println("Error in  the list of manager ");
            numErrors++;
        }
        if (!s1.managers.get(0).getEmployeeId().equals(m1.getEmployeeId())) {
            System.out.println("Error in returning corrrect manager's employeeID!");
            numErrors++;
        }

        mName = "David";
        mSIN = 19919;
        mEN = "875";

        m1 = new Manager(mName, mSIN, mEN);

        s1.addManager(m1);
        if (!s1.hasManager(mEN)) {
            System.out.println("Error in either hasManager or addManager method! 3");
            numErrors++;
        }
        if (s1.managers.size() != 2) {
            System.out.println("Error in  the list of manager ");
            numErrors++;
        }

        s1.removeManager(mEN);
        if (s1.hasManager(mEN)) {
            System.out.println("Error in either hasManager or removeManager method! 4");
            numErrors++;
        }

        String output = "\nName: " + sName + "\nSSN: "+ sSIN + "\nNSID: " + sNSID + "\nBed: " + bedLabel + "\nManagers: " +  "555" + ", ";
        if(!s1.toString().equals(output)){
            System.out.println("Error in tostring method");
            numErrors++;
        }

        System.out.println("The number of errors found is " + numErrors);

    }
}

