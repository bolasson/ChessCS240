package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard activeBoard;
    private TeamColor teamTurn;

    public ChessGame() {
        this.activeBoard = new ChessBoard();
        this.teamTurn = TeamColor.WHITE;
        this.activeBoard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = activeBoard.getPiece(startPosition);
        if (piece == null || piece.getTeamColor() != teamTurn) return null;
        Collection<ChessMove> possibleMoves = piece.pieceMoves(activeBoard, startPosition);
        ChessBoard oldBoard = activeBoard.deepClone();
        Collection<ChessMove> validMoves = new ArrayList<>();
        for (ChessMove move : possibleMoves) {
            activeBoard.makeMove(move);
            if (!isInCheck(teamTurn)) validMoves.add(move);
            setBoard(oldBoard);
        }
        return validMoves;
    }

    // public static void main(String[] args){
    //     ChessGame game = new ChessGame();
    //     System.out.println(game.getBoard().toString());
    // }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        if (validMoves == null || !validMoves.contains(move)) {
            throw new InvalidMoveException("Invalid move");
        }
        activeBoard.makeMove(move);
        setTeamTurn((teamTurn == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE);
        System.out.println("OFFICIAL GAME MOVE:\n\n" + activeBoard.toString());
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingsPosition = findKingPosition(teamColor, activeBoard);
        TeamColor opponent = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        Collection<ChessMove> opponentMoves = getAllMoves(opponent, activeBoard);
        for (ChessMove move : opponentMoves) {
            if (move.getEndPosition().equals(kingsPosition)) {
                return true;
            }
        }
        return false;
    }

    private ChessPosition findKingPosition(TeamColor teamColor, ChessBoard board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPosition position = new ChessPosition(row+1, col+1);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                    return position;
                }
            }
        }
        throw new Error("There isn't a King piece on the board");
    }

    private Collection<ChessMove> getAllMoves(TeamColor teamColor, ChessBoard board) {
        Collection<ChessMove> moves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPosition position = new ChessPosition(row+1, col+1);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getTeamColor() == teamColor) {
                    moves.addAll(piece.pieceMoves(board, position));
                }
            }
        }        
        return moves;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            System.out.println("Not in check");
            return false;
        }
        Collection<ChessMove> allMoves = getAllMoves(teamColor, activeBoard);
        for (ChessMove move : allMoves) {
            ChessBoard clonedBoard = activeBoard.deepClone();
            clonedBoard.makeMove(move);
            if (!isInCheck(teamColor)) {
                System.out.println("There is a move to get out of check");
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        activeBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return activeBoard;
    }
}
