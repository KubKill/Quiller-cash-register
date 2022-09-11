import model.items.Weapon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class WeaponTest {

    @ParameterizedTest
    @ValueSource(ints = {-5, -100, -5000, -1234567, Integer.MIN_VALUE})
    public void weaponConstructor_damageEqualsNegative_throwsIllegalArgumentException(int upperBondDamage) {
        String name = "sword";
        String description = "Basic sword.";

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Weapon(name, description, upperBondDamage));
    }

    @ParameterizedTest
    @ValueSource(ints = {31, 100, 500, 10000, Integer.MAX_VALUE})
    public void weaponConstructor_damageTooHigh_throwsIllegalArgumentException(int upperBondDamage) {
        String name = "sword";
        String description = "Basic sword.";

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Weapon(name, description, upperBondDamage));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,3, 4, 8, 15, 27, 29})
    public void weaponConstructor_damageEqualsInRange_doesntThrowIllegalArgumentException(int upperBondDamage) {
        String name = "sword";
        String description = "Basic sword.";

        Assertions.assertDoesNotThrow(() -> new Weapon(name, description, upperBondDamage));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 13, 21, 30})
    public void weaponGetDamage_damageInPermittedRange_returnsDamageInPermittedRange(int upperBondDamage) {
        String name = "sword";
        String description = "Basic sword.";

        Weapon sword = new Weapon(name, description, upperBondDamage);

        Assertions.assertTrue(sword.getDamage() >= 0, "Error, damage is too low.");
        Assertions.assertTrue(sword.getDamage()<=upperBondDamage, "Error, damage is too high.");
    }

}
