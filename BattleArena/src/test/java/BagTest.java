import model.bag.Bag;
import model.items.Item;
import model.items.Weapon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BagTest {

    private Bag bag;
    private Weapon weapon;

    @BeforeEach
    public void setup() {
        bag = new Bag(30);
        weapon = new Weapon("sword", "Dangerous one.", 20);
    }

    @Test
    public void addItem_itemNull_throwsIllegalArgumentException() {
        Item item = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> bag.addItem(item));
    }

    @Test
    public void addItem_itemEmptyBag_Success() {
        Item item = weapon;

        Assertions.assertDoesNotThrow(() -> bag.addItem(item));
    }

    @Test
    public void addItem_itemNotBagButStillSpace_Success() {
        Item item = weapon;
        Item item1 = new Weapon("spike", "Sharp one.", 23);

        bag.addItem(item1);

        Assertions.assertDoesNotThrow(() -> bag.addItem(item));
    }

    @Test
    public void addItem_itemBagFull_throwsIllegalStateException() {
        for (int i = 0; i < 30; i++) {
            bag.addItem(weapon);
        }

        Assertions.assertThrows(IllegalStateException.class, () -> bag.addItem(weapon) );
    }

    @Test
    public void removeItem_itemNull_throwsIllegalArgumentException() {
        Item item = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> bag.removeItem(item));
    }

    @Test
    public void removeItem_itemOneItemInBag_Success() {
        Item item = weapon;
        bag.addItem(weapon);

        Assertions.assertDoesNotThrow(() -> bag.removeItem(item));
    }

    @Test
    public void removeItem_itemNoItemInBag_throwsIllegalStateException() {
        Item item = weapon;

        Assertions.assertThrows(IllegalStateException.class, () -> bag.removeItem(item));
    }

    @Test
    public void removeItem_NoItemInBagButBagFull_throwsIllegalStateException() {
        for (int i = 0; i < 30; i++) {
            bag.addItem(weapon);
        }
        Item spike = new Weapon("spike", "Sharp one.", 23);

        Assertions.assertThrows(IllegalStateException.class, () -> bag.removeItem((spike) ));
    }

    @Test
    public void contains_itemNull_throwsIllegalArgumentException() {
        Item item = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> bag.contains(null));
    }

}
