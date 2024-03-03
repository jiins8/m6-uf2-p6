import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class Main2 {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public static void main(String[] args) {
        var em = entityManagerFactory.createEntityManager();
        System.out.println(em.getProperties());
        EntityTransaction transaction = em.getTransaction();

        List<Director> directors = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();

        try {
            transaction.begin();

            for (int i = 0; i < 10; i++) {
                var director = new Director();
                director.setName("Director " + (i + 1));
                directors.add(director);

                var review = new Review();
                review.setText("Review " + (i + 1));
                reviews.add(review);

                var movie = new Movie();
                movie.setTitle("Movie " + (i + 1));
                movie.setDirector(director);
                movie.getReviews().add(review);
                director.getMovie().add(movie);
                movies.add(movie);

                em.persist(director);
                em.persist(movie);
                em.persist(review);
            }

            transaction.commit();
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
}