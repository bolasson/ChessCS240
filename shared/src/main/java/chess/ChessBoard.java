package chess;

import java.util.Arrays;
import java.util.Collection;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] board;

    public ChessBoard() {
        this.board = new ChessPiece[8][8];
    }

    public ChessBoard deepClone() {
        ChessBoard clone = new ChessBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null) {
                    ChessPiece clonedPiece = new ChessPiece(piece.getTeamColor(), piece.getPieceType());
                    clone.board[i][j] = clonedPiece;
                }
            }
        }
        return clone;
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow();
        int col = position.getColumn();
        board[row-1][col-1] = piece;
    }

    public void removePiece(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        board[row-1][col-1] = null;
    }

    public void makeMove(ChessMove move) {
        ChessPiece piece = getPiece(move.getStartPosition());
        addPiece(move.getEndPosition(), piece);
        removePiece(move.getStartPosition());
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard that)) return false;

        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        String boardASCII = ""; //"------------------------\n";
        for (int i = 7; i > -1; i--) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece == null) {
                    boardASCII += ("| |");
                } else {
                    boardASCII += (piece.toString());
                }
            }
            boardASCII += ("\n");//------------------------\n");
        }
        return boardASCII;
    }

    public void printValidMoves(Collection<ChessMove> moves) {
        ChessBoard board = this.deepClone();
        for (ChessMove move : moves) {
            board.addPiece(move.getEndPosition(), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.XAMPLE));
        }
        System.out.println(board.toString());
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()-1][position.getColumn()-1] == null ? null : board[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        this.board = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            board[1][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            board[6][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }
        board[0][0] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        board[0][7] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        board[7][0] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        board[7][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        board[0][1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        board[0][6] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        board[7][1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        board[7][6] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        board[0][2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board[0][5] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board[7][2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        board[7][5] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        board[0][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        board[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        board[0][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        board[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
    }

    public void clearBoard() {
        this.board = new ChessPiece[8][8];
    }

    public ChessBoard simulateMove(ChessMove move) throws InvalidMoveException {
        ChessBoard simulatedBoard = this.deepClone();
        ChessPiece piece = simulatedBoard.getPiece(move.getStartPosition());
        if (piece == null) {
            throw new InvalidMoveException("No piece at the start position");
        }
        simulatedBoard.removePiece(move.getStartPosition());
        simulatedBoard.removePiece(move.getEndPosition());
        simulatedBoard.addPiece(move.getEndPosition(), piece);
        return simulatedBoard;
    }
}
