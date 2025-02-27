package kyra.me.knights;

public class Move {
    private Knight movingPiece;
    private final Tile startingSquare;
    private final Tile endingSquare;

    //copy constructor
    public Move(Move move){
        this.startingSquare = Main.gameManager.board.getTile(move.startingSquare.getXPosition(), move.startingSquare.getYPosition());
        this.endingSquare = Main.gameManager.board.getTile(move.endingSquare.getXPosition(), move.endingSquare.getYPosition());
        this.movingPiece = this.startingSquare.getOccupyingPiece();

        if (startingSquare.getXPosition() == 2 && startingSquare.getYPosition() == 3 && endingSquare.getXPosition() == 3 && endingSquare.getYPosition() == 2){
            System.out.print("");
        }
    }
    public Move(Tile start, Tile end){
        if (start.getOccupyingPiece() == null || end.getOccupyingPiece() != null){
            throw new IllegalStateException("There is no moving piece");
        }
        if (start.getXPosition() == 2 && start.getYPosition() == 3 && end.getXPosition() == 3 && end.getYPosition() == 2){
            System.out.print("");
        }
        this.movingPiece = start.getOccupyingPiece();
        this.startingSquare = start;
        this.endingSquare = end;
    }

    public void doMove(){
        movingPiece = startingSquare.getOccupyingPiece();
        movingPiece.getStackPane().getChildren().remove(movingPiece);
        Main.gameManager.playAudio();

        startingSquare.setOccupyingPiece(null);
        movingPiece.setOccupiedTile(endingSquare);
        endingSquare.setOccupyingPiece(movingPiece);

        endingSquare.getStackPane().getChildren().add(movingPiece);
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


    public boolean isUselessMove(){
        //The move moves the piece to its starting square so it is useless
        //If the piece is white (true) and it is moving down (true) it is moving backwards
        //If the piece is black (false) and it is moving up (false) it is moving backwards
        int startPos = movingPiece.isWhite()? 1 : 4;
        if (startPos == endingSquare.getYPosition()) return true;
        return false;
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
