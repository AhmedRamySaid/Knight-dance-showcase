package kyra.me.knights;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.List;

public class Knight extends ImageView {
    private Tile occupiedTile;
    private boolean isWhite;
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
        int[] values = {1, 2};

        //Iterate through {1, 2}, {-1, 2}, {1, -2}, {-1, -2}
        Tile t;
        for (int i = 0; i < 4; i++) {
            t = board.getTile(occupiedTile.getXPosition() + values[0],occupiedTile.getYPosition() + values[1]);
            this.addMove(t, moves);

            t = board.getTile(occupiedTile.getXPosition() + values[1],occupiedTile.getYPosition() + values[0]);
            this.addMove(t, moves);

            //Perform the transformation
            if (i % 2 == 0) {
                values[0] = -values[0];  //Toggle the first value
            } else {
                values[1] = -values[1];  //Toggle the second value
            }
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
