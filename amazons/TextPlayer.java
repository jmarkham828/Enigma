package amazons;

import static amazons.Move.mv;
import static amazons.Move.isGrammaticalMove;

/** A Player that takes input as text commands from the standard input.
 *  @author John Markham
 */
class TextPlayer extends Player {

    /** A new TextPlayer with no piece or controller (intended to produce
     *  a template). */
    TextPlayer() {
        this(null, null);
    }

    /** A new TextPlayer playing PIECE under control of CONTROLLER. */
    private TextPlayer(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new TextPlayer(piece, controller);
    }

    @Override
    String myMove() {
        while (true) {
            String line = _controller.readLine();
            if (line == null) {
                return "quit";
            } else if (isGrammaticalMove(line)) {
                Move possible = mv(line);
                if (board().getMoves().contains(possible)) {
                    continue;
                }
                if (board().getPieces().get(possible
                        .from().index()) != board().turn()) {
                    _controller.reportError("Invalid movement. "
                            + "Please try again.");
                } else if (!board().isLegal(possible)) {
                    _controller.reportError("Invalid movement. "
                            + "Please try again.");
                } else {
                    return line;
                }
            } else {
                return line;
            }
        }
    }
}
