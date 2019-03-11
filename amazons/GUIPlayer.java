package amazons;

/** A Player that takes input from a GUI.
 *  @author John Markham
 */
class GUIPlayer extends Player implements Reporter {

    /** A new GUIPlayer that takes moves and commands from GUI.  */
    GUIPlayer(GUI gui) {
        this(null, null, gui);
    }

    /** A new GUIPlayer playing PIECE under control of CONTROLLER, taking
     *  moves and commands from GUI. */
    GUIPlayer(Piece piece, Controller controller, GUI gui) {
        super(piece, controller);
        _gui = gui;
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new GUIPlayer(piece, controller, _gui);
    }

    @Override
    String myMove() {
        String move = _gui.readCommand();
        move = move.trim();
        if (move.trim().equals("undo")) {
            undoMove(move);
        } else if (!move.trim().equals("quit")
            && !move.trim().equals("new")
            && !move.trim().equals("undo")) {
            reportMove(move);
        }
        return move;
    }

    /** Undoes a move. */
    public void undoMove(String unused) {
        board().undo();
        _gui.update(board());
    }

    @Override
    public void reportError(String fmt, Object... args) {
        _gui.reportError(fmt, args);
    }

    @Override
    public void reportNote(String fmt, Object... args) {
        _gui.reportNote(fmt, args);
    }

    @Override
    public void reportMove(Move unused) {
        _gui.reportMove(unused);
    }

    /** Helper in dissecting the
     * string Move into an actual move.
     * @param move the move to parse.
     * */
    public void reportMove(String move) {
        String[] toFrom = move.split("-");
        String from = toFrom[0];
        String preTo = toFrom[1];
        String[] toSplit = preTo.split("\\(");
        String to = toSplit[0].trim();
        String spear = toSplit[1].replace(")", "").trim();
        Square fromSquare = Square.sq(from);
        Square toSquare = Square.sq(to);
        Square spearSquare = Square.sq(spear);
        Move toReport = Move.mv(fromSquare, toSquare, spearSquare);
        reportMove(toReport);
    }

    /** The GUI I use for input. */
    private GUI _gui;
}
