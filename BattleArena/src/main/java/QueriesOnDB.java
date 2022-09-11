import db.InitDbConnection;
import db.SeedDb;
import jakarta.persistence.Query;
import model.avatar.Avatar;
import model.guild.Guild;
import model.items.Item;
import model.items.Weapon;
import org.hibernate.Session;
import repositories.QueryTrainingRepository;

import java.util.List;

public class QueriesOnDB {

    public static void main(String[] args) {

        // ////////////////////////////////////////////////////////////////////////////////////////////////////////
                                                    /* Setup */
        // ////////////////////////////////////////////////////////////////////////////////////////////////////////

        /* Creates connection with database. */
        InitDbConnection dbConnection = new InitDbConnection();
        dbConnection.createConnection();
        Session sessionSeed = dbConnection.getSession();

        /* Seeds database. */
        SeedDb seedDb = new SeedDb(sessionSeed);
        seedDb.seed();

        // ////////////////////////////////////////////////////////////////////////////////////////////////////////
                                                    /* Queries */
        // ////////////////////////////////////////////////////////////////////////////////////////////////////////

        /* Prints alive avatars names */
        QueryTrainingRepository.printAliveAvatarsNames(dbConnection.getSession());

        /* Prints the name of the weakest weapon */
        System.out.println("\n");
        QueryTrainingRepository.printWeakestWeaponsName(dbConnection.getSession());

        /* Returns weapons with max damage in between specified parameters */
        int lowerBondDamage = 10;
        int upperBondDamage = 20;
        System.out.println("\nWeapons with max dmg in between " + lowerBondDamage + " and " + upperBondDamage + " :");
        List<Weapon> weapons = QueryTrainingRepository
                .getWeaponsWithMaxDamageInBetween(lowerBondDamage, upperBondDamage, dbConnection.getSession());
        for (Weapon weapon :
                weapons) {
            System.out.println(weapon.getName() + " : " + weapon.getUpperBondDamage() + " max dmg");
        }

        /* Returns avatars who won any fight */
        System.out.println("\nAvatars who won fight :");
        List<Avatar> avatarsWinners = QueryTrainingRepository.getAvatarsWhoWonFight(dbConnection.getSession());
        for (Avatar avatar :
                avatarsWinners) {
            System.out.println(avatar.getName() + ", won fights " + avatar.getFightsWinner().size());
        }

        /* Returns avatars with no bag */
        System.out.println("\nAvatars with no bag :");
        List<Avatar> avatarsWithNoBag = QueryTrainingRepository.getAvatarsWithNoBag(dbConnection.getSession());
        for (Avatar avatar :
                avatarsWithNoBag) {
            System.out.println(avatar.getName());
        }

        /* Returns all items */
        System.out.println("\nAll items :");
        List<Item> items = QueryTrainingRepository.getAllItems(dbConnection.getSession());
        for (Item item :
                items) {
            System.out.println(item.getName());
        }

        /* Returns avatars who had any fight with the specified avatar  */
        Avatar avatarQuery = avatarsWinners.get(1);
        System.out.println("\n" + avatarQuery.getName() + " fought with :");
        List<Avatar> formerOpponents = QueryTrainingRepository
                .getAvatarsWhoFoughtWithAvatar(avatarQuery, dbConnection.getSession());
        for (Avatar avatar :
                formerOpponents) {
            System.out.println(avatar.getName());
        }

        /* Adds new weapon with specified parameters */
        System.out.println("\nAdding a new weapon");
        QueryTrainingRepository.addNewWeapon("axe", "Dwarven one.", 12, dbConnection.getSession());

        /* Restores health to all avatars */
        System.out.println("\nRestoring health to all avatars");
        QueryTrainingRepository.reviveHeroes(dbConnection.getSession());

        /* Returns all guilds */
        List<Guild> allGuilds = QueryTrainingRepository.getGuilds(dbConnection.getSession());

        /* Deletes specified guild */
        System.out.println("\nDeleting the specified guild");
        QueryTrainingRepository.deleteGuild(allGuilds.get(0), dbConnection.getSession());


        dbConnection.shutDownConnection();
    }
}
