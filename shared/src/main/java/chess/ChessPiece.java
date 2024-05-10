package chess;

import chess.ChessGame.TeamColor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor color;
    private PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceType = type;
        this.color = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        switch (pieceType) {
            case KING:
                moves.addAll(addLinearMoves(board, myPosition, new int[][]{{1,0}, {0,1}, {-1,0}, {0,-1}, {1,1}, {1,-1}, {-1,1}, {-1,-1}},1));
                break;
            case QUEEN:
                moves.addAll(addLinearMoves(board, myPosition, new int[][]{{1,0}, {0,1}, {-1,0}, {0,-1}, {1,1}, {1,-1}, {-1,1}, {-1,-1}},0));
                break;
            case BISHOP:
                moves.addAll(addLinearMoves(board, myPosition, new int[][]{{1,1}, {1,-1}, {-1,1}, {-1,-1}},0));
                break;
            case KNIGHT:
                moves.addAll(addKnightMoves(board, myPosition));
                break;
            case ROOK:
                moves.addAll(addLinearMoves(board, myPosition, new int[][]{{1,0}, {0,1}, {-1,0}, {0,-1}},0));
                break;
            case PAWN:
                // Still need to make the pawn moves.
                break;
        }
        return moves;
    }

    // Helper function for knight
    private Collection<ChessMove> addKnightMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int[][] positions = {
            {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
        };
    
        for (int[] position : positions) {
            int newRow = myPosition.getRow() + position[0];
            int newCol = myPosition.getColumn() + position[1];
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessPiece pieceAtNewPosition = board.getPiece(newPosition);
                if (pieceAtNewPosition == null || pieceAtNewPosition.getTeamColor() != this.color) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }
        return moves;
    }

    // Helper function for bishop, rook, queen, and king
    private Collection<ChessMove> addLinearMoves(ChessBoard board, ChessPosition myPosition, int[][] positions, int maxSteps) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
    
        for (int[] position : positions) {
            int tempRow = position[0];
            int tempCol = position[1];
            int nextRow = row + tempRow;
            int nextCol = col + tempCol;
            int steps = 0;
    
            while (nextRow >= 0 && nextRow < 8 && nextCol >= 0 && nextCol < 8 && (steps < maxSteps || maxSteps == 0)) {
                ChessPosition nextPosition = new ChessPosition(nextRow, nextCol);
                ChessPiece pieceAtNextPosition = board.getPiece(nextPosition);
                
                if (pieceAtNextPosition == null) {
                    moves.add(new ChessMove(myPosition, nextPosition, null));
                } else {
                    if (pieceAtNextPosition.getTeamColor() != this.color) {
                        moves.add(new ChessMove(myPosition, nextPosition, null));
                    }
                    break;
                }
                nextRow += tempRow;
                nextCol += tempCol;
                steps++;
            }
        }
        return moves;
    }
    
}
