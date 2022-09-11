package db;

import model.avatar.Avatar;
import model.bag.Bag;
import model.fight.Fight;
import model.guild.Crest;
import model.guild.Guild;
import model.items.Weapon;
import model.usefull.Message;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class SeedDb {

    Session session;

    public SeedDb(Session session) {
        this.session = session;
    }

    public void seed() {
        List<Weapon> weapons = getWeapons();
        List<Avatar> avatars = getAvatars(weapons);

        session.beginTransaction();

        /* Persists weapons */
        for (Weapon weapon : weapons) {
            session.persist(weapon);
        }

        /* Persists avatars */
        for (Avatar avatar : avatars) {
            session.persist(avatar);
        }

        Message.suppressFightLogs();
        List<Fight> fights = getFights(avatars);

        /* Persists fights */
        for (Fight fight : fights) {
            session.persist(fight);
        }

        Message.enableFightLogs();

        session.getTransaction().commit();

    }

    private List<Weapon> getWeapons() {
        List<Weapon> weapons = new ArrayList<>();

        weapons.add(new Weapon("sword", "Sharp and nice.", 10));
        weapons.add(new Weapon("magic sword", "Blazes.", 17));
        weapons.add(new Weapon("bow", "Radiant quiver.", 8));
        weapons.add(new Weapon("broom", "Uhhh... funny.", 3));
        weapons.add(new Weapon("laser pistol", "Alien stuff.", 25));
        weapons.add(new Weapon("pick", "Pick it up.", 6));

        return weapons;
    }

    private List<Avatar> getAvatars(List<Weapon> weapons) {
        List<Avatar> avatars = new ArrayList<>();

        avatars.add(new Avatar("swords master", new Bag(10), 25, 250));
        avatars.get(0).pickUpItem(weapons.get(0));
        avatars.get(0).equipWeapon(weapons.get(0));
        avatars.get(0).joinGuild(new Guild("Mercenaries", Crest.Colors.RED, Crest.Symbols.RABBIT));

        avatars.add(new Avatar("angel", new Bag(15), 17, 399));
        avatars.get(1).pickUpItem(weapons.get(1));
        avatars.get(1).equipWeapon(weapons.get(1));
        avatars.get(1).joinGuild(new Guild("Haven", Crest.Colors.GREEN, Crest.Symbols.GEM));

        Guild guildCivilians = new Guild("Civilians", Crest.Colors.YELLOW, Crest.Symbols.ONION);

        avatars.add(new Avatar("housekeeper", new Bag(25), 13, 120));
        avatars.get(2).pickUpItem(weapons.get(3));
        avatars.get(2).equipWeapon(weapons.get(3));
        avatars.get(2).joinGuild(guildCivilians);


        avatars.add((new Avatar("homeless dog", 23, 70)));

        avatars.add(new Avatar("miner", new Bag(29), 10, 180));
        avatars.get(4).pickUpItem(weapons.get(5));
        avatars.get(4).equipWeapon(weapons.get(5));
        avatars.get(4).joinGuild(guildCivilians);

        return avatars;
    }

    public List<Fight> getFights(List<Avatar> avatars) {
        List<Fight> fights = new ArrayList<>();

        Avatar dog = avatars.get(3);
        Avatar housekeeper = avatars.get(2);

        fights.add(new Fight(dog, housekeeper));
        fights.get(0).startFight();

        Avatar angel = avatars.get(1);
        Avatar swordsMaster = avatars.get(0);

        fights.add(new Fight(angel, swordsMaster));
        fights.get(1).startFight();

        return fights;
    }
}
