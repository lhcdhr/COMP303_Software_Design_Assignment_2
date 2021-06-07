import kotlin.OverloadResolutionByLambdaReturnType;

import java.io.File;
import java.util.*;
/**
 * Library has to static fields to store all the created
 * Movie objects, Episode objects, and Watchlist objects.
 * They are stored in HashSets to avoid possible duplicates.
 * TVShow objects are stored in HashMap to allow getting it
 * based on its name(title).
 * All path used by existed objects will be stored in a HashSet
 * in order to make sure the uniqueness.
 * Watchlist objects can be generated based on requirements.
 *
 * @author Haochen Liu(260917834)
 * @version 2.0
 */
public class Library {

    private static HashSet<Movie> libMovies = new HashSet<Movie>();
    private static HashSet<Episode> libEpisodes = new HashSet<Episode>();
    private static HashSet<Watchlist> libWatchlists = new HashSet<Watchlist>();
    private static HashMap<String,TVShow> libTVShow = new HashMap<String,TVShow>();
    private static HashSet<String> existedPath = new HashSet<String>();


    public static HashSet<String> getExistedPaths(){
        return (HashSet<String>) Library.existedPath.clone();
    }

    /**
     * Add a Movie object to Library.
     * A movie object with used path should not be added to the library.
     *
     * @param toAdd the Movie object to be added
     */
    public static void addMovie(Movie toAdd, String path){
        // No movie objects shall point to the same file, except the copy.
        File toCheck = new File(path);
        for(String p: existedPath){
            File temp = new File(p);
            if(toCheck.compareTo(temp)==0){
                throw new IllegalArgumentException("This path has been used!");
            }
        }

        Library.existedPath.add(path);
        Library.libMovies.add(toAdd);

    }

    public static void addEpisode(Episode toAdd, String path){
        File toCheck = new File(path);
        for(String p: existedPath) {
            File temp = new File(p);
            if (toCheck.compareTo(temp) == 0) {
                throw new IllegalArgumentException("This path has been used!");
            }
        }
        Library.existedPath.add(path);
        Library.libEpisodes.add(toAdd);
    }

    /**
     * Remove a Movie object from the library.
     *
     * @param toRemove the Movie to be removed
     */
    public static void removeMovie(Movie toRemove){
        Library.libMovies.remove(toRemove);
    }
    /**
     * Add a Watchlist object to Library.
     *
     * @param toAdd the Watchlist object to be added
     */
    public static void addWatchlist(Watchlist toAdd){
        Library.libWatchlists.add(toAdd);
    }

    /**
     * Remove a Watchlist object from the library.
     *
     * @param toRemove the Watchlist to be removed
     */
    public static void removeWatchlist(Watchlist toRemove){
        Library.libWatchlists.remove(toRemove);
    }

    /**
     * Add a TVShow object to the library.
     * No TVShow objects in the library share the same title.
     * @param toAdd the TVShow object to be stored in the library.
     */
    public static void addTVShow(TVShow toAdd){
        if(Library.libTVShow.containsKey(toAdd.getTitle())){
            throw new IllegalArgumentException("This series has been added to the library!");
        }
        Library.libTVShow.put(toAdd.getTitle(),toAdd);
    }

    /**
     * Find and return the TVShow object with a name(title) given by the user.
     *
     * @param name the name of the TVShow that client wants
     * @return the TVShow object in the library with the given name(title)
     */
    private static LinkedList<Watchable> getAllTVShowEpisodes(String name){
        if(Library.libTVShow.containsKey(name)){
            return Library.libTVShow.get(name).accessAll();
        }
        throw new IllegalArgumentException("This TV show is not currently stored in the library.");
    }


