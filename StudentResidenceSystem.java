

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * student residence system to manage all the task
 */
public class StudentResidenceSystem {

    /**
     * intializing the object of residence
     */
    private Residence residence;

    /**
     * a tree map for all students with NSID as key and object of student as value
     */
    private TreeMap<String, Student> students;

    /**
     * a treemap for all manager with employeeID as key and object manager as value
     */
    private TreeMap<String, Manager> managers;

    /**
     * initialization of a scanner
     */
    private static Scanner consoleInput  = new Scanner(System.in);


    /**
     * constructor to initialize student residence system
     * initialize new student and manager tree map
     * takes input from the user
     */
    public StudentResidenceSystem(){

        students = new TreeMap<String, Student>();
        managers = new TreeMap<String, Manager>();

        System.out.println();
        System.out.println("Enter the name for Residence Name: ");
        String rName = consoleInput.nextLine();

        System.out.println("Enter label for the first bed :");
        int minBedLabel = consoleInput.nextInt();

        System.out.println("Enter label for last bed :");
        int maxBedLabel = consoleInput.nextInt();

        residence = new Residence(rName, minBedLabel, maxBedLabel);

    }

    /**
     * takes input from the users and add the student to the tree map
     */
    public void addStudent(){

        System.out.println("Enter the name of the student : ");
        String sName = consoleInput.next();

        System.out.println("Enter the social insurance number of the student : ");
        int sSIN = consoleInput.nextInt();

        System.out.println("Enter the NSID of the Student : ");
        String sNSID = consoleInput.next();

        if (students.containsKey(sNSID)) {
            throw new IllegalStateException("Student with this NSID already exists");
        }

        Student s = new Student(sName, sSIN, sNSID);
        students.put(s.getNSID(), s);
        System.out.println("*Student added successfully*\n");
    }

    /**
     * takes input from the users and add the manager to the tree map
     */
    public void addManager(){
        System.out.println("Enter the manager name : ");
        String mName =  consoleInput.next();

        System.out.println("Enter the SIN of manger: ");
        int mSIN = consoleInput.nextInt();

        System.out.println("Enter the manager's employee number: ");
        String mEmployeeNum = consoleInput.next();
        if (managers.containsKey(mEmployeeNum)) {
            throw new IllegalStateException("Manager with this employee ID already exists");
        }

        System.out.println("Is this manager a consultant? Y or N :");
        String type = consoleInput.next();
        if(!(type.charAt(0) == 'y' || type.charAt(0) == 'Y' || type.charAt(0) == 'N' || type.charAt(0) == 'n')){
            do{
                System.out.println("Please enter correct input : ");
                type = consoleInput.next();
            }
            while (!(type.charAt(0) == 'y' || type.charAt(0) == 'Y' || type.charAt(0) == 'N' || type.charAt(0) == 'n'));
        }

        if(type.charAt(0) == 'y' || type.charAt(0) == 'Y'){
            Manager m = new Consultant(mName , mSIN, mEmployeeNum);
            managers.put(m.getEmployeeId(),m);
            System.out.println("*Consultant added successfully*\n");
        }
        else{
            Manager m = new Manager(mName, mSIN, mEmployeeNum);
            managers.put(m.getEmployeeId(),m);
            System.out.println("*Manager added successfully*\n");
        }
    }

    /**
     * Assign a manager to the student of given NSID taken form user
     */
    public void assignManagerToStudent(){
        System.out.println("Enter the student's NSID: ");
        String sNSID = consoleInput.next();

        Student s = students.get(sNSID);
        if(s==null){
            throw new NoSuchElementException("Invalid NSID of the student " + sNSID);
        }

        System.out.print("Enter the employee number of the manager: ");
        String mEmployeeNum = consoleInput.next();

        Manager m = managers.get(mEmployeeNum);
        if(m == null){
            throw new NoSuchElementException("Invalid manager employee number " + mEmployeeNum);
        }
        s.addManager(m);
        m.addStudent(s);
        System.out.println("*Manager assigned successfully*\n");
    }

