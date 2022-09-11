package model.guild;

import jakarta.persistence.*;

@Embeddable
public class Crest {

    public enum Colors {RED, GREEN, BLUE, YELLOW}
    public enum Symbols {SWORD, RABBIT, GEM, ONION}

    @Column(name = "crest_color")
    @Enumerated(EnumType.STRING)
    private Colors color;

    @Column(name = "crest_symbol")
    @Enumerated(EnumType.STRING)
    private Symbols symbol;

    public Crest(){}

    public Crest(Colors color, Symbols symbol, Guild guild) {
        if (color == null)
            throw new IllegalArgumentException("Can't accept null as the argument color.");
        if (symbol == null)
            throw new IllegalArgumentException("Can't accept null as the argument symbol.");
        if (guild.hasCrest())
            throw new IllegalStateException("Guild aready has crest.");

        this.color = color;
        this.symbol = symbol;
    }
}
