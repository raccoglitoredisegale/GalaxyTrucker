package it.polimi.ingsw.Model.ComponentCardTest;

import it.polimi.ingsw.Model.ComponentCard.Connector;
import it.polimi.ingsw.Model.ComponentCard.SingleComponentCard.ComponentCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComponentCardTest {

    @Test
    public void componentCardTest() {
        Connector[] conns = new Connector[]{
                new Connector(3, true), new Connector(2, true), new Connector(0, true), new Connector(3, true)};
        ComponentCard card = new ComponentCard("1",true,true,conns);
        Connector[] conns2 = conns;
        card.setValid(false);
        card.setOccupation(false);
        assertEquals(false, card.isValid());
        assertEquals(false, card.isOccupation());
        assertEquals("???", card.getShortName());
        assertEquals("", card.getFacingSymbol());
        assertEquals("1", card.getId());
        assertEquals(conns2, card.getConnectors());
        Connector[] conns3 = new Connector[]{
                new Connector(3, true), new Connector(3, true), new Connector(2, true), new Connector(0, true)};
        card.rotation();
        assertEquals(conns3[0].getTubeNumber(), card.getConnectors()[0].getTubeNumber());
        assertEquals(conns3[1].getTubeNumber(), card.getConnectors()[1].getTubeNumber());
        assertEquals(conns3[2].getTubeNumber(), card.getConnectors()[2].getTubeNumber());
        assertEquals(conns3[3].getTubeNumber(), card.getConnectors()[3].getTubeNumber());

    }
}