    /**
     * assign a bed to student
     */
    public void assignBed() {
        System.out.println("Enter the NSID of the student ");
        String sNSID = consoleInput.next();

        Student s = students.get(sNSID);
        if (s == null) {
            throw new NoSuchElementException("Invalid NSID of the student " + sNSID);
        }
        if (s.getBedLabel() != -1) {
            throw new IllegalStateException("Student already assign to a bed.");
        }

        System.out.println("Enter the bed number of the student: ");
        int num = consoleInput.nextInt();

        if (residence.isOccupied(num)) {
            throw new IllegalStateException("This bed is already assign to a student");
        } else {
            s.setBedLabel(num);
            residence.assignPersonToBed(s, num);
        }
        System.out.println("*Bed assigned successfully*\n");
    }

    /**
     * Drop manager-student association
     */
    public void dropAssociation() {
        System.out.println("Enter the NSID of the student : ");
        String sNSID = consoleInput.next();

        Student s = students.get(sNSID);
        if (s == null) {
            throw new NoSuchElementException("Invalid NSID of the student " + sNSID);
        }

        System.out.println("Enter the employee number of the manager : ");
        String mEmployeeNum = consoleInput.next();

        Manager m = managers.get(mEmployeeNum);
        if (m == null) {
            throw new NoSuchElementException("Invalid manager employee number " + mEmployeeNum);
        }

        s.removeManager(mEmployeeNum);
        m.removeStudent(sNSID);
        System.out.println("*Manager-student association dropped*\n");
    }

    /**
     * Display current system state
     */
    public void systemState() {
        System.out.println(this.toString());
    }

    /**
     * Display the empty beds of the residence
     */
    public void displayEmptybeds() {
        for(int n : residence.availableBeds()){
            System.out.print("Empty beds : " + n + ", ");
        }
    }

    /**
     * release the student from the residence
     */
    public void releaseStudent() {
        System.out.println("Enter the NSID of the Sudent : ");
        String sNSID = consoleInput.next();

        if (!students.containsKey(sNSID)) {
            throw new IllegalStateException("Student with this NSID does not exists");
        }

        if(students.get(sNSID).getBedLabel() == -1){
            throw new IllegalStateException("Student not assign to any bed yet!");
        }else{
        residence.freeBed(students.get(sNSID).getBedLabel());}
        students.remove(sNSID);
        System.out.println("*Student released*\n");
    }

    /**
     * return a string representing all the information about residence
     * @return a string of all the information of residence
     */
    public String toString() {
        String output = "Residence name : " + residence.getName() + "\nFirst bed label : " + residence.getMinBedLabel() + "\nLast bed label : " + residence.getMaxBedLabel() + "\nTotal number of beds : " + (residence.getMaxBedLabel() - residence.getMinBedLabel() + 1 + "\n");
        output += "\nStudents : ";
        Collection<Student> st = students.values();
        for (Student n : st) {
            output += n.toString() +"\n";
        }
        output += "\nManagers : ";
        Collection<Manager> mg = managers.values();
        for (Manager k : mg) {
            output += k.toString() + "\n";
        }
        return output;
    }

    /**
     * a main method construct a new instanceof residence system to carry out different class
     */
    public static void main(String[] args) {

        System.out.println("---System running---");
        System.out.println("*The Student Residence Management System*\n");
        StudentResidenceSystem system1 = new StudentResidenceSystem();
        int task;
        do {
            System.out.println("\nSelect Task : \n"
                    + "1. Quit \n"
                    + "2. Add a new student to the system\n"
                    + "3. Add a new manager to the system\n"
                    + "4. Assign a manager to a student \n"
                    + "5. Display the empty beds of the residence\n"
                    + "6. Assign a student a bed\n"
                    + "7. Release a student from the residence\n"
                    + "8. Drop manager-student association\n"
                    + "9. Display current system state\n");

            task = consoleInput.nextInt();

            if (task != 1) {
                switch (task) {
                    case 2:
                        system1.addStudent();
                        break;
                    case 3:
                        system1.addManager();
                        break;
                    case 4:
                        system1.assignManagerToStudent();
                        break;
                    case 5:
                        system1.displayEmptybeds();
                        break;
                    case 6:
                        system1.assignBed();
                        break;
                    case 7:
                        system1.releaseStudent();
                        break;
                    case 8:
                        system1.dropAssociation();
                        break;
                    case 9:
                        system1.systemState();
                        break;
                    default:
                        System.out.println("Invalid option! Try again: ");
                        break;
                }
            }
            else if (task == 1) {
                system1.systemState();
            }
        }
        while (task != 1);

        System.out.println("---System closed---");
    }
}