    /**
     * Generate an array of random Integer within the given range.
     * The generated Integers are less than max.
     *
     * @param max The maximum(range) Integer generated
     * @param num The number of random Intger to generate
     * @return the array of generated random Integers
     */
    private static LinkedList<Integer> getRandomInts(int max, int num){
        if(num>max){
            throw new IllegalArgumentException("The number of ints to get must be smaller than the required maximum!");
        }
        Random random = new Random();
        LinkedList<Integer> out = new LinkedList<Integer>();
        int count = 0;
        while(count < num){
            int randomInt = random.nextInt(max);
            if(!out.contains(randomInt)){
                out.add(randomInt);
                count++;
            }
        }
        return out;
    }


    /**
     * From the source LinkedList, randomly pick some of the Watchable objects,
     * and return them in a new LinkedList.
     *
     * @param source the LinkedList to randomly choose Watchable objects from
     * @param amount the number of Watchable objects needed
     * @return the LinkedList of randomly-picked Watchable objects
     */
    private static LinkedList<Watchable> getRandoms(LinkedList<Watchable> source, int amount){
        LinkedList<Watchable> randomChoices = new LinkedList<Watchable>();
        if(source.size()<amount){
            throw new IllegalArgumentException("Not sufficient object provided in source");
        }
        LinkedList<Integer> randInts = getRandomInts(source.size(),amount);
        for(Integer i: randInts){
            randomChoices.add(source.get(i));
        }
        return randomChoices;
    }

    /**
     * Generate a random LinkedList of Watchable objects from the TVShow objects
     * stored in the library based on the language(s) and studio(s) assigned by
     * the client. The number of objects in the LinkedList depends on the user
     * input and how many TVShow objects are stored in the library.
     *
     * @param lanFilter filter of assigned language
     * @param stuFilter filter of assigned studio
     * @param amount number of objects wanted
     * @return filtered and randomly chosen objects in LinkedList.
     */
    private static LinkedList<Watchable> getTVShowByFilter(String[] lanFilter, String[] stuFilter, int amount){
        LinkedList<Watchable> filtered = new LinkedList<Watchable>();
        if(lanFilter.length == 0){
            for(TVShow t: Library.libTVShow.values()){
                filtered.add(new TVShow(t));
            }
        }
        else{
            for(TVShow t: Library.libTVShow.values()){
                for(String l:lanFilter){
                    if(t.getLanguage().equals(l.toLowerCase())){
                        filtered.add(t);
                    }
                }
            }
        }
        if(filtered.size()==0){
            return filtered;
        }
        else if(stuFilter.length==0){
            return getRandoms(filtered,amount);
        }
        else{
            for(Watchable m: filtered){
                boolean sign = false;
                for(String s: stuFilter){
                    if(m.getPublishingStudio().equals(s.toLowerCase())){
                        sign = true;
                    }
                }
                if(!sign){
                    filtered.remove(m);
                }
            }
            return getRandoms(filtered,amount);
        }
    }

    /**
     * Generate a random LinkedList of Watchable objects from the Movie objects
     * stored in the library based on the language(s) and studio(s) assigned by
     * the client. The number of objects in the LinkedList depends on the user
     * input and how many Movie objects are stored in the library.
     *
     * @param lanFilter filter of assigned language
     * @param stuFilter filter of assigned studio
     * @param amount number of objects wanted
     * @return filtered and randomly chosen objects in LinkedList.
     */
    private static LinkedList<Watchable> getMoviesByFilter(String[] lanFilter, String[] stuFilter, int amount){
        LinkedList<Watchable> filtered = new LinkedList<Watchable>();
        if(lanFilter.length == 0){
            for(Movie m: Library.libMovies){
                filtered.add(new Movie(m));
            }
        }
        else{
            for(Movie m: Library.libMovies){
                for(String l:lanFilter){
                    if(m.getLanguage().equals(l.toLowerCase())){
                        filtered.add(m);
                    }
                }
            }
        }
        if(filtered.size()==0){
            return filtered;
        }
        else if(stuFilter.length==0){
            return getRandoms(filtered,amount);
        }
        else{
            for(Watchable m: filtered){
                boolean sign = false;
                for(String s: stuFilter){
                    if(m.getPublishingStudio().equals(s.toLowerCase())){
                        sign = true;
                    }
                }
                if(!sign){
                    filtered.remove(m);
                }
            }
            return getRandoms(filtered,amount);
        }
    }

