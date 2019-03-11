package amazons;

import ucb.gui2.Pad;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import static amazons.Piece.BLACK;
import static amazons.Piece.WHITE;
import static amazons.Piece.EMPTY;
import static amazons.Piece.SPEAR;
import static amazons.Square.sq;

/** A widget that displays an Amazons game.
 *  @author John Markham
 */
class BoardWidget extends Pad {

    /**
     * Colors of empty squares and grid lines.
     */
    private static final Color
            DARK_SQUARE_COLOR = new Color(27, 28, 27),
            LIGHT_SQUARE_COLOR = new Color(255, 199, 139),
            RED = new Color(254, 82, 86),
            WHITE_TEXT = new Color(255, 255, 255);

    /**
     * Locations of images of white and black queens.
     */
    private static final String
            WHITE_QUEEN_IMAGE = "lb4.png",
            BLACK_QUEEN_IMAGE = "gq4.png",
            SPEAR_IMAGE = "spear.png",
            COIN_IMAGE = "coin.png",
            WINNER_IMAGE = "winner.png";

    /**
     * Size parameters.
     */
    private static final int
            SQUARE_SIDE = 60,
            BOARD_SIDE = SQUARE_SIDE * 10;

    /**
     * A graphical representation of an Amazons board that sends commands
     * derived from mouse clicks to COMMANDS.
     */
    BoardWidget(ArrayBlockingQueue<String> commands) {
        _commands = commands;
        setMouseHandler("click", this::mouseClicked);
        setPreferredSize(BOARD_SIDE, BOARD_SIDE);

        try {
            _whiteQueen = ImageIO.read(Utils.getResource(WHITE_QUEEN_IMAGE));
            _blackQueen = ImageIO.read(Utils.getResource(BLACK_QUEEN_IMAGE));
            _spearImage = ImageIO.read(Utils.getResource(SPEAR_IMAGE));
            _coinImage = ImageIO.read(Utils.getResource(COIN_IMAGE));
            _winnerImage = ImageIO.read(Utils.getResource(WINNER_IMAGE));

        } catch (IOException excp) {
            System.err.println("Could not read queen images.");
            System.exit(1);
        }
        _acceptingMoves = false;
    }

    /**
     * Draw the bare board G.
     */
    private void drawGrid(Graphics2D g) {
        int counter = 0;
        _drawer = g;
        for (int i = 0; i < BOARD_SIDE; i += SQUARE_SIDE, counter += 1) {
            if (counter % 2 == 0) {
                drawWhiteStart(g, i);
            } else {
                drawBlackStart(g, i);
            }
        }
        int turns = _board.numMoves();
        g.setFont(new Font("default", Font.BOLD, TURN_FONT));
        g.setColor(WHITE_TEXT);
        g.drawString("# Turns: " + turns, TURN_X, TURN_Y);
    }


    /** Draw a column that starts with a white square.
     * @param col the current counter for the column.
     * @param g the drawer for the board.
     * */
    public void drawWhiteStart(Graphics2D g, int col) {
        int counter = 0;
        for (int i = 0; i < BOARD_SIDE; i += SQUARE_SIDE, counter += 1) {
            if (counter % 2 == 0) {
                g.setColor(LIGHT_SQUARE_COLOR);
                g.fillRect(col, i, BOARD_SIDE, BOARD_SIDE);
            } else {
                g.setColor(DARK_SQUARE_COLOR);
                g.fillRect(col, i, BOARD_SIDE, BOARD_SIDE);
            }
        }
    }

