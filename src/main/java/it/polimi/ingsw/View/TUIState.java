package it.polimi.ingsw.View;

public enum TUIState {
    HELP("help"), // show help command
    SERVER_CONN("server-conn"),
    PRE_LOBBY("pre-lobby"), // choose username, choose whether to create a lobby or join another lobby
    LOBBY("lobby"), // waiting for other players, choose color, create shipboard, after started create deck

    ASSEMBLY_PHASE("assembly-phase"), //
    WELD_COMPONENT_CARD("weld-card"),    // the player decides whether to weld the card, or discard it, or reserve it

    PRE_FLIGHT_PHASE("pre-flight-phase"),
    FLIGHT_PHASE("flight-phase"),
    ADVENTURE_CARD_WAIT("adventure-card-wait"),
    ADVENTURE_CARD_DRAW("adventure-card-draw"),
    STALL("stall"),
    ADVENTURE_CARD_PLANETS("Planets"),
    ADVENTURE_CARD_ABANDONED_STATION("abandoned-station"),
    ADVENTURE_CARD_ABANDONED_SHIP("abandoned-ship"),
    ADVENTURE_CARD_METEOR_SWARM("meteor-swarm"),
    ADVENTURE_CARD_PIRATES("pirates"),
    ADVENTURE_CARD_PIRATES_BLAST("pirates blast"),
    ADVENTURE_CARD_SLAVERS("slavers"),
    ADVENTURE_CARD_EPIDEMIC("epidemic"),
    ADVENTURE_CARD_STARDUST("stardust"),
    ADVENTURE_CARD_OPEN_SPACE("open-space"),
    ADVENTURE_CARD_SMUGGLERS("smugglers"),
    ADVENTURE_CARD_WARZONE("warzone"),
    ADVENTURE_CARD_WARZONE_BLAST("warzone blast"),
    ADVENTURE_CARD_WARZONE_CREW("warzone crew"),
    ADVENTURE_CARD_WARZONE_GOODS("warzone goods"),


    FINAL_PHASE("final-phase");


    private final String state;

    TUIState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