    /**
     * The random Watchlist generating algorithm that based on the type of object,
     * language, studio, and number given by the client. Type are limited to TVShow
     * and Movie.
     *
     * @param wName the name of Watchlist given by the client
     * @param type the type of object to choose
     * @param lanFilter the language(s) of objects to choose
     * @param stuFilter the studio(s) of objects to choose
     * @param amount the number of objects wanted
     * @return a Watchlist object that contains the randomly-chosen objects that meet the requirements
     */
    public static Watchlist generateWatchlist(String wName, String type, String[] lanFilter, String[] stuFilter, int amount){
        if(type.equals("TVShow")){
            LinkedList<Watchable> result = getTVShowByFilter(lanFilter,stuFilter,amount);
            Watchlist gWatchlist = new Watchlist(wName);
            for(Watchable w:result){
                gWatchlist.add(w);
            }
            return gWatchlist;
        }
        else if(type.equals("Movie")){
            LinkedList<Watchable> result = getMoviesByFilter(lanFilter,stuFilter,amount);
            Watchlist gWatchlist = new Watchlist(wName);
            for(Watchable w:result){
                gWatchlist.add(w);
            }
            return gWatchlist;
        }
        else{
            throw new IllegalArgumentException("Unknown algorithm.");
        }
    }


    /**
     * This algorithm focus on getting the whole series of a TVShow, or
     * all sequels of a Movie series based on the name(title)required by the client.
     * The result will be a Watchlist containing only Movie objects or Episode objects.
     *
     * @param identifier identify what the user want to get, can only be "allSeries" or "allEpisodes"
     * @param name the name of the TVShow object or Movie object to generate Watchlist based on it
     * @return the Watchlist object that stores all Movies/Episodes that meet the requirement
     */
    public static Watchlist generateWatchlist(String identifier, String name){
        if(identifier.equals("allSeries")) {
            LinkedList<Movie> result = new LinkedList<Movie>();
            for (Movie toCheck : Library.libMovies) {
                if (toCheck.getTitle().equals(name.toLowerCase())) {
                    result.add(new Movie(toCheck));
                    Movie pointer = toCheck;
                    while(pointer.hasPreceding()){
                        result.add(0,pointer.getPreceding());
                        pointer = pointer.getPreceding();
                    }
                    pointer = toCheck;
                    while(pointer.hasSequel()){
                        result.add(pointer.getSequel());
                        pointer = pointer.getSequel();
                    }
                    break;
                }
            }
            if(result.size()==0){
                throw new IllegalArgumentException("No movie named " +name+" was found in the library.");
            }
            Watchlist gWatchlist = new Watchlist(name+" all series");
            for(Movie m:result){
                gWatchlist.add(m);
            }
            return gWatchlist;
        }
        else if(identifier.equals("allEpisodes")){
            LinkedList<Watchable> allSeries = Library.getAllTVShowEpisodes(name);
            Watchlist gWatchlist = new Watchlist(name+" all series");
            for(Watchable e: allSeries){
                gWatchlist.add(e);
            }
            return gWatchlist;
        }
        else if(identifier.equals("5RandomEpisodes")){
            LinkedList<Watchable> allSeries = Library.getAllTVShowEpisodes(name);
            Watchlist gWatchlist = new Watchlist(name+" 5 random episodes");
            LinkedList<Watchable> choice = getRandoms(allSeries,5);
            for(Watchable w: choice){
                gWatchlist.add(w);
            }
            return gWatchlist;
        }
        else{
            throw new IllegalArgumentException("Unknown algorithm.");
        }
    }
}