    /** Draw a column that starts with a black square.
     * @param col the current counter for the column.
     * @param g the drawer for the board.
     * */
    public void drawBlackStart(Graphics2D g, int col) {
        int counter = 0;
        for (int i = 0; i < BOARD_SIDE; i += SQUARE_SIDE, counter += 1) {
            if (counter % 2 == 0) {
                g.setColor(DARK_SQUARE_COLOR);
                g.fillRect(col, i, BOARD_SIDE, BOARD_SIDE);
            } else {
                g.setColor(LIGHT_SQUARE_COLOR);
                g.fillRect(col, i, BOARD_SIDE, BOARD_SIDE);
            }
        }
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        drawGrid(g);
        for (int i = 0; i < _board.getPieces().size(); i++) {
            if (_board.getPieces().get(i) == SPEAR) {
                drawSpear(g, Square.sq(i));
            } else if (_board.getPieces().get(i) == BLACK) {
                drawQueen(g, Square.sq(i), BLACK);
            } else if (_board.getPieces().get(i) == EMPTY) {
                continue;
            } else {
                drawQueen(g, Square.sq(i), WHITE);
            }
        } if (_showingFromLegal) {
            Iterator<Square> reachableFrom = _board
                    .reachableFrom(_from, null);
            ArrayList<Square> toMark = new ArrayList<>();
            while (reachableFrom.hasNext()) {
                toMark.add(reachableFrom.next());
            }
            for (int i = 0; i < toMark.size(); i++) {
                Square square = toMark.get(i);
                drawCoin(_drawer, square);
            }
        } else if (_showingToLegal) {
            Iterator<Square> reachableFrom = _board
                    .reachableFrom(_to, null);
            ArrayList<Square> toMark = new ArrayList<>();
            while (reachableFrom.hasNext()) {
                toMark.add(reachableFrom.next());
            }
            for (int i = 0; i < toMark.size(); i++) {
                Square square = toMark.get(i);
                drawCoin(_drawer, square);
            }
        }
        Piece winner = _board.winner();
        if (winner == null) {
            drawStar(_drawer, winner);
        }
    }

    /** Draw a star and the winner to denote the end of the game.
     * @param g g
     * @param winner winner*/
    private void drawStar(Graphics2D g, Piece winner) {
        if (winner == WHITE) {
            g.setColor(RED);
            g.setFont(new Font("default", Font.BOLD, WINNER_FONT));
            g.setColor(LIGHT_SQUARE_COLOR);
            g.fillRect(ZERO, ZERO, BOARD_SIDE, BOARD_SIDE);
            g.setColor(RED);
            g.drawString("Black wins!", STRING_WINNER_X, STRING_WINNER_Y);
            g.drawImage(_winnerImage, WINNER_X, WINNER_Y, null);
        } else if (winner == BLACK) {
            g.setColor(RED);
            g.setFont(new Font("default", Font.BOLD, WINNER_FONT));
            g.setColor(LIGHT_SQUARE_COLOR);
            g.fillRect(ZERO, ZERO, BOARD_SIDE, BOARD_SIDE);
            g.setColor(RED);
            g.drawString("White wins!", STRING_WINNER_X, STRING_WINNER_Y);
            g.drawImage(_winnerImage, WINNER_X, WINNER_Y, null);
        }
    }

    /**
     * Draw a queen for side PIECE at square S on G.
     */
    private void drawQueen(Graphics2D g, Square s, Piece piece) {
        g.drawImage(piece == WHITE ? _whiteQueen : _blackQueen,
                cx(s.col()) + 7, cy(s.row()) + 10, null);
    }

    /**
     * Draw a queen for side PIECE at square S on G.
     */
    private void drawSpear(Graphics2D g, Square s) {
        g.drawImage(_spearImage,
                cx(s.col()) + 4, cy(s.row()) + 8, null);
    }

    /**
     * Draw a queen for side PIECE at square S on G.
     */
    private void drawCoin(Graphics2D g, Square s) {
        g.drawImage(_coinImage,
                cx(s.col()) + 10, cy(s.row()) + 8, null);
    }

    /**
     * Handle a click on S.
     */
    private void click(Square s) {
        if (_from == null) {
            Piece fromPiece = _board.getPieces().get(s.index());
            if (fromPiece == BLACK
                    || fromPiece == WHITE) {
                _from = s;
                _showingFromLegal = true;
            }
        } else if (_to == null) {
            if (_board.isLegal(_from, s)) {
                _to = s;
                _showingFromLegal = false;
                _showingToLegal = true;
            }
        } else {
            _showingToLegal = false;
            _spear = s;
            if (_board.isLegal(_from, _to, _spear)
                    && _board.getPieces().get(_to.index()) == Piece.EMPTY) {
                Piece from = _board.getPieces().get(_from.index());
                _board.getPieces().put(_to.index(), from);
                _board.getPieces().put(_from.index(), EMPTY);
                _board.getPieces().put(_spear.index(), EMPTY);
                Move m = Move.mv(_from, _to, s);
                _commands.add(m.toString());
                _from = null;
                _spear = null;
                _to = null;
            } else {
                _from = null;
                _spear = null;
                _to = null;
            }
        }
        repaint();
    }

