
import java.util.ArrayList;

/**
 * A Residence of a hospital with a specified number of beds with consecutive labels.
 */
public class Residence
{
    /**
     * The name of this Residence.
     */
    private String name;

    /**
     * The (external) label of the first bed of the Residence.
     */
    private int minBedLabel;

    /**
     * An array to represent the beds of the Residence.  Each bed is empty (null)
     * or else has a Person in it.
     */
    private Person[] beds;

    /**
     * Initialize the Residence with the name given, and with beds those labels are
     * the consecutive integers from minBedLabel to maxBedLabel.
     * @param wName    the name of the Residence
     * @param wMinBedLabel the label of the first bed in the Residence
     * @param wMaxBedLabel the label of the last bed in the Residence
     * @precond wName != null && !wName.equals("") && wMinBedLabel >= 0 && wMaxBedLabel >= wMinBedLabel
     */
    public Residence(String wName, int wMinBedLabel, int wMaxBedLabel)
    {
        if (wName == null || wName.equals(""))
            throw new IllegalArgumentException("The name of a Residence cannot be null or empty.  "
                    + "It is " + wName);
        if (wMinBedLabel < 0 || wMaxBedLabel < wMinBedLabel)
            throw new IllegalArgumentException("The bed labels " + wMinBedLabel + " and " + wMaxBedLabel
                    + " are invalid as they cannot be negative, and must have at least one bed.");

        name = wName;
        minBedLabel = wMinBedLabel;
        beds = new Person[wMaxBedLabel - wMinBedLabel + 1];
    }

    /**
     * Return the name of this Residence.
     * @return  the name of this Residence
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return the smallest label for a bed on the Residence.
     * @return  the smallest Label for a bed on the Residence
     */
    public int getMinBedLabel()
    {
        return minBedLabel;
    }

    /**
     * Return the largest label for a bed on the Residence.
     * @return  the largest label for a bed on the Residence
     */
    public int getMaxBedLabel()
    {
        return minBedLabel + beds.length - 1;
    }

    /**
     * Return the internal/array index of the bed corresponding to the external label.
     * @param bedLabel the label of a bed from the external/user perspective
     * @precond isValidLabel(bedLabel)
     * @return the internal/array index of the bed corresponding to the external label
     */
    private int externalToInternalIndex(int bedLabel)
    {
        if (! isValidLabel(bedLabel))
            throw new IllegalArgumentException("The value " + bedLabel
                    + " is not a valid label for a bed in the Residence.");

        return bedLabel - minBedLabel;
    }

    /**
     * Return the external/user label of the bed corresponding to the internal index.
     * @param arrayIndex the index of a location in the beds array
     * @precond 0 <= arrayIndex < beds.length
     * @return the external/user label of the bed corresponding to the internal index
     */
    private int internalToExternalLabel(int arrayIndex)
    {
        if (arrayIndex < 0 || arrayIndex >= beds.length)
            throw new IllegalArgumentException("The value " + arrayIndex +
                    " is not a valid index for an array of length " + beds.length + ".");

        return arrayIndex + minBedLabel;
    }

    /**
     * Is the specified bed occupied?
     * @param bedLabel  the label of the bed to be tested for being occupied
     * @precond isValidLabel(bedLabel)
     * @return  is the specified bed occupied?
     */
    public boolean isOccupied(int bedLabel)
    {
        if (! isValidLabel(bedLabel))
            throw new IllegalArgumentException("The value " + bedLabel
                    + " is not a valid label for a bed in the Residence.");

        return beds[externalToInternalIndex(bedLabel)] != null;
    }

    /**
     * Return the student in the specified bed.
     * @param bedLabel  the label of the bed that has the student to be retrieved
     * @precond isValidLabel(bedLabel) && isOccupied(bedLabel)
     * @return  the student in the specified bed
     */
    public Person getPerson(int bedLabel)
    {
        if (! isValidLabel(bedLabel))
            throw new IllegalArgumentException("The value " + bedLabel
                    + " is not a valid label for a bed in the Residence.");

        if (! isOccupied(bedLabel))
            throw new IllegalStateException("Bed " + bedLabel + " is not currently occupied"
                    + " so cannot get its student");
        return beds[externalToInternalIndex(bedLabel)];
    }

    /**
     * Assign the specified student to the specified bed.
     * @param p  the student to be assigned a bed
     * @param bedLabel  the label of the bed that the student is to be assigned
     * @precond  isValidLabel(bedLabel) && !isOccupied(bedLabel)
     */
    public void assignPersonToBed(Person p, int bedLabel)
    {
        if (! isValidLabel(bedLabel))
            throw new IllegalArgumentException("The value " + bedLabel
                    + " is not a valid label for a bed in the Residence.");

        if (isOccupied(bedLabel))
            throw new IllegalStateException("Bed " + bedLabel + " is currently occupied by "
                    + beds[externalToInternalIndex(bedLabel)]
                    + " so cannot be assigned to another student");

        beds[externalToInternalIndex(bedLabel)] = p;
    }

