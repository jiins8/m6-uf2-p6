import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public static void main(String[] args) {
        var em = entityManagerFactory.createEntityManager();
        System.out.println(em.getProperties());
        EntityTransaction transaction = em.getTransaction();

        var director = new Director();
        director.setName("Director 1");

        var review = new Review();
        review.setText("Review 1");

        var movie = new Movie();
        movie.setTitle("Movie 1");
        movie.setDirector(director);
        movie.getReviews().add(review);
        director.getMovie().add(movie);

        transaction.begin();
        em.persist(director);
        em.persist(movie);
        em.persist(review);
        transaction.commit();

    }
}