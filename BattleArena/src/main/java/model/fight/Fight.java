package model.fight;

import model.avatar.Avatar;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import model.usefull.Message;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fights")
public class Fight {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy="increment")
    private long id;

    @ManyToOne()
    @JoinColumn(name = "avatar1_id", referencedColumnName = "id")
    private Avatar avatar1;

    @ManyToOne()
    @JoinColumn(name = "avatar2_id", referencedColumnName = "id")
    private Avatar avatar2;

    @ManyToOne()
    @JoinColumn(name = "avatarWinner_id", referencedColumnName = "id")
    private Avatar winner;

    private int rounds = 1;

    public Fight() {}

    public Fight(Avatar avatar1, Avatar avatar2) {

        if (avatar1 == null || avatar2 == null)
            throw new IllegalArgumentException("Can't accept argument null as any avatar");
        if (!avatar1.isAlive() || !avatar2.isAlive())
            throw new IllegalArgumentException("Can't accept dead avatar as argument.");

        this.avatar1 = avatar1;
        this.avatar2 = avatar2;
    }

    public void startFight() {
        Message.starFightLog(avatar1, avatar2);
        Message.doubleEmptyLine();

        List<Avatar> fightOrder = determineOrder(avatar1, avatar2);

        Avatar quickerAvatar = fightOrder.get(0);
        Avatar slowerAvatar = fightOrder.get(1);

        while (avatar1.isAlive() && avatar2.isAlive()) {
            Message.roundBattleLog(rounds);
            Message.emptyLine();

            int dealtDamage = quickerAvatar.attack(slowerAvatar);
            Message.attackBattleLog(quickerAvatar, slowerAvatar, dealtDamage);

            slowerAvatar.updateAliveStatus();
            Message.survivedBattleLog(slowerAvatar);

            if (!slowerAvatar.isAlive()) {
                winner = quickerAvatar;
                break;
            }

            Message.emptyLine();

            dealtDamage = slowerAvatar.attack(quickerAvatar);
            Message.attackBattleLog(slowerAvatar, quickerAvatar, dealtDamage);

            quickerAvatar.updateAliveStatus();
            Message.survivedBattleLog(quickerAvatar);

            if (!quickerAvatar.isAlive()) {
                winner = slowerAvatar;
                break;
            }

            finishRound();
            Message.emptyLine();
        }

        Message.emptyLine();
        Message.declareWinner(winner);

    }

    private List<Avatar> determineOrder(Avatar avatar1, Avatar avatar2) {
        List<Avatar> fightOrder = new ArrayList<>();
        if (avatar1.getInitiativePoints() > avatar2.getInitiativePoints()) {
            fightOrder.add(avatar1);
            fightOrder.add(avatar2);
        } else {
            fightOrder.add(avatar2);
            fightOrder.add(avatar1);
        }
        return fightOrder;
    }

    private void finishRound() {
        this.rounds += 1;
    }

    public Avatar getWinner() {
        return winner;
    }
}
