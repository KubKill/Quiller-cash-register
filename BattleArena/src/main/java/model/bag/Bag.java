package model.bag;

import model.avatar.Avatar;
import model.items.Item;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import model.usefull.Range;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bags")
public class Bag {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy="increment")
    private long id;

    @Transient
    private static final Range bagCapacityRange = new Range(1, 40);

    private int capacity;

    @OneToMany(mappedBy = "inBag", fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    @OneToOne(mappedBy = "bag", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Avatar possessedByAvatar;

    public Bag(){}

    public Bag(int capacity) {
        if (!bagCapacityRange.checkIfInRange(capacity))
            throw new IllegalArgumentException("Capacity not in permitted range.");

        this.capacity = capacity;
    }

    public void addItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't accept null as argument.");

        if (items.size() < capacity) {
            items.add(item);
        } else throw new IllegalStateException("Can't add more items then up to maximum bag capacity");
    }

    public void removeItem(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't accept null as argument.");

        if (items.contains(item)) {
            items.remove(item);
        } else throw new IllegalStateException("Can't remove item that is not in the bag.");
    }

    public boolean contains(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Can't accept null as argument.");
        return this.items.contains(item);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "capacity=" + capacity +
                ", items=" + items +
                '}';
    }
}
