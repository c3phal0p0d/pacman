package src.actor;

/**
 * Type: Modified file
 * Team Name: Thursday 11:00am Team 1
 * Team Members:
 *      - Jiachen Si (1085839)
 *      - Natasha Chiorsac (1145264)
 *      - Jude Thaddeau Data (1085613)
 */

public enum MonsterType {
    Troll,
    TX5,
    Orion,
    Alien,
    Wizard;

    /**
     * GETS the image FILEPATH of a monster's sprite.
     * @return The image FILEPATH of a monster's sprite.
     */
    public String getImageName() {
        switch (this) {
            case Troll: return "m_troll.gif";
            case TX5: return "m_tx5.gif";
            case Orion: return "m_orion.gif";
            case Alien: return "m_alien.gif";
            case Wizard: return "m_wizard.gif";
            default: {
                assert false;
            }
        }
        return null;
    }
}
