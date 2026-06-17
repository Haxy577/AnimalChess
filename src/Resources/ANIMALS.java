package Resources;

/**
 * Represents all the possible animals and its corresponding rank in the game "Animal Chess"
 *
 * @author Richmond Jase Von M. Salvador
 * @version 1.6 6/11/2026
 * @since 1.0
 * @see <a href="https://ancientchess.com/page/play-doushouqi.htm">Animal Chess Rules</a>
 */
public enum ANIMALS {

    MOUSE(1),
    CAT(2),
    WOLF(3),
    DOG(4),
    LEOPARD(5),
    TIGER(6),
    LION(7),
    ELEPHANT(8);

    public final int rankNumber;

    ANIMALS(int rankNumber) {
        this.rankNumber = rankNumber;
    }
}
