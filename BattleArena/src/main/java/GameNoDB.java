import model.bag.Bag;
import model.fight.Fight;
import model.avatar.Avatar;
import model.items.Weapon;

public class GameNoDB {

    public static void main(String[] args) {

        Bag bag = new Bag(10);
        Weapon sword = new Weapon("sword", "Basic sword.", 20);
        Avatar soldier = new Avatar("soldier", bag, 20, 300);
        Avatar peasant = new Avatar("peasant", 29, 150);

        soldier.pickUpItem(sword);
        soldier.equipWeapon(sword);

        Fight fight = new Fight(peasant, soldier);
        fight.startFight();

    }
}
