package model.items;

import model.bag.Bag;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "items")
@Inheritance( strategy = InheritanceType.JOINED)
public abstract class Item {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy="increment")
    private Long id;

    @Column( length = 40)
    private String name;
    @Column( length = 1000)
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "inBag_id", referencedColumnName="id")
    private Bag inBag;

    public Item() {}

    public Item(String name, String description) {

        if (name == null || description == null)
            throw new IllegalArgumentException("Can't accept null as argument.");
        if (name.equals("") || description.equals(""))
            throw new IllegalArgumentException("Can't accept empty string as argument");
        if (description.length() > 1000)
            throw new IllegalArgumentException("Can't accept such long string as description argument.");
        if (name.length() > 40)
            throw new IllegalArgumentException("Can't accept such long string as name argument.");

        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Bag getInBag() {
        return inBag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInBag(Bag inBag) {
        this.inBag = inBag;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
