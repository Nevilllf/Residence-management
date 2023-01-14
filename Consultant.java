
/**
 * This class extends the Manager class
 */
public class Consultant extends Manager{

    /**
     * a constructor to initialize the class with given information
     * @param mName name of the manager
     * @param mSIN social insurance number of the manager
     * @param mEmployeeID of the manager
     */
    public Consultant(String mName, int mSIN, String mEmployeeID){
        super(mName,mSIN,mEmployeeID);
    }

    /**
     * A method that returns a string representation of all the information about the manager in a form suitable for printing
     * @return a string representing all the information
     */
    public String toString(){
        return "\nConsultant: " + super.toString();
    }

    /**
     * a main method to test all the features
     */
    public static void main(String[] args) {

        //test case to test all the methods
        int numErrors =0;
        String mName, mEN;
        int mSIN;
        Consultant c;

        mName = "Jhon";
        mSIN = 456;
        mEN = "555";
        c = new Consultant(mName,mSIN, mEN);

        String output = "\nConsultant: " +"\nName: " + mName + "\nSSN: " + mSIN + "\nEmployee Id: " + mEN + "\nAssigned Students: " + "No student assigned." ;
        if(!c.toString().equals(output)){
            System.out.println("Error in toString method");
            System.out.println(c.toString());
            System.out.println(output);
            numErrors++;
        }

        System.out.println("The number of errors found is " + numErrors);
    }

}
