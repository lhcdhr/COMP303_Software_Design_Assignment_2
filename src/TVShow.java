import java.util.*;

/**
 * This class stores some necessary information about a TVShow.
 * TVShow stores the reference of Episode objects.
 * The Episodes are stored by a key-value pair,
 * with k be its sequence number, value be the Episode.
 * The name of each TVShow is unique.
 * TVShow knows how many valid Episodes it contains.
 *
 * @author Haochen Liu(260917834)
 * @version 2.0
 */
public class TVShow implements Watchable,Bingeable{

    private String name;
    private HashMap<Integer,Episode> allEpisodes;
    private int expectedSequence;
    private int currentSequence;
    private String studio;
    private String language;
    private HashMap<String,String> customInfo;
    private boolean validity;


    /**
     * Constructor.
     * Generate a new TVShow object based on the given information.
     * Store the newly-created TVShow object to the library.
     *
     * @param tName the name of the TVShow
     * @param tStudio the publishing studio of the TVShow
     * @param tLanguage the language of the TVShow
     * @param tSequence the total expected sequences of the TVShow
     */
    public TVShow(String tName, String tStudio, String tLanguage,int tSequence){
        if(tName == null || tStudio == null || tLanguage == null){
            throw new IllegalArgumentException("The required input cannot be null!");
        }

        this.name = tName.toLowerCase();
        this.allEpisodes = new HashMap<Integer, Episode>();
        this.currentSequence = 0;
        this.expectedSequence=tSequence;
        this.studio = tStudio.toLowerCase();
        this.language = tLanguage.toLowerCase();
        this.customInfo = new HashMap<String,String>();
        this.validity = false;
        Library.addTVShow(this);
    }

    /**
     * Constructor that makes a deep-copy of this TVShow object
     * to prevent reference leaking.
     *
     * @param toCopy the TVShow object to copy from
     */
    public TVShow(TVShow toCopy){
        this.name = toCopy.name;
        HashMap<Integer, Episode> copyEpisodes = new HashMap<Integer,Episode>();
        for(int i=0;i<this.expectedSequence;i++){
            if(this.allEpisodes.containsKey(i)){
                copyEpisodes.put(i,new Episode(this.allEpisodes.get(i)));
            }
        }
        this.allEpisodes = copyEpisodes;
        this.expectedSequence = toCopy.expectedSequence;
        this.currentSequence = toCopy.currentSequence;
        this.studio = toCopy.studio;
        this.language = toCopy.language;
        this.customInfo = (HashMap<String, String>) toCopy.customInfo.clone();
        this.validity = toCopy.validity;
    }

    /**
     * Return a deep-copy of this TVShow object
     * @return deep-copy of this TVShow object
     */
    @Override
    public TVShow getCopy(){
        return new TVShow(this);
    }

    /**
     * Update or add
     * @param ePath
     * @param eSequence
     */
    public void updateEpisode(String ePath, int eSequence){
        if(eSequence > this.expectedSequence || eSequence < 0){
            throw new IllegalArgumentException("Invalid sequence number.");
        }
        String episodeName = this.name + eSequence;
        Episode nEpisode = new Episode(this, ePath, this.name, episodeName, this.language, this.studio, eSequence);
        if(!this.allEpisodes.containsKey(eSequence)){
            this.currentSequence++;
        }
        this.allEpisodes.put(eSequence,nEpisode);
        this.updateValidity();
    }

    /**
     * Update the validity of the TVShow.
     * The rule of validity is that if the TVShow
     * contains at least one valid Episode,
     * then it is valid.
     */
    @Override
    public void updateValidity(){
        for(Episode toCheck: this.allEpisodes.values()){
            toCheck.updateValidity();
            if(toCheck.getValidity()){
                this.validity = true;
            }
        }
    }

    /**
     * Get the validity of this TVShow
     * @return
     */
    @Override
    public boolean getValidity() {
        this.updateValidity();
        return this.validity;
    }

    /**
     * Get the title of this TVShow
     * @return the title of this TVShow
     */
    @Override
    public String getTitle(){
        return this.name;
    }

    /**
     * Get the language of this TVShow
     * @return the language of this TVShow
     */
    @Override
    public String getLanguage(){
        return this.language;
    }

    /**
     * Get the publishing studio of this TVShow
     * @return the publishing studio of this TVShow
     */
    @Override
    public String getPublishingStudio() {
        return this.studio;
    }


    /**
     * Add or change custom information.
     * If the key does not exist, then it will create a new pair;
     * If the key exist, the older value will be overwritten.
     * @param key key to add
     * @param value value to add
     */
    public void updateCustomInfo(String key, String value){
        this.customInfo.put(key,value);
    }

    /**
     * Remove a custom information based on the key.
     * @param key the key to remove
     */
    public void removeCustomInfo(String key){
        if(this.customInfo.containsKey(key)){
            this.customInfo.remove(key);
        }
        else{
            System.out.println("The information does not exist!");
        }
    }

    /**
     * Return a copy of the hashmap that stores all the custom information about a Movie object.
     * HashMap<>.clone() is a shallow copy, but the key and value stored in it are String,
     * so the changing the returned hashmap will not affect the one stored in this Movie object.
     *
     * @return the copy of the hashmap that contains all custom information
     */
    @Override
    public HashMap<String,String> getCustomInfo() {

        return (HashMap<String, String>) this.customInfo.clone();
    }

    /**
     * Return a deep copy of all Episodes stored in this TVShow.
     * The user's action to the copy of Episodes will not affect
     * the original Episodes.
     *
     * @return a deep-copied linked list of Episodes stored in this TVShow
     */
    @Override
    public LinkedList<Watchable> accessAll(){
        LinkedList<Watchable> copyEpisodes = new LinkedList<Watchable>();
        for(int i=0;i<this.expectedSequence+1;i++){
            if(this.allEpisodes.containsKey(i)){
                copyEpisodes.add(new Episode(allEpisodes.get(i)));
            }
        }
        return copyEpisodes;
    }

    /**
     * Access a episode by its sequence number.
     * If this episode is not stored within this TVShow,
     * it will throw IllegalArgumentException.
     *
     * @param sequence the sequence number of the Episode required
     * @return the deep-copy required Episode object
     */
    public Episode accessEpisode(int sequence){
        if(this.allEpisodes.containsKey(sequence)){
            return new Episode(this.allEpisodes.get(sequence));
        }
        else{
            throw new IllegalArgumentException("This episode is not stored in the library!");
        }
    }

}
