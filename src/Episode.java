import java.io.File;
import java.util.*;

/**
 * This class stores some necessary information about a movie,
 * including its storing path, title, language, publishing studio,
 * validity, format, and some custom information. It also stores
 * its sequence number in a TVShow, and which TVShow it is coming from.
 *
 * @author Haochen Liu(260917834)
 * @version 2.0
 */
public class Episode implements Watchable{

    private enum Format{ MP4, MOV, WMV, AVI, FLV, MKV }

    private boolean validity;
    private Format format;
    private String seriesName;
    final private String PATH;
    private String title;
    private String language;
    private String publishingStudio;
    private int sequence;
    private TVShow tvShow;

    /**
     * Generate a Episode object based on the input.
     * Similar to Movie objects, with path pointing to a unique
     * file.
     * This constructor is only used in the add method of TVShow,
     * so those input, except ePath, come from the TVShow object.
     *
     * @param eShow the TVShow that the Episode belongs to
     * @param ePath the path of the file
     * @param eSeriesName the combination of the title of the TVShow and the sequence number of the Episode, like "wow1"
     * @param eTitle the title of the TVShow that this Episode belongs to
     * @param eLanguage the language of the TVShow that this Episode belongs to
     * @param eStudio the publishing studio of the TVShow that this Episode belongs to
     * @param eSequence the sequence number of this Episode
     */
    public Episode(TVShow eShow, String ePath, String eSeriesName, String eTitle, String eLanguage, String eStudio, int eSequence){

        String mFormat = ePath.substring(ePath.length()-3).toUpperCase();

        // Check if the file is one of the 6 restricted formats.
        boolean formatCheck = false;
        for(int i=0;i<6;i++){

            // Once the format matches, set the format of the object.
            if(Episode.Format.values()[i].name().equals(mFormat)){
                formatCheck = true;
                this.format = Episode.Format.values()[i];
                break;
            }
        }
        // Episode object with invalid format should not be created.
        if(!formatCheck){
            throw new IllegalArgumentException("Unable to create the movie due to invalid file type.");
        }

        // Set the path.
        File temp = new File(ePath);
        HashSet<String> toCheck = Library.getExistedPaths();
        for(String existedPath: toCheck){
            File usedFile = new File(existedPath);
            if(temp.compareTo(usedFile)==0){
                throw new IllegalArgumentException("Unable to create the movie. This file has been assigned to another movie.");
            }
        }
        this.PATH = ePath;
        // Check and set the validity of the object.
        File movie = new File(ePath);
        this.validity = movie.exists();
        this.seriesName = eSeriesName.toLowerCase();
        // Set the required information
        this.title = eTitle.toLowerCase();
        this.language = eLanguage.toLowerCase();
        this.publishingStudio = eStudio.toLowerCase();

        if(eSequence<0){
            this.sequence =1;
        }
        else {
            this.sequence = eSequence;
        }
        // Store each Episode object in Library.
        Library.addEpisode(this,this.PATH);
        this.tvShow=eShow;
    }

    /**
     * Generate a deep-copy of the Episode object.
     *
     * @param toCopy the deep-copy of the Episode object
     */
    public Episode(Episode toCopy) {
        this.validity = toCopy.validity;
        this.PATH = toCopy.PATH;
        String cFormat = this.PATH.substring(this.PATH.length() - 3).toUpperCase();
        for (int i = 0; i < 6; i++) {
            // Once the format matches, set the format of the object.
            if (Episode.Format.values()[i].name().equals(cFormat)) {
                this.format = Episode.Format.values()[i];
                break;
            }
        }
        this.seriesName = toCopy.seriesName;
        this.title = toCopy.title;
        this.language = toCopy.language;
        this.publishingStudio = toCopy.publishingStudio;
        this.sequence = toCopy.sequence;
        this.tvShow = toCopy.tvShow;
    }

    /**
     * Return a deep-copy of the Episode object.
     *
     * @return the deep-copy of the Episode object
     */
    @Override
    public Episode getCopy(){
        return new Episode(this);
    }
    /**
     * The validity of a Movie object is based on whether the file exists.
     * If the underlying file disappears, it becomes invalid.
     * If the file is restored, it becomes valid.
     *
     */

    /**
     * The validity of a Episode object is based on whether the file exists.
     * If the underlying file disappears, it becomes invalid.
     * If the file is restored, it becomes valid.
     *
     */
    public void updateValidity(){
        File toCheck = new File(this.PATH);
        this.validity = toCheck.exists();
    }

    /**
     * Return the current validity of this Episode object.
     *
     * @return the current validity
     */
    @Override
    public boolean getValidity(){
        return this.validity;
    }

    /**
     * Return the title of the Episode Object.
     *
     * @return the current title
     */
    @Override
    public String getTitle(){
        return this.title;
    }

    /**
     * Return the language of the Episode Object.
     *
     * @return the current language
     */
    @Override
    public String getLanguage(){
        return this.language;
    }

    /**
     * Return the publishing studio of the Episode Object.
     *
     * @return the current publishing studio
     */
    @Override
    public String getPublishingStudio(){
        return this.publishingStudio;
    }

    /**
     * Get the name of TVShow of this Episode
     * @return the name of TVShow of this Episode
     */
    public String getSeriesName(){
        return this.tvShow.getTitle();
    }

    /**
     * Get the sequence number of this Episode
     * @return sequence number
     */
    public int getSequence(){
        return this.sequence;
    }

    /**
     * Get the custom information of the TVShow that the Episode belongs to.
     * Every episode shares the same custom information with its TVShow.
     *
     * @return the custom information of its TVShow
     */
    @Override
    public HashMap<String,String> getCustomInfo(){
        return this.tvShow.getCustomInfo();
    }

    /**
     * Get a deep-copy of the previous episode of this Episode object.
     * Note that if the previous one is not stored, there will be an
     * IllegalArgumentException.
     *
     * @return the previous episode of this Episode
     */
    public Episode getPrevious(){
        return this.tvShow.accessEpisode(this.sequence-1);
    }

    /**
     * Get a deep-copy of the next episode of this Episode object.
     * Note that if the next one is not stored, there will be an
     * IllegalArgumentException.
     *
     * @return
     */
    public Episode getNext(){
        return this.tvShow.accessEpisode(this.sequence+1);
    }

}
