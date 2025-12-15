package it.polimi.ingsw.Model.ComponentCard.SingleComponentCard;

import it.polimi.ingsw.Model.ComponentCard.Connector;

import java.io.Serializable;

public class StructuralModule extends ComponentCard implements Serializable {


    public StructuralModule(String id, boolean IsValid, boolean occupation, Connector[] connectors) {
        super(id, IsValid, occupation, connectors);
    }

    @Override
    public String getShortName() {
        return "STM";
    }
}
