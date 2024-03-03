import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class MainNoDirector {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public static void main(String[] args) {
        var em = entityManagerFactory.createEntityManager();
        System.out.println(em.getProperties());
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // Create a new Movie with a potential new Director
            var newMovie = new Movie();
            newMovie.setTitle("New Movie");
            // If you set a Director with an ID, it should use the existing one; otherwise, it will create a new one.
            // newMovie.setDirector(new Director()); // For testing a new Director creation

            // Persist the new Movie and its associated Director (if needed)
            em.persist(newMovie);

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