    /**
     * Return a String representation of the properties of the Residence.
     * @return a String representation of the properties of the Residence
     */
    public String toString()
    {
        String result = "\nResidence " + name + " with capacity " + beds.length
                + " has the following students: ";
        for (int i = 0; i < beds.length; i++)
        {
            result = result + "\nbed " + internalToExternalLabel(i) + ": ";
            if (beds[i] != null)
                result = result + beds[i].getName();
        }
        return result + "\n";
    }


    /**
     * Is bedLabel a valid external label for a bed?
     * @param bedLabel  an int to be tested to determined whether it is a valid label
     *             for a bed (from the external/user perspective)
     * @return  is bedLabel a valid external label for a bed?
     *
     * This is a helper method for testing pre-conditions students were not required to implement it
     */
    public boolean isValidLabel(int bedLabel)
    {
        return bedLabel >= minBedLabel && bedLabel <= minBedLabel + beds.length - 1;
    }

    /**
     *A method that returns a list of the empty beds in the residence
     * @return a list containing the bed label of the all the empty bed
     */
    public ArrayList<Integer> availableBeds(){
        ArrayList<Integer> emptyBeds = new ArrayList<Integer>();
        for(int i = minBedLabel; i<= minBedLabel+beds.length-1; i++){
            if(!isOccupied(i)){
                emptyBeds.add(i);
            }
        }
        return emptyBeds;
    }

    /**
     *  A method that removes a Student from a specific bed.
     * @param bedLabel bed label of the bed from student to remove
     */
    public void freeBed(int bedLabel){
        if (! isValidLabel(bedLabel))
            throw new IllegalArgumentException("The value " + bedLabel
                    + " is not a valid label for a bed in the Residence.");

        if (!isOccupied(bedLabel))
            throw new IllegalStateException("Bed " + bedLabel + " is not currently occupied"
                    + " so it cannot be freed");

        beds[externalToInternalIndex(bedLabel)] = null;
    }

