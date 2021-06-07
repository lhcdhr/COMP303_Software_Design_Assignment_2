import java.util.LinkedList;

/**
 * Bingeable interface is implemented
 * by class TVShow and class Watchlist.
 *
 * @author Haochen Liu(260917834)
 * @version 2.0
 */
public interface Bingeable<T> {
    /**
     * Providing access to the elements of Bingeable object,
     * but not hiding the original reference to prevent
     * unauthorized modification of elements.
     * A deep-copy of the LinkedList of elements will be
     * generated.
     * @return LinkedList that stores the deep-copy of elements stored in this Bingeable object.
     */
    public LinkedList<T> accessAll();

    /**
     * Update the validity of the Bingeable object
     */
    public void updateValidity();
}
