package model.avatar;

import jakarta.persistence.*;
import model.bag.Bag;
import model.fight.Fight;
import model.guild.Guild;
import model.items.Item;
import model.items.Weapon;
import model.usefull.Range;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "avatars")
public class Avatar {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Transient
    private static final Range healthPointsRange = new Range(1, 400);

    @Transient
    private static final Range initiativePointsRange = new Range(1, 30);

    private String name;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "bag_id", referencedColumnName = "id")
    private Bag bag;

    @Column(updatable = false)
    private int initiativePoints;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "weapon_id", referencedColumnName = "id")
    private Weapon weapon;

    @Column(updatable = false)
    private int maxHealthPoints;

    private int currentHealthPoints;

    private boolean alive = true;

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "avatar_guild",
            joinColumns = @JoinColumn(name = "avatar_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "guild_id", referencedColumnName = "id")
    )
    private Set<Guild> belongsToGuilds = new HashSet<>();

    @OneToMany(
            mappedBy = "avatar1",
            fetch = FetchType.LAZY)
    private List<Fight> fightsAvatar1;

    @OneToMany(
            mappedBy = "avatar2",
            fetch = FetchType.LAZY)
    private List<Fight> fightsAvatar2;

    @OneToMany(
            mappedBy = "winner",
            fetch = FetchType.LAZY)
    private List<Fight> fightsWinner;

    public Avatar(String name, Bag bag, int initiativePoints, int maxHealthPoints) {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Can't accept argument null or emty string as name.");
        if (!healthPointsRange.checkIfInRange(maxHealthPoints))
            throw new IllegalArgumentException("Can't accept argument healthPoints " +
                    "because its value is not in permitted range.");
        if (!initiativePointsRange.checkIfInRange(initiativePoints))
            throw new IllegalArgumentException("Can't accept argument initiativePoints " +
                    "because its value is not in permitted range.");

        this.name = name;
        this.bag = bag;
        this.initiativePoints = initiativePoints;
        this.maxHealthPoints = maxHealthPoints;
        this.currentHealthPoints = maxHealthPoints;
        this.weapon = new Weapon("Fists", "You use your fists as weapon", 4);
    }

    public Avatar() {
    }

    public Avatar(String name, int initiativePoints, int maxHealthPoints) {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Can't accept argument null or emty string as name.");
        if (!healthPointsRange.checkIfInRange(maxHealthPoints))
            throw new IllegalArgumentException("Can't accept argument healthPoints " +
                    "because its value is not in permitted range.");
        if (!initiativePointsRange.checkIfInRange(initiativePoints))
            throw new IllegalArgumentException("Can't accept argument initiativePoints " +
                    "because its value is not in permitted range.");

        this.bag = null;
        this.name = name;
        this.weapon = new Weapon("Fists", "You use your fists as weapon", 4);
        this.maxHealthPoints = maxHealthPoints;
        this.currentHealthPoints = maxHealthPoints;
        this.initiativePoints = initiativePoints;
    }

    public void equipWeapon(Weapon weapon) {
        if (weapon == null) throw new IllegalArgumentException("Can't accept null as weapon argument.");
        if (this.bag == null) throw new IllegalStateException("Can't equip any weapon without having bag.");
        if (!hasItemInBag(weapon)) throw new IllegalArgumentException("No such item in the bag.");

        this.weapon = weapon;
    }

    public void pickUpItem(Item item) {
        if (this.bag == null)
            throw new IllegalStateException("Can't do the action because of lack the bag.");
        if (item == null) throw new IllegalArgumentException("Can't accept null as argument.");
        this.bag.addItem(item);
        item.setInBag(this.bag);
    }

    public void removeItem(Item item) {
        if (this.bag == null) throw new IllegalStateException("Can't do the action because of lack the bag.");
        if (item == null) throw new IllegalArgumentException("Can't accept null as argument.");
        this.bag.removeItem(item);
    }

    // Tu jest problem, bo nie chce, zeby to bylo publiczne
    public int attack(Avatar avatar) {
        if (avatar == null) throw new IllegalArgumentException("Can't accept argument null as avatar.");
        return avatar.sufferDamage(this.weapon.getDamage());
    }

    public boolean hasItemInBag(Item item) {
        if (item == null) throw new IllegalArgumentException("Can't accept argument null as item.");
        return bag.contains(item);
    }

    public void updateAliveStatus() {
        if (currentHealthPoints < 0) this.alive = false;
    }

    private int sufferDamage(int damage) {
        this.currentHealthPoints = this.currentHealthPoints - damage;
        return damage;
    }

    public void joinGuild(Guild guild) {
        if (guild == null)
            throw new IllegalArgumentException("Can't accept null as an the argument guild.");
        belongsToGuilds.add(guild);
        guild.addAvatarToGuild(this);
    }

    public void leaveGuild(Guild guild) {
        if (guild == null)
            throw new IllegalArgumentException("Can't accept null as the argument guild.");
        belongsToGuilds.remove(guild);
        guild.removeAvatarFromGuild(this);
    }

    public void reviveOrHeal() {
        this.currentHealthPoints = this.maxHealthPoints;
        this.alive = true;
    }

    public long getId() {
        return id;
    }

    public Bag getBag() {
        return bag;
    }

    public int getInitiativePoints() {
        return initiativePoints;
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return alive;
    }

    public List<Fight> getFightsWinner() {
        return fightsWinner;
    }

    public Set<Guild> getBelongsToGuilds() {
        return belongsToGuilds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avatar)) return false;
        Avatar avatar = (Avatar) o;
        return id == avatar.id && initiativePoints == avatar.initiativePoints && maxHealthPoints == avatar.maxHealthPoints && alive == avatar.alive && healthPointsRange.equals(avatar.healthPointsRange) && initiativePointsRange.equals(avatar.initiativePointsRange) && name.equals(avatar.name) && Objects.equals(bag, avatar.bag) && weapon.equals(avatar.weapon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, healthPointsRange, initiativePointsRange, name, bag, initiativePoints, weapon, maxHealthPoints, alive);
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "name='" + name + '\'' +
                ", bag=" + bag +
                ", initiativePoints=" + initiativePoints +
                ", weapon=" + weapon +
                ", healthPoints=" + maxHealthPoints +
                ", alive=" + alive +
                '}';
    }
}