    /**
     * Handle mouse click event E.
     */
    private synchronized void mouseClicked(String unused, MouseEvent e) {
        int xpos = e.getX(), ypos = e.getY();
        int x = xpos / SQUARE_SIDE,
                y = (BOARD_SIDE - ypos) / SQUARE_SIDE;
        if (_acceptingMoves
                && x >= 0 && x < Board.SIZE && y >= 0 && y < Board.SIZE) {
            click(sq(x, y));
        }
    }

    /**
     * Revise the displayed board according to BOARD.
     */
    synchronized void update(Board board) {
        _board.copy(board);
        repaint();
    }

    /**
     * Turn on move collection iff COLLECTING, and clear any current
     * partial selection.   When move collection is off, ignore clicks on
     * the board.
     */
    void setMoveCollection(boolean collecting) {
        _acceptingMoves = collecting;
        repaint();
    }

    /**
     * Return x-pixel coordinate of the left corners of column X
     * relative to the upper-left corner of the board.
     */
    private int cx(int x) {
        return x * SQUARE_SIDE;
    }

    /**
     * Return y-pixel coordinate of the upper corners of row Y
     * relative to the upper-left corner of the board.
     */
    private int cy(int y) {
        return (Board.SIZE - y - 1) * SQUARE_SIDE;
    }

    /**
     * Return x-pixel coordinate of the left corner of S
     * relative to the upper-left corner of the board.
     */
    private int cx(Square s) {
        return cx(s.col());
    }

    /**
     * Return y-pixel coordinate of the upper corner of S
     * relative to the upper-left corner of the board.
     */
    private int cy(Square s) {
        return cy(s.row());
    }

    /**
     * Queue on which to post move commands (from mouse clicks).
     */
    private ArrayBlockingQueue<String> _commands;
    /**
     * Board being displayed.
     */
    private final Board _board = new Board();

    /** Image of a coin. */
    private BufferedImage _coinImage;

    /**
     * Image of white queen.
     */
    private BufferedImage _whiteQueen;
    /**
     * Image of black queen.
     */
    private BufferedImage _blackQueen;
    /**
     * Image of spear.
     */
    private BufferedImage _spearImage;
    /**
     * Image of star.
     */
    private BufferedImage _winnerImage;

    /**
     * True iff accepting moves from user.
     */
    private boolean _acceptingMoves;

    /** Initializes the from part of the move. */
    private Square _from = null;

    /** Initializes the to part of the move. */
    private Square _to = null;

    /** Initializes the spear part of the move. */
    private Square _spear = null;

    /** My drawer. */
    private Graphics2D _drawer;

    /** Stage one of reachableMove show. */
    private boolean _showingFromLegal = false;

    /** Stage two of reachableMove show. */
    private boolean _showingToLegal = false;

    /** Winner X position. */
    private static final int WINNER_X = BOARD_SIDE / 2 - 25;
    /** Winner Y position. */
    private static final int WINNER_Y = BOARD_SIDE / 2 - 20;

    /** Winner X position. */
    private static final int STRING_WINNER_X = BOARD_SIDE / 2 - 130;
    /** Winner Y position. */
    private static final int STRING_WINNER_Y = (BOARD_SIDE / 2) - 60;

    /** Font size of winner text. */
    private static final int WINNER_FONT = 50;

    /** X position of turn: text. */
    private static final int TURN_X = BOARD_SIDE - SQUARE_SIDE - 30;

    /** Y position of turn: text. */
    private static final int TURN_Y = BOARD_SIDE - SQUARE_SIDE + 30;

    /** Size of turn font. */
    private static final int TURN_FONT = 14;

    /** The number zero. */
    private static final int ZERO = 0;

}
