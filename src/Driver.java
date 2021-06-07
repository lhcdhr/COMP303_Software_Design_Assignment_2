import java.util.*;

public class Driver {
    public static void main(String[] args){
        TVShow show1 = new TVShow("McGill","McGrill","McGillish",10);
        show1.getTitle();
        show1.updateEpisode("McGill2.wmv",2);
        show1.updateEpisode("McGill1.wmv",1);
        show1.updateEpisode("McGill3.wmv",3);
        show1.updateEpisode("McGill4.wmv",4);
        show1.updateEpisode("McGill5.wmv",5);
        show1.updateEpisode("McGill6.wmv",6);
        show1.updateEpisode("McGill7.wmv",7);
        show1.updateEpisode("McGill8.wmv",8);
        show1.updateEpisode("McGill9.wmv",9);
        show1.updateEpisode("McGill10.wmv",10);

        Movie m1 = new Movie("somewhere/pl1.mp4","Patlabor: the Movie ","Japanese","studio deen");
        Movie m2 = new Movie("somewhere/pl2.mp4","Patlabor 2: the Movie","Japanese","production I.G");
        Movie m3 = new Movie("somewhere/pl3.mp4","WXIII: Patlabor the Movie 3 ","Japanese","MADHOUSE");
        m1.setSequel(m2);
        m2.setPreceding(m1);
        m2.setSequel(m3);
        m3.setPreceding(m2);

        Movie m4 = new Movie("a/b/c/some4.avi","Rush Hour 1","English","Roger Birnbaum Productions");
        Movie m5 = new Movie("b/c/some5.avi","Ip Man 4", "Mandarin","Mandarin Motion Pictures");
        Movie m6 = new Movie("c/d/some6.avi", "Hero","Mandarin","Sil-Metropole Organisation");

        Movie m7 = new Movie("/that7.avi","A Movie","English","studio1");
        Movie m8 = new Movie("that8.avi","A Film","English","studio1");
        Movie m9 = new Movie("that9.flv","An Opera","English","studio1");
        Movie m10 = new Movie("that10.mkv","A Moving Picture","English","studio1");
        Movie m11 = new Movie("that11.mov","A Show","English","studio1");

        System.out.println("========================================================================");
        System.out.println("Generate a watchlist with 5 random episodes from the TV series McGill");

        Watchlist w1 = Library.generateWatchlist("5RandomEpisodes","mcgill");
        System.out.println("5 Random of TVSeries McGill");
        LinkedList<Watchable> wAll= w1.accessAll();
        for(Watchable e:wAll){
            System.out.println(e.getTitle()+"  "+e.getLanguage()+"  "+e.getPublishingStudio());

            System.out.println("--------------------------------------------------------");
        }
        System.out.println("========================================================================");
        System.out.println("Generate a watchlist with 4 random movies with filter: English and studio1.");
        String[] language = {"English"};
        String[] studio = {"studio1"};
        Watchlist w2 = Library.generateWatchlist("English family","Movie",language,studio,4);
        LinkedList<Watchable> wAll2=w2.accessAll();

        for(Watchable e:wAll2){
            System.out.println(e.getTitle()+"  "+e.getLanguage()+"  "+e.getPublishingStudio());
            System.out.println("--------------------------------------------------------");
        }
        Watchlist w3 = Library.generateWatchlist("allSeries","Patlabor 2: the Movie");
        LinkedList<Watchable> wAll3 = w3.accessAll();
        System.out.println("========================================================================");
        System.out.println("Generate a watchlist with all series of Movie Patlabor in the order of old to new.");
        for(Watchable p: wAll3){
            System.out.println(p.getTitle());
        }
    }
}
