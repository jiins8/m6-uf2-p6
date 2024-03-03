import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

public class MainDeleteDirector {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public static void main(String[] args) {
        var em = entityManagerFactory.createEntityManager();
        System.out.println(em.getProperties());
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // Create a new Director with associated Movie and Review
            var director = new Director();
            director.setName("DirectorToDelete");

            var review = new Review();
            review.setText("ReviewToDelete");

            var movie = new Movie();
            movie.setTitle("MovieToDelete");
            movie.setDirector(director);
            movie.getReviews().add(review);
            director.getMovie().add(movie);

            em.persist(director);

            transaction.commit();

            // Print the current state of the database
            printDatabaseState();

            // Delete the Director and its associated entities
            transaction.begin();
            em.remove(director);
            transaction.commit();

            // Print the state of the database after deletion
            printDatabaseState();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            entityManagerFactory.close();
        }
    }

    private static void printDatabaseState() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            List<Director> directors = em.createQuery("SELECT d FROM Director d", Director.class).getResultList();
            List<Movie> movies = em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
            List<Review> reviews = em.createQuery("SELECT r FROM Review r", Review.class).getResultList();

            System.out.println("Directors in the database: " + directors);
            System.out.println("Movies in the database: " + movies);
            System.out.println("Reviews in the database: " + reviews);
        } finally {
            em.close();
        }
    }
}