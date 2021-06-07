import java.util.*;

/**
 * This class stores some necessary information about a watchlist.
 * Watchlist stores the reference of Movie objects.
 * The name of each watchlist is unique.
 * Watchlist knows how many valid movies it contains.
 *
 * @author Haochen Liu(260917834)
 * @version 2.0
 */

public class Watchlist implements Bingeable{


    private String name;
    private LinkedList<Watchable> listWatchable;
    private int validCount;

    private static LinkedList<String> existedWatchlist = new LinkedList<String>();

    /**
     * Constructor of Watchlist.
     * It takes a String as its name, and an array of Movie object(s);
     * Each Watchlist object has its unique name.
     * Initialize the LinkedList to store Watchable objects.
     *
     * @param wName the name of the Watchlist object
     *
     */
    public Watchlist(String wName){
        if(wName == null){
            throw new IllegalArgumentException("The name cannot be null!");
        }
        if(Watchlist.existedWatchlist.contains(wName)){
            throw new IllegalArgumentException("The name for the watchlist has already been used. Please set a new one.");
        }
        this.name = wName;
        existedWatchlist.add(wName);
        this.listWatchable = new LinkedList<Watchable>();
        Library.addWatchlist(this);
    }

    /**
     * Add a Watchable object to this Watchlist.
     * No duplicates allowed.
     *
     * @param toAdd the Watchable object to be added
     */
    public void add(Watchable toAdd){
        if(toAdd == null){
            throw new IllegalArgumentException("Input cannot be null!");
        }
        if(!this.listWatchable.contains(toAdd)){
            //store the Movie object to this Watchlist
            if(toAdd.getValidity()){
                this.validCount++;
            }
            this.listWatchable.add(toAdd);
        }
        else{
            throw new IllegalArgumentException("It is already in this watchlist!");
        }
    }

    /**
     * Rename the Watchlist after created.
     * The older name will be removed from the existed name.
     * No existing name will be used.
     *
     * @param newName the new name for this Watchlist
     */
    public void rename(String newName){
        // no existing name will be used
        if(Watchlist.existedWatchlist.contains(newName)){
            throw new IllegalArgumentException("This name has been used!");
        }
        Watchlist.existedWatchlist.remove(this.name);
        this.name = newName;
    }

    /**
     * Get a String array of all languages in this Watchlist.
     *
     * @return the String array that contains all languages without duplicates
     */
    public String[] getLanguages(){
        HashSet<String> sLan = new HashSet<String>();
        for(Watchable toCheck: this.listWatchable){
            sLan.add(toCheck.getLanguage());
        }
        int lSize = sLan.size();
        int count = 0;
        String[] arrayLanguage = new String[lSize];
        if(lSize != 0) {
            for (String lan : sLan) {
                arrayLanguage[count] = lan;
                count++;
            }
        }
        return arrayLanguage;


    }
    /**
     * Get a String array of all publishing studios in this Watchlist.
     *
     * @return the String array that contains all publishing studios without duplicates
     */
    public String[] getStudios(){
        HashSet<String> sStu = new HashSet<String>();
        for(Watchable toCheck: this.listWatchable){
            sStu.add(toCheck.getLanguage());
        }
        int lSize = sStu.size();
        int count = 0;
        String[] arrayStudio = new String[lSize];
        if(lSize != 0) {
            for (String stu : sStu) {
                arrayStudio[count] = stu;
                count++;
            }
        }
        return arrayStudio;
    }

    /**
     * Update the validity of every Watchable object stored in this Watchlist.
     */
    @Override
    public void updateValidity(){
        for(Watchable toCheck: this.listWatchable){
            toCheck.updateValidity();
        }
    }
    /**
     * Remove the first movie in the Watchlist.
     * If this Watchlist contains no movie, it will return false.
     * If the movie is successfully removed, it will return true.
     *
     * @return whether the first movie is successfully removed
     */
    public boolean removeFirst(){
        // if there is no movie, then return false
        if(this.listWatchable.size() == 0){
            return false;
        }
        this.updateValidity();
        Watchable toRemove = this.listWatchable.getFirst();
        if(toRemove.getValidity()){
            this.validCount = this.validCount - 1;
        }

        this.listWatchable.removeFirst();

        return true;
    }

    /**
     * Return a deep copy of all Movies stored in this Watchlist.
     * The user's action to the copy of Movies will not affect
     * the original Movies.
     *
     * @return a deep-copied linked list of Movies stored in this Watchlist
     */
    @Override
    public LinkedList<Watchable> accessAll(){
        int size = this.listWatchable.size();
        LinkedList<Watchable> copyWatchables = new LinkedList<Watchable>();
        for(int i=0;i<size;i++){
            copyWatchables.add(this.listWatchable.get(i).getCopy());
        }

        return copyWatchables;
    }

    /**
     * Return the number of valid Movies in this Watchlist.
     *
     * @return the number of valid Movies
     */
    public int getValidCount(){
        return this.validCount;
    }



}
