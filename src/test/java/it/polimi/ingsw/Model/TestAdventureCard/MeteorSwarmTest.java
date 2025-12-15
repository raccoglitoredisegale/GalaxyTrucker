package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.MeteorSwarm;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.MeteorCardMessage;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.ShipSetupForTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeteorSwarmTest {
    MeteorSwarm meteorSwarm_2 = new MeteorSwarm(21132, 1, false, false,
            new ArrayList<ArrayList<Character>>(Arrays.asList(new ArrayList<Character>(Arrays.asList('S', 'N')),
                    new ArrayList<Character>(Arrays.asList('S', 'N')),
                    new ArrayList<Character>(Arrays.asList('S', 'W')),
                    new ArrayList<Character>(Arrays.asList('S', 'E')),
                    new ArrayList<Character>(Arrays.asList('S', 'S'))))); // GT-cards_I_IT_0110


    @Test
    public void meteorTest() {
        ArrayList<ArrayList<String>> meteors = new ArrayList<ArrayList<String>>(Arrays.asList(new ArrayList<String>(Arrays.asList("S", "N")),
                new ArrayList<String>(Arrays.asList("S", "N")),
                new ArrayList<String>(Arrays.asList("S", "W")),
                new ArrayList<String>(Arrays.asList("S", "E")),
                new ArrayList<String>(Arrays.asList("S", "S"))));

        ArrayList<Player> playerInorder = new ArrayList<>();
        Player p1 = new Player("cardlo");
        playerInorder.add(p1);
        Player p2 = new Player("franco");
        playerInorder.add(p2);
        ArrayList<Player> emptyList = new ArrayList<>();
        assertEquals(emptyList.size(), meteorSwarm_2.calculatePlayerActionOrder(playerInorder).size());

        assertEquals(meteors.size(), meteorSwarm_2.getCurrentMeteors().size());
        assertEquals(meteors.get(0).get(0), meteorSwarm_2.getCurrentMeteors().get(0).get(0));
        

    }

    @Test
    public void meteorTest2() throws Exception {
        ShipSetupForTest setup = new ShipSetupForTest();
        MeteorCardMessage message = new MeteorCardMessage(MessageType.ASK_SHIP);
        message.setRemovedBatteryFrom(1,2);
        message.setRemovedComponentFrom(4,1);

        AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
        msg.MessageMeteorSwarm(setup.getPlayer(),true, null, message.getRemovedBatteryFrom());
        ApplyEffect applyEffect = new ApplyEffect();
        applyEffect.visit(meteorSwarm_2, msg);
        int n=0;
        for(ComponentCard[] c : setup.getShipboard().getShip()){
            for(ComponentCard cc : c){
              if(cc != null)  n++;
            }
        }

        assertEquals(1, setup.getBatteryCompartment_1().getBatteriesAvailable());
        assertEquals(15, n);

        AdventureCardVisitorMessage msg2 = new AdventureCardVisitorMessage();
        msg2.MessageMeteorSwarm(setup.getPlayer(),false, message.getRemovedComponentFrom(), null);
        applyEffect.visit(meteorSwarm_2, msg2);
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
