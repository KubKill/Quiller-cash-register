package model.items;

import model.avatar.Avatar;
import jakarta.persistence.*;
import model.usefull.Range;

@Entity
@Table(name = "weapons")
public class Weapon extends Item {

    @Id
    private long id;

    @Transient
    private static final Range weaponDamageRange = new Range(1, 30);

    @Column(name = "upper_bond_damage")
    private int upperBondDamage;

    @OneToOne(
            mappedBy = "weapon",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Avatar equippedByAvatar;


    public Weapon() {}

    public Weapon(String name, String description, int upperBondDamage) {
        super(name, description);

        if (!weaponDamageRange.checkIfInRange(upperBondDamage))
            throw new IllegalArgumentException("Can't accept argument as damage because it's value is not in permitted range");
        this.upperBondDamage = upperBondDamage;
    }

    public int getDamage() {
        return (int) Math.round(Math.random() * upperBondDamage);
    }

    public int getUpperBondDamage() {
        return upperBondDamage;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "upperBondDamage=" + upperBondDamage +
                '}';
    }
}
