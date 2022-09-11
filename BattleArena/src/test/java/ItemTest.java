import model.items.Weapon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ItemTest {

    @Test
    public void itemConstructor_nameNull_throwsIllegalArgumentException() {
        //given
        String name = null;
        String description = "This is sword.";
        int upperBondDamage = 20;

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Weapon(name, description, upperBondDamage));
    }

    @Test
    public void itemConstructor_nameEmptyString_throwsIllegalArgumentException() {
        //given
        String name = "";
        String description = "This is sword.";
        int upperBondDamage = 20;

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Weapon(name, description, upperBondDamage));
    }

    @Test
    public void itemConstructor_descriptionNull_throwsIllegalArgumentException() {
        //given
        String name = "sword";
        String description = null;
        int upperBondDamage = 20;

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Weapon(name, description, upperBondDamage));
    }
}
