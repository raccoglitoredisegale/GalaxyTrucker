package it.polimi.ingsw.Model.ComponentCard;

import it.polimi.ingsw.Controller.Exceptions.DeckException;
import it.polimi.ingsw.Controller.LoadElements.LoadComponentCards;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.Cabin;
import it.polimi.ingsw.Model.Player.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ComponentCardDeck implements Serializable {

    private final ArrayList<String> componentCardIds;
    private final ArrayList<String> revealedCards;

    private final ArrayList<Cabin> startingCabins;


    public ComponentCardDeck() {

        LoadComponentCards ldc = new LoadComponentCards();
        this.componentCardIds = ldc.getIdsOfComponentCard();

        this.startingCabins = ldc.getStartingCabins();

        this.revealedCards = new ArrayList<>();
    }

    public String getComponentCardFromRevealedDeck(int idxOfComponentCard) throws DeckException {
        synchronized (revealedCards) {
            if (idxOfComponentCard < 0 || idxOfComponentCard >= revealedCards.size()) {
                throw new DeckException("Invalid card index!");
            }
            String componentCardID = this.revealedCards.get(idxOfComponentCard);
            this.revealedCards.remove(idxOfComponentCard);
            return componentCardID;
        }
    }

    public void updateRevealedCard(String id) {
        synchronized (revealedCards) {
            this.revealedCards.add(id);
        }
    }

    /**
     * @componentCardIds represents the cards that are covered in the center, once the client draws one it will no longer be available
     * to other clients. In fact, he can decide whether to pay it to his shipboard,
     * reserve it or put it back in the center, and at that point the card will be added to the RevealedCard arraylist,
     * or the uncovered cards, which can be seen by all clients
     */
    public String getCardID() throws DeckException {
        synchronized (this.componentCardIds) {
            try {
                if (this.componentCardIds.isEmpty()) {
                    throw new DeckException("The deck of face-down component cards in the center is finished!");
                }
                Collections.shuffle(this.componentCardIds);
                String id = this.componentCardIds.getFirst();
                this.componentCardIds.removeFirst();
                return id;

            } catch (Exception e) {
                throw new DeckException("The deck of face-down component cards in the center is finished!");
            }
        }
    }

    public ArrayList<String> getRevealedCard() {
        synchronized (revealedCards) {
            return this.revealedCards;
        }
    }

    public Cabin getStartingCabin(Color color) {
        return switch (color) {
            case BLUE -> this.startingCabins.get(0);
            case GREEN -> this.startingCabins.get(1);
            case RED -> this.startingCabins.get(2);
            case YELLOW -> this.startingCabins.get(3);
        };
    }
}
