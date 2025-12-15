package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.EffectVisitor;
import it.polimi.ingsw.Model.Player.Player;

import java.io.IOException;
import java.util.ArrayList;

public class Pirates extends AdventureCard {

    private final ArrayList<Dim_Dir> shots;

    private final ArrayList<Dim_Dir> currentShots;

//	private ArrayList<Player> defeatedPlayers = new ArrayList<>();

    private final int pirateCannonsPower;

    private final int daysLost;

    private final int credits;

    private boolean controlled;


    public Pirates(int id, int level, boolean turnedUp, boolean completed, int pirateCannonsPower, int daysLost,
                   ArrayList<Dim_Dir> shots, int credits) {
        super(id, level, turnedUp, completed);
        this.pirateCannonsPower = pirateCannonsPower;
        this.shots = shots;
        this.currentShots = new ArrayList<>();
        this.currentShots.addAll(shots);
        this.daysLost = daysLost;
        this.credits = credits;
        setHasActionOrder(true);
        this.controlled = false;

    }

    public int getDaysLost() {
        return daysLost;
    }

    public int getPirateCannonsPower() {
        return pirateCannonsPower;
    }

    public void removeFirstShot() {
        currentShots.removeFirst();
    }

//	public ArrayList<Dim_Dir> getShots() {
//		return shots;
//	}

    public ArrayList<Dim_Dir> getCurrentShots() {
        return currentShots;
    }

//	public ArrayList<Player> getDefeatedPlayers() {
//		return defeatedPlayers;
//	}

//	public void addDefeatedPlayer(Player player) {
//		this.defeatedPlayers.add(player);
//	}

    public int getCreditsGained() {
        return credits;
    }

    public void resetCurrentShots() {
        this.currentShots.addAll(shots);
    }

    public boolean isControlled() {
        return controlled;
    }

    public void setControlled(boolean controlled) {
        this.controlled = controlled;
    }

    @Override
    public CardType getCardType() {
        return CardType.PIRATES;
    }


    @Override
    public ArrayList<Player> calculatePlayerActionOrder(ArrayList<Player> playersInOrder) {
        ArrayList<Player> playerActionOrder = new ArrayList<>(playersInOrder);
        return playerActionOrder;
    }

    @Override
    public void accept(EffectVisitor visitor, AdventureCardVisitorMessage msg) throws IOException {
        visitor.visit(this, msg);
    }

    @Override
    public String getEffectDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("Type: Pirates 🏴‍☠️\n" +
                "Prepare your maximum firepower if you don't want to get roasted. Facing pirates can be risky...but rewarding too!\n");
        sb.append("• Power of opponent cannon: ").append(pirateCannonsPower).append("\n");
        sb.append("• Your fire power necessary: ").append(pirateCannonsPower).append("\n");
        sb.append("• Credits gained if you win: ").append(credits).append("\n");
        sb.append("• Days lost if you win: ").append(daysLost).append("\n");
        sb.append("• Opponent's attack direction: ");

        for (Dim_Dir shot : shots) {
            String size = switch (shot.getDimension()) {
                case "B" -> "big";
                case "S" -> "small";
                default -> "unknown";
            };
            String dir = switch (shot.getDirection()) {
                case "N" -> "North";
                case "S" -> "South";
                case "E" -> "East";
                case "W" -> "West";
                default -> "unknown direction";
            };
            sb.append("[").append(size).append(" from ").append(dir).append("] ");
        }

        return sb.toString();
    }


}
