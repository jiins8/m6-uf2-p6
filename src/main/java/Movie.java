import jakarta.persistence.*;

@Entity
@Table(name = "PELICULES")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Basic
    private String title;
    @ManyToOne
    private Director director;
    @ManyToOne
    private Review review;
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
}
