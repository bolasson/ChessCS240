package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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
        XAMPLE,
        PAWN;
    }

    @Override
    public String toString() {
        String pieceTypeString = pieceType == PieceType.KNIGHT ? "Horse" : pieceType.toString();
        String firstLetter = pieceTypeString.substring(0, 1);
        if (color == ChessGame.TeamColor.WHITE) {
            return "|" + firstLetter.toUpperCase() + "|";
        } else {
            return "|" + firstLetter.toLowerCase() + "|";
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;

        return color == that.color && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(color);
        result = 31 * result + Objects.hashCode(pieceType);
        return result;
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

    public void promotePawn(ChessMove move) {
        if(isPromotionRow(move.getEndPosition())) this.pieceType = move.getPromotionPiece();
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
                moves.addAll(addPawnMoves(board, myPosition));
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
            if (newRow >= 1 && newRow < 9 && newCol >= 1 && newCol < 9) {
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
    
            while (nextRow >= 1 && nextRow < 9 && nextCol >= 1 && nextCol < 9 && (steps < maxSteps || maxSteps == 0)) {
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

    // Helper function for pawn
    private Collection<ChessMove> addPawnMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int direction = (this.color == ChessGame.TeamColor.BLACK) ? -1 : 1;
        int startRow = (this.color == ChessGame.TeamColor.BLACK) ? 7 : 2;
    
        ChessPosition oneStepForward = new ChessPosition(row + direction, col);
        if (isWithinBounds(oneStepForward) && board.getPiece(oneStepForward) == null) {
            if (isPromotionRow(oneStepForward)) {
                moves.add(new ChessMove(myPosition, oneStepForward, PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, oneStepForward, PieceType.ROOK));
                moves.add(new ChessMove(myPosition, oneStepForward, PieceType.KNIGHT));
                moves.add(new ChessMove(myPosition, oneStepForward, PieceType.BISHOP));
            } else {
                moves.add(new ChessMove(myPosition, oneStepForward, null));
            }
        }
    
        if (row == startRow) {
            ChessPosition twoStepsForward = new ChessPosition(row + 2 * direction, col);
            if (isWithinBounds(twoStepsForward) && board.getPiece(oneStepForward) == null && board.getPiece(twoStepsForward) == null) {
                moves.add(new ChessMove(myPosition, twoStepsForward, null));
            }
        }
    
        int[] captureCols = {col - 1, col + 1};
        for (int captureCol : captureCols) {
            ChessPosition capturePosition = new ChessPosition(row + direction, captureCol);
            if (isWithinBounds(capturePosition)) {
                ChessPiece targetPiece = board.getPiece(capturePosition);
                if (targetPiece != null && targetPiece.getTeamColor() != this.color) {
                    if (isPromotionRow(capturePosition)) {
                        moves.add(new ChessMove(myPosition, capturePosition, PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, capturePosition, PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, capturePosition, PieceType.KNIGHT));
                        moves.add(new ChessMove(myPosition, capturePosition, PieceType.BISHOP));
                    } else {
                        moves.add(new ChessMove(myPosition, capturePosition, null));
                    }
                }
            }
        }
        return moves;
    }
    
    // Is the desired position on the board
    private boolean isWithinBounds(ChessPosition position) {
        return position.getRow() >= 1 && position.getRow() < 9 && position.getColumn() >= 1 && position.getColumn() < 9;
    }
    
    // Is the pawn on the edge of the board
    private boolean isPromotionRow(ChessPosition position) {
        return (this.color == ChessGame.TeamColor.WHITE && position.getRow() == 8) ||
               (this.color == ChessGame.TeamColor.BLACK && position.getRow() == 1);
    }
    
    public String movesToString(Collection<ChessMove> moves) {
        StringBuilder sb = new StringBuilder();
        for (ChessMove move : moves) {
            sb.append(move.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
