/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */
 
public class NameSurferEntry implements NameSurferConstants {
 
    /* Constructor: NameSurferEntry(line) */
    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        //Split the data line into an array.
        String[] data = line.split(" ");
         
        name = data[0];
         
        //For the rest of the data array, parse the integers and create an array of integers.
        for(int i = 1; i < NDECADES + 1; i ++){
Great work accounting for the off-by-one issue!

Megan Faulk (mfaulk)
            int rank = Integer.parseInt(data[i]);
            rankByDecade[i - 1] = rank;
        }
    }
 
 
    /* Method: getName() */
    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return name;
    }
 
    /* Method: getRank(decade) */
    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decade) {
        return rankByDecade[decade];
    }
 
 
    /* Method: toString() */
    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    public String toString() {
        /**Create string that displays the name and all data related 
         * to that name in a readable format.*/
        String entry = name + " [";
        for(int i = 0; i < NDECADES; i++){
            String rank = rankByDecade[i].toString();
            entry += rank;
        }
        entry += "]";
 
        return entry;
    }
 
    String name;
    Integer[] rankByDecade = new Integer[NDECADES];
 
}
 