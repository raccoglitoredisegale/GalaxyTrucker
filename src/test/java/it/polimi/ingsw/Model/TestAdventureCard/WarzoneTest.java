package it.polimi.ingsw.Model.TestAdventureCard;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Smugglers;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.AdventureCardVisitorMessage;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors.ApplyEffect;
import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Warzone;
import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.BlastCardMessage;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.WarzoneCrewPenaltyCardMessage;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.View.ShipSetupForTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarzoneTest {
    Warzone warzone_2 = new Warzone(21149, 1, false, false, new ArrayList<ArrayList<Character>>(Arrays.asList(new ArrayList<Character>(Arrays.asList('C', 'D')),
            new ArrayList<Character>(Arrays.asList('E', 'G')),
            new ArrayList<Character>(Arrays.asList('T', 'C')))), 4, 0, 3, new ArrayList<ArrayList<String>>(Arrays.asList(new ArrayList<String>(Arrays.asList("S", "N")),
            new ArrayList<String>(Arrays.asList("S", "W")), new ArrayList<String>(Arrays.asList("S", "E")),
            new ArrayList<String>(Arrays.asList("B", "S"))))); // GT-cards_II_IT_0116

    @Test
    public void WarzoneTest() {
        ArrayList<ArrayList<Character>> criteria = new ArrayList<ArrayList<Character>>(Arrays.asList(new ArrayList<Character>(Arrays.asList('C', 'D')),
                new ArrayList<Character>(Arrays.asList('E', 'G')),
                new ArrayList<Character>(Arrays.asList('T', 'C'))));

        assertEquals(criteria.size(), warzone_2.getWarzoneCriteria().size());
        assertEquals(criteria.get(0), warzone_2.getWarzoneCriteria().get(0));
        assertEquals(4, warzone_2.getDaysLost());
        assertEquals(0, warzone_2.getCrewLost());
        assertEquals(3, warzone_2.getGoodsLost());

       ArrayList<ArrayList<String>> currentblast = new ArrayList<ArrayList<String>>(Arrays.asList(new ArrayList<String>(Arrays.asList("S", "N")),
                new ArrayList<String>(Arrays.asList("S", "W")), new ArrayList<String>(Arrays.asList("S", "E")),
                new ArrayList<String>(Arrays.asList("B", "S"))));

        assertEquals(currentblast.size(), warzone_2.getCurrentBlastArray().size());
        assertEquals(currentblast.get(0), warzone_2.getCurrentBlastArray().get(0));

        ArrayList<Player> playerInorder = new ArrayList<>();
        Player p1 = new Player("cardlo");
        playerInorder.add(p1);
        Player p2 = new Player("franco");
        playerInorder.add(p2);
        assertEquals(playerInorder.size(), warzone_2.calculatePlayerActionOrder(playerInorder).size());
        assertEquals(playerInorder.get(0), warzone_2.calculatePlayerActionOrder(playerInorder).get(0));
        assertEquals(playerInorder.get(1), warzone_2.calculatePlayerActionOrder(playerInorder).get(1));

        warzone_2.setWait(true);
        warzone_2.setFirstControl(true);
        warzone_2.setSecondControl(true);
        warzone_2.setThirdControl(true);
        assertEquals(true, warzone_2.getFirstControl());
        assertEquals(true, warzone_2.getSecondControl());
        assertEquals(true, warzone_2.getThirdControl());
        assertEquals(true, warzone_2.getWait());

        warzone_2.setFirstPlayersParameters(p1, 1);
        warzone_2.setFirstPlayersParameters(p2, 3);

        warzone_2.setSecondPlayersParameters(p1, 4);
        warzone_2.setSecondPlayersParameters(p2, 1);

        assertEquals(1, warzone_2.getFirstPlayersParameters().get("cardlo"));
        assertEquals(p1.getUsername(), warzone_2.getLoser(warzone_2.getFirstPlayersParameters()));
        assertEquals(p2.getUsername(), warzone_2.getLoser(warzone_2.getSecondPlayersParameters()));



    }

    @Test
    public void WarzoneTest2() throws Exception{
        ShipSetupForTest setup = new ShipSetupForTest();

        AdventureCardVisitorMessage msg = new AdventureCardVisitorMessage();
        msg.MessageWarzone(setup.getPlayer(),setup.getFlightboard(), null, false,null,null,null,1);
        ApplyEffect applyEffect = new ApplyEffect();
        applyEffect.visit(warzone_2, msg);

        assertEquals(setup.getPlayer().getId(), setup.getFlightboard().getTrack().getValue(2));
/// ////////////////////////

        WarzoneCrewPenaltyCardMessage message = new WarzoneCrewPenaltyCardMessage(MessageType.ASK_SHIP);
        message.setRemovedCrewFrom(2,2);
        message.setRemovedCrewFrom(2,3);
        AdventureCardVisitorMessage msg2 = new AdventureCardVisitorMessage();
        msg2.MessageWarzone(setup.getPlayer(),setup.getFlightboard(), message.getRemovedCrewFrom(), false,null,null,null,2);
        applyEffect.visit(warzone_2, msg2);

        Smugglers smugglers_1 = new Smugglers(21111, 2, false, false, 1, 8, new ArrayList<Goods>( // missing cargo lost
                Arrays.asList(Goods.RED, Goods.YELLOW, Goods.YELLOW)), 3); // GT-cards_II_IT_012


        assertEquals(1, setup.getCabin1().getOccupantNumber());
        assertEquals(1, setup.getStr().getOccupantNumber());
/// ///////////////////////////
        Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap = new HashMap<>();
        ArrayList<Integer> coords1 = new ArrayList<>();
        ArrayList<Integer> coords2 = new ArrayList<>();
        coords1.add(1);
        coords1.add(4);
        coords2.add(3);
        coords2.add(4);
        ArrayList<Goods> goods1 = new ArrayList<>();
        ArrayList<Goods> goods2 = new ArrayList<>();
        goods1.add(Goods.YELLOW);
        goods1.add(Goods.GREEN);
        goods2.add(Goods.RED);
        goodsMap.put(coords1, goods1);
        goodsMap.put(coords2, goods2);

        AdventureCardVisitorMessage msg3 = new AdventureCardVisitorMessage();
        msg3.MessageSmugglers(true,setup.getPlayer(), setup.getFlightboard(), goodsMap, null);
        applyEffect.visit(smugglers_1, msg3);

        AdventureCardVisitorMessage msg4 = new AdventureCardVisitorMessage();
        msg4.MessageWarzone(setup.getPlayer(),setup.getFlightboard(), null, false,null,null,goodsMap,4);
        applyEffect.visit(warzone_2, msg4);

        assertEquals(0, setup.getHold1().getCurrentCargo());
        assertEquals(0, setup.getHold3().getCurrentCargo());

        /// ///////////////////

        BlastCardMessage message2 = new BlastCardMessage(MessageType.ASK_SHIP);
        message2.setRemovedBatteryFrom(1,2);
        message2.setRemovedComponentFrom(4,1);

        AdventureCardVisitorMessage msg5 = new AdventureCardVisitorMessage();
        msg5.MessageWarzone(setup.getPlayer(),setup.getFlightboard(), null, true,null,message2.getRemovedBatteryFrom(),null,3);
        applyEffect.visit(warzone_2, msg5);

        int n=0;
        for(ComponentCard[] c : setup.getShipboard().getShip()){
            for(ComponentCard cc : c){
                if(cc != null)  n++;
            }
        }

        assertEquals(1, setup.getBatteryCompartment_1().getBatteriesAvailable());
        assertEquals(15, n);

        AdventureCardVisitorMessage msg6 = new AdventureCardVisitorMessage();
        msg6.MessageWarzone(setup.getPlayer(),setup.getFlightboard(), null, false,message2.getRemovedComponentFrom(),null,null,3);
        applyEffect.visit(warzone_2, msg6);
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
