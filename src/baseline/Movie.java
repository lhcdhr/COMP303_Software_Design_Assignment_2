import java.io.File;
import java.util.*;

/**
 * This class stores some necessary information about a movie,
 * including its storing path, title, language, publishing studio,
 * validity, format, and some custom information.
 *
 * @author Haochen Liu(260917834)
 * @version 2.0
 */

public class Movie implements Watchable{

    private enum Format{ MP4, MOV, WMV, AVI, FLV, MKV }

    private boolean validity;
    private Format format;
    final private String PATH;
    private String title;
    private String language;
    private String publishingStudio;
    private HashMap<String, String> customInfo;
    private Movie preceding;
    private Movie sequel;


    /**
     * Constructor of Movie class.
     * Each Movie object has a path that points to a unique file.
     * The path cannot be changed after the creation of this movie object.
     * No Movie objects will point to a same file.
     * The format of the file can only be one of MP4, MOV, WMV, AVI, FLV,and MKV,
     * cases of letters in the path does not matter.
     * The movie is valid if the path points to a actual file.
     * The
     *
     * @param mPath the path pointing to where the movie file is stored
     * @param mTitle the title of the movie
     * @param mLanguage the language of the movie
     * @param mStudio the publishing studio of the movie
     */
    public Movie(String mPath, String mTitle, String mLanguage, String mStudio){

        /*
        * Get the format of the movie file based on the input path, and convert
        * all lower case characters to upper case.
        * */
        String mFormat = mPath.substring(mPath.length()-3).toUpperCase();

        // Check if the file is one of the 6 restricted formats.
        boolean formatCheck = false;
        for(int i=0;i<6;i++){

            // Once the format matches, set the format of the object.
            if(Format.values()[i].name().equals(mFormat)){
                formatCheck = true;
                this.format = Format.values()[i];
                break;
            }
        }
        // Movie object with invalid format should not be created.
        if(!formatCheck){
            throw new IllegalArgumentException("Unable to create the movie due to invalid file type.");
        }
        File temp = new File(mPath);
        HashSet<String> toCheck = Library.getExistedPaths();
        for(String existedPath: toCheck){
            File usedFile = new File(existedPath);
            if(temp.compareTo(usedFile)==0){
                throw new IllegalArgumentException("Unable to create the movie. This file has been assigned to another movie.");
            }
        }
        // Set the path.
        this.PATH = mPath;
        // Check and set the validity of the object.
        File movie = new File(mPath);
        this.validity = movie.exists();
        // Set the required information
        this.title = mTitle.toLowerCase();
        this.language = mLanguage.toLowerCase();
        this.publishingStudio = mStudio.toLowerCase();
        // Create a hashmap to store the custom information in key-value pairs.
        this.customInfo = new HashMap<String,String>();
        // Store each movie object in Library.
        Library.addMovie(this,this.PATH);
    }

    /**
     * This constructor will deep-copy the input Movie object.
     * Users will have access to all the movies stored in a watchlist,
     * so it is necessary to prevent them from changing the information of the
     * Movie object by mistake.
     *
     * @param toCopy the Movie object to be deep-copied
     */
    public Movie(Movie toCopy){
        this.validity = toCopy.validity;
        this.PATH = toCopy.PATH;
        String cFormat = this.PATH.substring(this.PATH.length()-3).toUpperCase();
        for(int i=0;i<6;i++){
            // Once the format matches, set the format of the object.
            if(Format.values()[i].name().equals(cFormat)){
                this.format = Format.values()[i];
                break;
            }
        }
        this.title = toCopy.title;
        this.language = toCopy.language;
        this.publishingStudio = toCopy.publishingStudio;
        this.customInfo = (HashMap<String, String>) toCopy.customInfo.clone();
        this.preceding=toCopy.preceding;
        this.sequel=toCopy.sequel;
    }

    /**
     * Return a deep-copy of a Movie object.
     *
     * @return the deep-copy of the Movie object
     */
    @Override
    public Movie getCopy(){
        return new Movie(this);
    }

    /**
     * The validity of a Movie object is based on whether the file exists.
     * If the underlying file disappears, it becomes invalid.
     * If the file is restored, it becomes valid.
     *
     */
    @Override
    public void updateValidity(){
        File toCheck = new File(this.PATH);
        this.validity = toCheck.exists();
    }

    /**
     * Return the current validity of this Movie object.
     *
     * @return the current validity
     */
    @Override
    public boolean getValidity(){
        this.updateValidity();
        return this.validity;
    }

    /**
     * Return the title of the Movie Object.
     *
     * @return the current title
     */
    @Override
    public String getTitle(){
        return this.title;
    }

    /**
     * Return the language of the Movie Object.
     *
     * @return the current language
     */
    @Override
    public String getLanguage(){
        return this.language;
    }

    /**
     * Return the publishing studio of the Movie Object.
     *
     * @return the current publishing studio
     */
    @Override
    public String getPublishingStudio(){
        return this.publishingStudio;
    }



    /**
     * Add or change custom information.
     * If the key does not exist, then it will create a new pair;
     * If the key exist, the older value will be overwritten.
     *
     * @param key key to add
     * @param value value to add
     */
    public void updateCustomInfo(String key, String value) {
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
    public HashMap<String,String> getCustomInfo(){
        return (HashMap<String, String>) this.customInfo.clone();
    }

    /**
     * Set the preceding movie of the Movie object.
     * @param pre the preceding movie
     */
    public void setPreceding(Movie pre){
        this.preceding=pre;
    }

    /**
     * Set the sequel movie of the Movie object.
     * @param seq the sequel movie
     */
    public void setSequel(Movie seq){
        this.sequel=seq;
    }

    /**
     * Get a deep-copy of the preceding movie.
     * @return deep-copy of the preceding movie
     */
    public Movie getPreceding(){
        return new Movie(this.preceding);
    }

    /**
     * Get a deep-copy of the sequel movie.
     * @return deep-copy of the sequel movie
     */
    public Movie getSequel(){
        return new Movie(this.sequel);
    }

    /**
     * Check whether this Movie object has a preceding movie.
     * @return true if this Movie object has a preceding movie, false otherwise
     */
    public boolean hasPreceding(){
        if(this.preceding==null){
            return false;
        }
        return true;
    }

    /**
     * Check whether this Movie object has a preceding movie.
     * @return true if this Movie object has a sequel movie, false otherwise
     */
    public boolean hasSequel(){
        if(this.sequel==null){
            return false;
        }
        return true;
    }
}
