package model.usefull;


import model.avatar.Avatar;

import java.io.OutputStream;
import java.io.PrintStream;

public class Message {

    private static PrintStream originalStream = System.out;
    private static PrintStream dummyStream = new PrintStream(new OutputStream(){
        public void write(int b) {
            // NO-OP
        }
    });

    public Message() {
    }

    public static void attackBattleLog(Avatar attacker, Avatar attacked, int dealtDamage) {
        System.out.println(attacker.getName() + " " +"attacks " + attacked.getName() + ". \n" +
        attacked.getName() + " suffers " + dealtDamage + " damage.");
    }

    public static void roundBattleLog(int roundNumber) {
        System.out.println(roundNumber + " round has begun!");
    }

    public static void starFightLog(Avatar avatar1, Avatar avatar2) {
        System.out.println("Fight between " + avatar1.getName() + " and " + avatar2.getName() + " has begun!" );
    }

    public static void survivedBattleLog(Avatar attacked) {
        if (attacked.isAlive()) {
            System.out.println(attacked.getName() + " survived the attack!");
        } else {
            System.out.println(attacked.getName() + " died after the attack!");
        }
    }

    public static void declareWinner(Avatar winner) {
        System.out.println("The winner of the fight is " + winner.getName() +"!");
    }

    public static void emptyLine(){
        System.out.println("");
    }

    public static void doubleEmptyLine() {
        System.out.println(" \n");
    }

    public static void suppressFightLogs() {
        System.setOut(Message.dummyStream);
    }

    public static void enableFightLogs() {
        System.setOut(Message.originalStream);
    }
}
