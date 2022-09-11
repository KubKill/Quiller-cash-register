import model.bag.Bag;
import model.fight.Fight;
import model.avatar.Avatar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FightTest {

    private Avatar mockAvatarDead() {
        Avatar mockAvatarDead = Mockito.mock(Avatar.class);
        Mockito.when(mockAvatarDead.isAlive()).thenReturn(false);
        return mockAvatarDead;
    }

    private Avatar createAvatarWithBag() {
        return new Avatar("soldier", new Bag(20), 15, 300);
    }

    @Test
    public void fightConstructor_avatar1NullAvatar2Exists_throwsIllegalArgumentException() {
        Avatar avatar1 = null;
        Avatar avatar2 = createAvatarWithBag();

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Fight(avatar1, avatar2));
    }

    @Test
    public void fightConstructor_avatar1DeadMockAvatar2Alive() {
        Avatar avatar1 = mockAvatarDead();
        Avatar avatar2 = createAvatarWithBag();

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Fight(avatar1, avatar2));
    }
}
