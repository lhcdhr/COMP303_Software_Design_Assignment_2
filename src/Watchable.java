import java.util.HashMap;

/**
 * Watchable interface is implemented
 * by class Movie, class Episode, and Class TVShow.
 *
 * @author Haochen Liu(260917834)
 * @version 2.0
 */
public interface Watchable {

    /**
     * Update the validity of this Watchable object
     */
    public void updateValidity();

    /**
     * Get the validity of this Watchable object
     * @return validity
     */
    public boolean getValidity();

    /**
     * Get the title(name) of this Watchable object
     * @return title
     */
    public String getTitle();

    /**
     * Get the language of this Watchable object
     * @return language
     */
    public String getLanguage();

    /**
     * Get the publishing studio of this Watchable object
     * @return studio
     */
    public String getPublishingStudio();

    /**
     * Return a deep-copy of the HashMap that stores the
     * custom information of this Watchable object.
     * @return deep-copy of the HashMap
     */
    public HashMap<String,String> getCustomInfo();

    /**
     * Return a deep-copy of this Watchable object
     * @return deep-copy of this Object
     */
    public Watchable getCopy();
}
