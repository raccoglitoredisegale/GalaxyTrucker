package it.polimi.ingsw.View;

import it.polimi.ingsw.Network.Client.Client;
import it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage.*;

public interface CalculateAdventureCardInteraction {
    MeteorCardMessage calculateMeteorCardMessage(Client client, Character dimension, int direction, int dice);

    PlanetsCardMessage calculatePlanetsCardMessage(Client client);

    AbandonedStationCardMessage calculateAbandonedStationCardMessage(Client client);

    OpenSpaceCardMessage calculateOpenSpaceMessage(Client client);

    AbandonedShipCardMessage calculateAbandonedShipMessage(Client client);

    SlaversWonCardMessage calculateSlaversWonMessage(Client client);

    SlaversLostCardMessage calculateSlaversLostMessage(Client client);

    SmugglersWonCardMessage calculateSmugglersWonMessage(Client client);

    SmugglersLostCardMessage calculateSmugglersLostMessage(Client client);

    SmugglersCardMessage calculateSmugglersMessage(Client client);

    PiratesWonCardMessage calculatePiratesWonMessage(Client client);

    BlastCardMessage calculateBlastCardMessage(Client client, Character dimension, int direction, int dice);

    WarzoneControlCardMessage calculateWarzoneCannonMessage(Client client);

    WarzoneControlCardMessage calculateWarzoneEngineMessage(Client client);

    WarzoneCrewPenaltyCardMessage calculateWarzoneCrewPenaltyMessage(Client client);

}
