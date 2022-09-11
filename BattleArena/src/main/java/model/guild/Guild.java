package model.guild;

import model.avatar.Avatar;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "guilds")
public class Guild {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy="increment")
    long id;

    String name;
    int level;

    @Embedded
    Crest crest;

    @ManyToMany(
            mappedBy = "belongsToGuilds",
            fetch = FetchType.LAZY)
    Set<Avatar> avatarsInGuild = new HashSet<>();

    public Guild() {}

    public Guild(String name, Crest.Colors crestColor, Crest.Symbols crestSymbol) {
        if (name == null)
            throw new IllegalArgumentException("Can't accept null as the argument name.");
        if (name.equals(""))
            throw new IllegalArgumentException("Can't accept empty string as the argument name.");
        if (crestColor == null)
            throw new IllegalArgumentException("Can't accept null as the argument crestColor.");
        if (crestSymbol == null)
            throw new IllegalArgumentException("Can't accept null as the argument crestSymbol.");

        this.name = name;
        this.level = 1;
        this.crest = new Crest(crestColor, crestSymbol, this);
    }

    public void addAvatarToGuild(Avatar avatar) {
        if (avatar == null)
            throw new IllegalArgumentException("Can't accept null as the argument avatar.");
        avatarsInGuild.add(avatar);
    }

    public void removeAvatarFromGuild(Avatar avatar) {
        if (avatar == null)
            throw new IllegalArgumentException("Can't accept null as the argument avatar.");
        avatarsInGuild.remove(avatar);
    }

    public boolean hasCrest() {
        return this.crest != null;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guild)) return false;
        Guild guild = (Guild) o;
        return id == guild.id && level == guild.level && name.equals(guild.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, level);
    }
}