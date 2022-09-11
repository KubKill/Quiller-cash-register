import model.bag.Bag;
import model.avatar.Avatar;
import model.items.Item;
import model.items.Weapon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AvatarTest {

    @Test
    public void avatarConstructorLonger_nameNull_throwsIllegalArgumentException() {
        String name = null;
        Bag bag1 = new Bag(30);
        int initiativePoints = 20;
        int healthPoints = 300;

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Avatar(name, bag1, initiativePoints, healthPoints));
    }

    @Test
    public void avatarConstructorLonger_nameEmptyString_throwsIllegalArgumentException() {
        String name = "";
        Bag bag1 = new Bag(30);

        int initiativePoints = 20;
        int healthPoints = 300;

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Avatar(name, bag1, initiativePoints, healthPoints));
    }

    @Test
    public void pickUpItem_bagNull_throwsIllegalStateException() {
        Avatar avatar = createAvatarWithNoBag();
        Item item = createItem();

        Assertions.assertThrows(IllegalStateException.class, () -> avatar.pickUpItem(item));
    }

    @Test
    public void pickUpItem_bagYesItemNull_throwsIllegalStateException() {
        Avatar avatar = createAvatarWithBag();
        Item item = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> avatar.pickUpItem(item));
    }

    @Test
    public void removeItem_bagNull_throwsIllegalStateException() {
        Avatar avatar = createAvatarWithNoBag();
        Item item = createItem();

        Assertions.assertThrows(IllegalStateException.class, () -> avatar.removeItem(item));
    }

    @Test
    public void removeItem_bagYesItemNull_throwsIllegalStateException() {
        Avatar avatar = createAvatarWithBag();
        Item item = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> avatar.removeItem(item));
    }

    @Test
    public void equipWeapon_weaponNull_throwsIllegalArgumentException() {
        Avatar avatar = createAvatarWithBag();
        Weapon sword = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> avatar.equipWeapon(sword));
    }

    @Test
    public void equipWeapon_noWeaponInTheBag_throwsIllegalArgumentException() {
        Weapon sword = createSword();
        Avatar avatarMockWithSwordInBag = mockAvatarWithSwordInBag();

        Assertions.assertDoesNotThrow(() -> avatarMockWithSwordInBag.hasItemInBag(sword));
    }

    @Test
    public void equipWeapon_avatarWithNoBag_throwsIllegalStateException() {
        Avatar avatarNoBag = createAvatarWithNoBag();
        Weapon sword = createSword();

        Assertions.assertThrows(IllegalStateException.class, () -> avatarNoBag.equipWeapon(sword));
    }

    private Avatar mockAvatarWithSwordInBag() {
        Avatar mockAvatar = Mockito.mock(Avatar.class);
        Mockito.when(mockAvatar.hasItemInBag(createItem())).thenReturn(true);
        return mockAvatar;
    }

    private Avatar createAvatarWithNoBag() {
        return new Avatar("peasant", 25, 150);
    }

    private Avatar createAvatarWithBag() {
        return new Avatar("soldier", new Bag(20), 15, 300);
    }

    private Item createItem() {
        return new Weapon("sword", "Sharp one.", 20);
    }

    private Weapon createSword() {
        return new Weapon("sword", "Sharp one.", 20);
    }

    private Avatar mockSlowAvatar() {
        Avatar mockSlowAvatar = Mockito.mock(Avatar.class);
        Mockito.when(mockSlowAvatar.getInitiativePoints()).thenReturn(10);
        return mockSlowAvatar;
    }

    private Avatar mockFastAvatar() {
        Avatar mockFastAvatar = Mockito.mock(Avatar.class);
        Mockito.when(mockFastAvatar.getInitiativePoints()).thenReturn(20);
        return mockFastAvatar;
    }


}
