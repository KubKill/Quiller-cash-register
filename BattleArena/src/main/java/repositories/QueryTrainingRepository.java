package repositories;

import model.avatar.Avatar;
import model.guild.Guild;
import model.items.Item;
import model.items.Weapon;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QueryTrainingRepository {

    public static void printWeakestWeaponsName(Session session) {
        session.beginTransaction();

        System.out.println("Name of the weakest weapon:");

        Query query = session.createQuery(
                "Select w.name " +
                        "FROM Weapon w " +
                        "ORDER BY w.upperBondDamage ASC")
                .setMaxResults(1);
        String weakestWeaponName = (String) query.getSingleResult();
        System.out.println(weakestWeaponName);

        session.getTransaction().commit();
    }

    public static void printAliveAvatarsNames(Session session) {
        session.beginTransaction();

        System.out.println("Names of alive avatars:");

        Query query = session.createQuery(
                "Select a.name " +
                        "FROM Avatar a " +
                        "WHERE alive = true");
        List<String> aliveNames = query.list();

        if (aliveNames.isEmpty()) System.out.println("Everyone's dead.");
        else {
            for (String name: aliveNames) {
                System.out.println(name);
            }
        }
        session.getTransaction().commit();
    }

    public static List<Weapon> getWeaponsWithMaxDamageInBetween(int lowerBondDamage, int upperBondDamage, Session session) {
        session.beginTransaction();

        Query query = session.createQuery(
                "FROM Weapon w " +
                "WHERE (w.upperBondDamage > :lowerBondDamage " +
                        "AND w.upperBondDamage < :upperBondDamage)")
                .setParameter("lowerBondDamage", lowerBondDamage)
                .setParameter("upperBondDamage", upperBondDamage);

        List<Weapon> weapons = query.list();

        session.getTransaction().commit();

        return (weapons.isEmpty()) ? null : weapons;
    }

    public static List<Avatar> getAvatarsWhoWonFight(Session session) {
        session.beginTransaction();

        Query query = session.createQuery("FROM Avatar a JOIN FETCH a.fightsWinner");
        List<Avatar> avatarsWinners = query.list();

        session.getTransaction().commit();

        return (avatarsWinners.isEmpty()) ? null : avatarsWinners;
    }

    public static List<Avatar> getAvatarsWithNoBag(Session session) {
        session.beginTransaction();

        Query query = session.createQuery("FROM Avatar a LEFT JOIN FETCH a.bag WHERE a.bag = NULL");
        List<Avatar> avatarsWithNoBag = query.list();

        session.getTransaction().commit();

        return (avatarsWithNoBag.isEmpty()) ? null : avatarsWithNoBag;
    }

    /* Polymorphism */
    public static List<Item> getAllItems(Session session) {
        session.beginTransaction();

        Query query = session.createQuery("FROM Item i");
        List<Item> items = query.list();

        session.getTransaction().commit();

        return (items.isEmpty()) ? null : items;
    }

    public static @Nullable List<Avatar> getAvatarsWhoFoughtWithAvatar(@NotNull Avatar avatar, @NotNull Session session) {
        session.beginTransaction();

        Query query = session.createQuery("SELECT f.avatar2 FROM Fight f" +
                " INNER JOIN f.avatar1 a" +
                " INNER JOIN f.avatar2" +
                " WHERE a.id = :id");
        query.setParameter("id", avatar.getId());
        Set<Avatar> formerOpponents = new HashSet<Avatar>((List<Avatar>) query.list());

        Query query1 = session.createQuery("SELECT f.avatar1 FROM Fight f" +
                " INNER JOIN  f.avatar1" +
                " INNER JOIN  f.avatar2 a" +
                " WHERE a.id = :id");
        query1.setParameter("id", avatar.getId());
        formerOpponents.addAll((List<Avatar>) query1.list());

        session.getTransaction().commit();

        return (formerOpponents.isEmpty()) ? null : formerOpponents.stream().toList();
    }

    public static void addNewWeapon(String name, String description, int upperDamageBase, @NotNull Session session) {
        session.beginTransaction();

        Weapon newWeapon = new Weapon(name, description, upperDamageBase);

        session.persist(newWeapon);

        session.getTransaction().commit();
    }

    public static void reviveHeroes(@NotNull Session session) {
        session.beginTransaction();

        Query query = session.createQuery("UPDATE Avatar a " +
                "SET a.alive = true, a.currentHealthPoints = a.maxHealthPoints " +
                "WHERE a.alive = false OR a.currentHealthPoints != a.maxHealthPoints");

        query.executeUpdate();

        session.getTransaction().commit();
    }

    public static void deleteGuild(Guild guild, Session session) {
        session.beginTransaction();

        Query query = session.createQuery("DELETE FROM Guild g where g.id = :id");
        query.setParameter("id", guild.getId());
        query.executeUpdate();

        session.getTransaction().commit();
    }

    public static List<Guild> getGuilds(Session session) {
        session.beginTransaction();

        List<Guild> allGuilds = session.createQuery("FROM Guild").list();

        session.getTransaction().commit();

        return (allGuilds.isEmpty()) ? null : allGuilds;
    }

    public static Avatar getAvatar(int id, Session session) {
        session.beginTransaction();

        return (Avatar) session
                .createQuery("FROM Avatar a WHERE a.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }
}