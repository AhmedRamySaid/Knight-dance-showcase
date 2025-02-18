package kyra.me.knights;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    private final int xPosition;
    private final int yPosition;
    private Knight occupyingPiece;

    public Tile(int x, int y, StackPane pane) {
        pane.getChildren().add(this);
        xPosition = x;
        yPosition = y;

        setOnMouseEntered(event -> {
            this.setOpacity(0.5);
        });
        setOnMouseExited(event -> {
            this.setOpacity(1);
        });

        this.setColor(Main.tileMainColor, Main.tileOffsetColor);
        this.widthProperty().bind(getStackPane().prefWidthProperty());
        this.heightProperty().bind(getStackPane().prefHeightProperty());
        Main.gameManager.addTile(this);
    }

    public void setColor(Color mainColor, Color offsetColor) {
        if (xPosition%2 == yPosition%2){
            this.setFill(offsetColor);
        }
        else{
            this.setFill(mainColor);
        }
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Tile){
            Tile other = (Tile) obj;
            return other.getXPosition() == this.getXPosition() && other.getYPosition() == this.getYPosition();
        }
        return false;
    }

    public void setOccupyingPiece(Knight piece) { occupyingPiece = piece; }
    public Knight getOccupyingPiece() { return occupyingPiece; }
    public StackPane getStackPane() { return (StackPane)getParent(); }
    public int getXPosition(){ return xPosition; }
    public int getYPosition(){ return yPosition; }
}
