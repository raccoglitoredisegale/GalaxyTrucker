package it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.Visitors;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.*;

import java.io.IOException;

public interface EffectVisitor {

    void visit(Slavers Slavers, AdventureCardVisitorMessage msg) throws IOException;

    void visit(AbandonedStation abandonedStation, AdventureCardVisitorMessage msg) throws IOException;

    void visit(Pirates pirates, AdventureCardVisitorMessage msg) throws IOException;

    void visit(Epidemic epidemic, AdventureCardVisitorMessage msg);

    void visit(AbandonedShip abandonedShip, AdventureCardVisitorMessage msg) throws IOException;

    void visit(MeteorSwarm meteorSwarm, AdventureCardVisitorMessage msg) throws IOException;

    void visit(Warzone warzone, AdventureCardVisitorMessage msg) throws IOException;

    void visit(Planets planets, AdventureCardVisitorMessage msg);

    void visit(OpenSpace openspace, AdventureCardVisitorMessage msg) throws IOException;

    void visit(Stardust stardust, AdventureCardVisitorMessage msg) throws IOException;

    void visit(Smugglers smugglers, AdventureCardVisitorMessage msg) throws IOException;

}
