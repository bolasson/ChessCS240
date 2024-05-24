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

    public ChessGame(ChessBoard board, TeamColor teamTurn) {
        this.activeBoard = board;
        this.teamTurn = teamTurn;
    }

    public ChessGame(ChessBoard board) {
        this.activeBoard = board;
        this.teamTurn = TeamColor.WHITE;
    }

    public ChessGame(TeamColor teamTurn) {
        this.activeBoard = new ChessBoard();
        this.teamTurn = teamTurn;
        this.activeBoard.resetBoard();
    }

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

    public TeamColor getOpponent(TeamColor color) { 
        return color == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
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
        setTeamTurn(piece.getTeamColor());
        if (piece == null || piece.getTeamColor() != teamTurn) return new ArrayList<>(); // Return empty collection instead of null
        Collection<ChessMove> validMoves = new ArrayList<>();
        Collection<ChessMove> possibleMoves = piece.pieceMoves(activeBoard, startPosition);
        ChessBoard originalBoard = getBoard().deepClone();
        for (ChessMove move : possibleMoves) {
            setBoard(originalBoard);
            try {
                ChessBoard simulatedBoard = activeBoard.simulateMove(move);
                setBoard(simulatedBoard);
                if (!isInCheck(piece.getTeamColor())) {
                    validMoves.add(move);
                }
            } catch (Exception e) {
                System.out.println("This is not a valid move.");
            }
        }
        setBoard(originalBoard);
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = activeBoard.getPiece(move.getStartPosition());
        if (piece == null || piece.getTeamColor() == getOpponent(teamTurn)) {
            throw new InvalidMoveException("Illegal move: piece does not exist or out of turn");
        }
        Collection<ChessMove> legalMoves = validMoves(move.getStartPosition());
        if (!legalMoves.contains(move)) {
            throw new InvalidMoveException("Illegal move");
        }
        activeBoard.makeMove(move);
        if(move.getPromotionPiece() != null) {
            ChessPiece prometedPiece = activeBoard.getPiece(move.getEndPosition());
            prometedPiece.promotePawn(move);
        }
        teamTurn = getOpponent(teamTurn);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingsPosition = locateKingPosition(teamColor);
        return isUnderAttack(kingsPosition, getOpponent(teamColor));
    }

    private ChessPosition locateKingPosition(TeamColor teamColor) {
        for (ChessPosition position : getAllPositions()) {
            ChessPiece piece = activeBoard.getPiece(position);
            if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                return position;
            }
        }
        return null;
    }

    public boolean isUnderAttack(ChessPosition myPosition, TeamColor opponentColor) {
        for (ChessPosition position : getAllPositions()) {
            ChessPiece piece = activeBoard.getPiece(position);
            if (piece != null && piece.getTeamColor() == opponentColor) {
                Collection<ChessMove> potentialMoves = piece.pieceMoves(activeBoard, position);
                if (potentialMoves != null) {
                    for (ChessMove move : potentialMoves) {
                        if (move.getEndPosition().equals(myPosition)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private Collection<ChessMove> getAllLegalMoves(TeamColor teamColor) {
        Collection<ChessMove> allLegalMoves = new ArrayList<>();
        for (ChessPosition position : getAllPositions()) {
            ChessPiece piece = activeBoard.getPiece(position);
            if (piece != null && piece.getTeamColor() == teamColor) {
                Collection<ChessMove> moves = validMoves(position);
                if (moves != null) {
                    allLegalMoves.addAll(moves);
                }
            }
        }
        return allLegalMoves;
    }

    public Collection<ChessPosition> getAllPositions() {
        Collection<ChessPosition> positions = new ArrayList<>();
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                positions.add(new ChessPosition(row, col));
            }
        }
        return positions;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) return false;
        Collection<ChessMove> allLegalMoves = getAllLegalMoves(teamColor);
        return allLegalMoves.isEmpty();
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)) return false;
        Collection<ChessMove> allLegalMoves = getAllLegalMoves(teamColor);
        return allLegalMoves.isEmpty();
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

    // This is for testing purposes.
    public ChessBoard loadBoard(String boardString) {
        ChessBoard board = new ChessBoard();
        String[] rows = boardString.split("\n");
        for (int row = 0; row < 8; row++) {
            String[] pieces = rows[7 - row].trim().split("\\|");
            for (int col = 0; col < 8; col++) {
                String piece = pieces[col + 1].trim();
                if (!piece.isEmpty()) {
                    char pieceType = piece.charAt(0);
                    TeamColor teamColor = Character.isUpperCase(pieceType) ? TeamColor.WHITE : TeamColor.BLACK;
                    pieceType = Character.toLowerCase(pieceType);
                    ChessPiece.PieceType chessPieceType = null;
                    switch (pieceType) {
                        case 'p':
                            chessPieceType = ChessPiece.PieceType.PAWN;
                            break;
                        case 'r':
                            chessPieceType = ChessPiece.PieceType.ROOK;
                            break;
                        case 'n', 'h':
                            chessPieceType = ChessPiece.PieceType.KNIGHT;
                            break;
                        case 'b':
                            chessPieceType = ChessPiece.PieceType.BISHOP;
                            break;
                        case 'q':
                            chessPieceType = ChessPiece.PieceType.QUEEN;
                            break;
                        case 'k':
                            chessPieceType = ChessPiece.PieceType.KING;
                            break;
                    }
                    ChessPiece chessPiece = new ChessPiece(teamColor, chessPieceType);
                    board.addPiece(new ChessPosition(row + 1, col + 1), chessPiece);
                }
            }
        }
        return board;
    }
}
