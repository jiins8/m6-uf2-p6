import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");

    public static void main(String[] args) {
    jakarta.persistence.EntityManager em =entityManagerFactory.createEntityManager();
        System.out.println(em.getProperties());


    }
}
