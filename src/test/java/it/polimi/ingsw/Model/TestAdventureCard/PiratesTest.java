package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Dim_Dir;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Pirates;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.BlastCardMessage;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.ShipSetupForTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PiratesTest {


    @Test
    public void PiratesTest() {
        ArrayList<Dim_Dir> shots_2 = new ArrayList<>();
        shots_2.add(new Dim_Dir("S", "S"));
        shots_2.add(new Dim_Dir("S", "S"));
        shots_2.add(new Dim_Dir("B", "S"));
        Pirates pirates_2 = new Pirates(21137, 2, false, false, 6, 2, shots_2, 7);

        assertEquals(2, pirates_2.getDaysLost());
        assertEquals(6, pirates_2.getPirateCannonsPower());
        assertEquals(7, pirates_2.getCreditsGained());
        pirates_2.setControlled(true);
        assertEquals(true, pirates_2.isControlled());

        ArrayList<Player> playerInorder = new ArrayList<>();
        Player p1 = new Player("cardlo");
        playerInorder.add(p1);
        Player p2 = new Player("franco");
        playerInorder.add(p2);
        assertEquals(playerInorder.size(), pirates_2.calculatePlayerActionOrder(playerInorder).size());
        assertEquals(playerInorder.get(0), pirates_2.calculatePlayerActionOrder(playerInorder).get(0));
        assertEquals(playerInorder.get(1), pirates_2.calculatePlayerActionOrder(playerInorder).get(1));

        ArrayList<Dim_Dir> currentShots = new ArrayList<>();
        currentShots.add(new Dim_Dir("S", "S"));
        currentShots.add(new Dim_Dir("S", "S"));
        currentShots.add(new Dim_Dir("B", "S"));
        assertEquals(currentShots.size(), pirates_2.getCurrentShots().size());
        assertEquals(currentShots.get(0).getDirection(), pirates_2.getCurrentShots().get(0).getDirection());
        pirates_2.removeFirstShot();
        assertEquals(currentShots.size()-1, pirates_2.getCurrentShots().size());
        assertEquals(currentShots.get(1).getDirection(), pirates_2.getCurrentShots().get(0).getDirection());
        pirates_2.removeFirstShot();
        pirates_2.removeFirstShot();
        pirates_2.resetCurrentShots();
        assertEquals(currentShots.size(), pirates_2.getCurrentShots().size());
        assertEquals(currentShots.get(0).getDirection(), pirates_2.getCurrentShots().get(0).getDirection());


    }

    @Test
    public void PiratesTest2() throws Exception {
        ArrayList<Dim_Dir> shots_2 = new ArrayList<>();
        shots_2.add(new Dim_Dir("S", "S"));
        shots_2.add(new Dim_Dir("S", "S"));
        shots_2.add(new Dim_Dir("B", "S"));
        Pirates pirates_2 = new Pirates(21137, 2, false, false, 6, 2, shots_2, 7);

        ShipSetupForTest setup = new ShipSetupForTest();

        BlastCardMessage message = new BlastCardMessage(MessageType.ASK_SHIP);
        message.setRemovedBatteryFrom(1,2);
        message.setRemovedComponentFrom(4,1);

        AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
        msg.MessagePirates(true,setup.getPlayer(),setup.getFlightboard(), false, null, null);
        ApplyEffect applyEffect = new ApplyEffect();
        applyEffect.visit(pirates_2, msg);

        assertEquals(7, setup.getPlayer().getCredits());
        assertEquals(setup.getPlayer().getId(), setup.getFlightboard().getTrack().getValue(4));


        AdventureCardVisitorMessage msg2 = new AdventureCardVisitorMessage();
        msg2.MessagePirates(false,setup.getPlayer(),setup.getFlightboard(), true, null, message.getRemovedBatteryFrom());
        applyEffect.visit(pirates_2, msg2);

        int n=0;
        for(ComponentCard[] c : setup.getShipboard().getShip()){
            for(ComponentCard cc : c){
                if(cc != null)  n++;
            }
        }

        assertEquals(1, setup.getBatteryCompartment_1().getBatteriesAvailable());
        assertEquals(15, n);

        AdventureCardVisitorMessage msg3 = new AdventureCardVisitorMessage();
        msg3.MessagePirates(false, setup.getPlayer() ,setup.getFlightboard(), false, message.getRemovedComponentFrom(), null);
        applyEffect.visit(pirates_2, msg3);
        int k=0;
        for(ComponentCard[] c : setup.getShipboard().getShip()){
            for(ComponentCard cc : c){
                if(cc != null) k++;
            }
        }

        assertEquals(14, k);
        assertEquals(null, setup.getShipboard().getShipComponent(4,1));
    }


}