    /**
     * A method to test the class.
     * @param args  not used
     */
    public static void main(String[] args)
    {
        int numErrors = 0;

        // testing all the methods with one instance of a Residence
        Residence residence = new Residence("Assiniboine", 200, 210);

        if(!residence.getName().equals("Assiniboine")) {
            System.out.println("The constructor or getName failed.");
            numErrors++;
        }

        if(residence.getMinBedLabel() != 200) {
            System.out.println("The constructor or getMinBedLabel failed.");
            numErrors++;
        }

        if(residence.getMaxBedLabel() != 210) {
            System.out.println("The constructor or getMaxBedLabel failed.");
            numErrors++;
        }


        if(!residence.isValidLabel(200) || !residence.isValidLabel(201)
                || !residence.isValidLabel(210) || !residence.isValidLabel(209)
                ||  residence.isValidLabel(199)
                ||  residence.isValidLabel(211)) {
            System.out.println("isValidLabel failed.");
            numErrors++;
        }

        if(residence.internalToExternalLabel(residence.externalToInternalIndex(200)) != 200
                || residence.internalToExternalLabel(residence.externalToInternalIndex(210)) != 210
                || residence.internalToExternalLabel(residence.externalToInternalIndex(205)) != 205) {
            System.out.println("internalToExternalLabel failed.");
            numErrors++;
        }

        if(residence.externalToInternalIndex(residence.internalToExternalLabel(0)) != 0
                || residence.externalToInternalIndex(residence.internalToExternalLabel(residence.beds.length-1)) != residence.beds.length-1
                || residence.externalToInternalIndex(residence.internalToExternalLabel(residence.beds.length/2)) != residence.beds.length/2) {
            System.out.println("externalToInternalIndex failed.");
            numErrors++;
        }

        if (residence.externalToInternalIndex(200) != 0) {
            System.out.println("Minimum external label was not converted to 0.");
            numErrors++;
        }
        if (residence.externalToInternalIndex(210) != residence.beds.length-1) {
            System.out.println("Maximum external label was not converted "
                    + "to last location of the array.");
            numErrors++;
        }
        if (residence.internalToExternalLabel(0) != 200) {
            System.out.println("Minimum internal index was not converted to first bed label.");
            numErrors++;
        }
        if (residence.internalToExternalLabel(residence.beds.length-1) != 210) {
            System.out.println("Maximum internal index was not converted "
                    + "to last bed label.");
            numErrors++;
        }

        if (residence.isOccupied(205)) {
            System.out.println("Function isOccupied incorrectly returns that bed 205 is occupied.");
            numErrors++;
        }

        Person p = new Person("Pete Roy", 123456);
        residence.assignPersonToBed(p, 205);
        if (! residence.isOccupied(205)) {
            System.out.println("assignPersonToBed() or isOccupied() failed: isOccupied incorrectly returns that bed 205 is not occupied.");
            numErrors++;
        }
        if (residence.getPerson(205) != p) {
            System.out.println("getPerson() or isOccupied() failed: Person Pete Roy should be in bed 205, but " + residence.getPerson(205) + " is.");
            numErrors++;
        }
        if (! residence.getPerson(205).getName().equals("Pete Roy")) {
            System.out.println("getPerson() or isOccupied() failed: Pete Roy should be in bed 205, but " + residence.getPerson(205) + " is.");
            numErrors++;
        }


        String expected = "\nResidence Assiniboine with capacity 11 has the following students: \n" +
                "bed 200: \n" +
                "bed 201: \n" +
                "bed 202: \n" +
                "bed 203: \n" +
                "bed 204: \n" +
                "bed 205: Pete Roy\n" +
                "bed 206: \n" +
                "bed 207: \n" +
                "bed 208: \n" +
                "bed 209: \n" +
                "bed 210: \n";
        if(!residence.toString().equals(expected)) {
            System.out.println("Error in toString() method. Expected: " + expected + "But returned: " + residence);
            numErrors++;
        }


        // retest all the methods on a second instance of the class
        residence = new Residence("Wollaston", 1, 3);

        if(!residence.getName().equals("Wollaston")) {
            System.out.println("The constructor or getName failed.");
            numErrors++;
        }

        if(residence.getMinBedLabel() != 1) {
            System.out.println("The constructor or getMinBedLabel failed.");
            numErrors++;
        }

        if(residence.getMaxBedLabel() != 3) {
            System.out.println("The constructor or getMaxBedLabel failed.");
            numErrors++;
        }


        if(!residence.isValidLabel(1) || !residence.isValidLabel(2)
                || !residence.isValidLabel(3)
                ||  residence.isValidLabel(0)
                ||  residence.isValidLabel(4)) {
            System.out.println("isValidLabel failed.");
            numErrors++;
        }

        if(residence.internalToExternalLabel(residence.externalToInternalIndex(1)) != 1
                || residence.internalToExternalLabel(residence.externalToInternalIndex(2)) != 2
                || residence.internalToExternalLabel(residence.externalToInternalIndex(3)) != 3) {
            System.out.println("internalToExternalLabel failed.");
            numErrors++;
        }

        if(residence.externalToInternalIndex(residence.internalToExternalLabel(0)) != 0
                || residence.externalToInternalIndex(residence.internalToExternalLabel(residence.beds.length-1)) != residence.beds.length-1
                || residence.externalToInternalIndex(residence.internalToExternalLabel(residence.beds.length/2)) != residence.beds.length/2) {
            System.out.println("externalToInternalIndex failed.");
            numErrors++;
        }

        if (residence.externalToInternalIndex(1) != 0) {
            System.out.println("Minimum external label was not converted to 0.");
            numErrors++;
        }
        if (residence.externalToInternalIndex(3) != residence.beds.length-1) {
            System.out.println("Maximum external label was not converted "
                    + "to last location of the array.");
            numErrors++;
        }
        if (residence.internalToExternalLabel(0) != 1) {
            System.out.println("Minimum internal index was not converted to first bed label.");
            numErrors++;
        }
        if (residence.internalToExternalLabel(residence.beds.length-1) != 3) {
            System.out.println("Maximum internal index was not converted "
                    + "to last bed label.");
            numErrors++;
        }

        if (residence.isOccupied(2)) {
            System.out.println("Function isOccupied incorrectly returns that bed 2 is occupied.");
            numErrors++;
        }

        p = new Person("Dan Foe", 789456);
        residence.assignPersonToBed(p, 1);
        if (! residence.isOccupied(1)) {
            System.out.println("assignPersonToBed() or isOccupied() failed: isOccupied incorrectly returns that bed 1 is not occupied.");
            numErrors++;
        }
        if (residence.getPerson(1) != p) {
            System.out.println("getPerson() or isOccupied() failed: Person Dan Foe should be in bed 1, but " + residence.getPerson(1) + " is.");
            numErrors++;
        }

        Student s3 = new Student("Nevil", 123, "abc123");
        Residence r = new Residence("CQ",10,14);

        r.assignPersonToBed(s3,11);
        r.freeBed(11);
        if(r.isOccupied(11)){
            System.out.println("Error in freeBed method");
            numErrors++;
        }

        r.assignPersonToBed(s3,12);
        ArrayList<Integer>expectedList = new ArrayList<Integer>();
        expectedList.add(10);
        expectedList.add(11);
        expectedList.add(13);
        expectedList.add(14);
        if(!r.availableBeds().equals(expectedList)){
            System.out.println("Error in expectedList method.");
        }

        System.out.println("The number of errors found is " + numErrors);
    }
}
