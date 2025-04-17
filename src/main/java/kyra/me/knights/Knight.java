package kyra.me.knights;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.List;

public class Knight extends ImageView {
    private Tile occupiedTile;
    private boolean isWhite;
    private static final int[][] KNIGHT_MOVES = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
    };
    static Image[] models = { new Image( Knight.class.getResourceAsStream("/kyra/me/knights/white knight.png")),
            new Image( Knight.class.getResourceAsStream("/kyra/me/knights/black knight.png")) };

    public Knight(Tile tile, boolean isWhite, StackPane pane) {
        this.occupiedTile = tile;
        this.isWhite = isWhite;

        if (this.isWhite) {
            this.setImage(models[0]);
        } else {
            this.setImage(models[1]);
        }

        pane.getChildren().add(this);
        occupiedTile.setOccupyingPiece(this);
        Main.gameManager.pieces.add(this);

        this.fitWidthProperty().bind(pane.prefWidthProperty());
        this.fitHeightProperty().bind(pane.prefHeightProperty());
    }
    public Knight(Tile tile, boolean isWhite){
        this.occupiedTile = tile;
        this.isWhite = isWhite;
        this.occupiedTile.setOccupyingPiece(this);
    }

    public void createMoves(List<Move> moves, Board board){
        //Iterates through all possible combination of moves for a knight
        Tile t;
        for (int[] move : KNIGHT_MOVES) {
            t = board.getTile(occupiedTile.getXPosition() + move[0],occupiedTile.getYPosition() + move[1]);
            this.addMove(t, moves);
        }
    }

    private void addMove(Tile endTile, List<Move> moves){
        if (endTile == null) return;
        if (endTile.getOccupyingPiece() != null) return;
        Move move = new Move(occupiedTile, endTile);
        if (move.isUselessMove()) return;
        moves.add(move);
    }

    public boolean isWhite(){
        return isWhite;
    }
    public StackPane getStackPane() { return (StackPane)getParent(); }
    public void setOccupiedTile(Tile occupiedTile) { this.occupiedTile = occupiedTile; }
    public Tile getOccupiedTile() { return occupiedTile; }
}
