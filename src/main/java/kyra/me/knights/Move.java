package kyra.me.knights;

public class Move {
    final Knight movingPiece;
    final Tile startingSquare;
    final Tile endingSquare;

    public Move(Tile start, Tile end){
        if (start.getOccupyingPiece() == null){
            throw new IllegalArgumentException("starting piece is null");
        }

        this.movingPiece = start.getOccupyingPiece();
        this.startingSquare = start;
        this.endingSquare = end;
    }

    public void doMove(){
        movingPiece.getStackPane().getChildren().remove(movingPiece);
        Main.gameManager.playAudio();

        startingSquare.setOccupyingPiece(null);
        movingPiece.setOccupiedTile(endingSquare);
        endingSquare.setOccupyingPiece(movingPiece);

        endingSquare.getStackPane().getChildren().add(movingPiece);
        Main.gameManager.turnStart();
    }

    public void doMoveTemporary(){
        startingSquare.setOccupyingPiece(null);
        movingPiece.setOccupiedTile(endingSquare);
        endingSquare.setOccupyingPiece(movingPiece);
    }
    public void undoMove(){
        startingSquare.setOccupyingPiece(movingPiece);
        movingPiece.setOccupiedTile(startingSquare);
        endingSquare.setOccupyingPiece(null);
    }

    @Override
    public String toString(){
        return "moving piece: " + movingPiece + " starting square: " + startingSquare.getXPosition() + " " + startingSquare.getYPosition() + " ending square: " + endingSquare.getXPosition() + " " + endingSquare.getYPosition();
    }
    @Override
    public boolean equals(Object obj){
        if (obj.getClass() != this.getClass()){
            return false;
        }
        Move m = (Move)obj;
        if (!this.movingPiece.equals(m.movingPiece)) {
            return false;
        }
        if (!this.startingSquare.equals(m.startingSquare)) {
            return false;
        }
        if (!this.endingSquare.equals(m.endingSquare)) {
            return false;
        }
        return true;
    }

    public Tile getStartingSquare() { return startingSquare; }
    public Tile getEndingSquare() { return endingSquare; }
    public Knight getMovingPiece() { return movingPiece; }
}
