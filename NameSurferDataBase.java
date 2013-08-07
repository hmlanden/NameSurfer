import java.io.*;
import java.util.*;
 
/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */
 
public class NameSurferDataBase implements NameSurferConstants {
 
    /* Constructor: NameSurferDataBase(filename) */
    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    public NameSurferDataBase(String filename) {
        //Initialize database HashMap. Key = String name, Value = NameSurferEntry entry
        database = new HashMap<String, NameSurferEntry>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
 
            /**read lines from the file and add them to the database.*/
            while(true){
                String entry = br.readLine();
                if(entry != null){
                    //create a NameSurferEntry from the data
                    NameSurferEntry nameEntry = new NameSurferEntry(entry);
 
                    //Add name and NameSurferEntry to HashMap.
                    database.put((nameEntry.name).toLowerCase(), nameEntry);
                }
                //stop reading when we run out of lines.
                if(entry == null) break;

            }
            br.close();
        }catch(IOException e){
            //Error: We all weep, because I would very much like this program to work.
        }
    }
 
    /* Method: findEntry(name) */
    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {
        return database.get(name);
    }
 
 
    /*
     * Instance variables section.
     */
    HashMap<String, NameSurferEntry> database;
}
 