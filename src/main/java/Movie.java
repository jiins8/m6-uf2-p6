import jakarta.persistence.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PELICULES")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PELICULA_ID_GENERADOR")
    @SequenceGenerator(name = "PELICULA_ID_GENERADOR", sequenceName = "PELICULA_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private int id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "director_id") //insertable = false, updatable = false)
    private Director director;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @PrePersist
    public void ensureDirector() {
        if (director != null && director.getId() != 0) {
            return; // Director is already persisted, do nothing
        }

        EntityManager em = Persistence.createEntityManagerFactory("default").createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            if (director == null) {
                director = new Director();
                director.setName("Default Director");
            }

            em.persist(director);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
