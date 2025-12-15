package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Controller.LoadElements.LoadComponentCards;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AdventureCard;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Cabin;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import it.polimi.ingsw.Model.Player.Color;
import it.polimi.ingsw.Network.Client.Exception.InvalidComponentCardID;

import java.util.ArrayList;

public class ClientGameController {

    private final ArrayList<ComponentCard> componentCard;
    private final ArrayList<Cabin> startingCabins;
    private AdventureCard currentAdventureCard;
    private final ArrayList<String> diceAndInfo;
    private boolean isDefeated = false;
    private boolean MTcontrolled;

    public ClientGameController() {
        LoadComponentCards ldc = new LoadComponentCards();
        this.componentCard = new ArrayList<>();

        this.startingCabins = new ArrayList<>();
        this.startingCabins.addAll(ldc.getStartingCabins());

        this.componentCard.addAll(ldc.getHolds());
        this.componentCard.addAll(ldc.getStructuralModules());
        this.componentCard.addAll(ldc.getBatteryCompartments());
        this.componentCard.addAll(ldc.getCannons());
        this.componentCard.addAll(ldc.getCabins());
        this.componentCard.addAll(ldc.getStartingCabins());
        this.componentCard.addAll(ldc.getShieldGenerators());
        this.componentCard.addAll(ldc.getAlienLifeSupports());
        this.componentCard.addAll(ldc.getEngines());
        diceAndInfo = new ArrayList<>();
        MTcontrolled = false;
    }

    public AdventureCard getCurrentAdventureCard() {
        return this.currentAdventureCard;
    }

    public void updateCurrentAdventureCard(AdventureCard card) {
        this.currentAdventureCard = card;
    }


    public ComponentCard getComponentCard(String componentCardId) throws InvalidComponentCardID {
        for (ComponentCard cc : this.componentCard) {
            if (cc.getId().equals(componentCardId)) {
                return cc;
            }
        }
        throw new InvalidComponentCardID("Invalid component card ID!");
    }

    public void setDiceAndInfo(String dimension, String direction, String dice) {
        try {
            MTcontrolled = true;
            diceAndInfo.clear();
            diceAndInfo.add(dimension);
            diceAndInfo.add(direction);
            diceAndInfo.add(dice);
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<String> getDiceAndInfo() {
        return diceAndInfo;
    }

    public boolean getIsDefeated() {
        return this.isDefeated;
    }

    public void setIsDefeated(boolean isDefeated) {
        this.isDefeated = isDefeated;
    }

    public boolean getMTcontrolled() {
        return this.MTcontrolled;
    }

    public void setMTcontrolled(boolean MTcontrolled) {
        this.MTcontrolled = MTcontrolled;
    }

    /**
     * startingCabins = [Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW]
     */
    public Cabin getStartingCabin(Color color) {
        return switch (color) {
            case BLUE -> this.startingCabins.get(0);
            case GREEN -> this.startingCabins.get(1);
            case RED -> this.startingCabins.get(2);
            case YELLOW -> this.startingCabins.get(3);
        };
    }
}
