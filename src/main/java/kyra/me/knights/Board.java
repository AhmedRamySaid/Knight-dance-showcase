package kyra.me.knights;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Tile[][] tiles;
    private final List<Move> moveHistory;
    private final List<Knight> pieces;

    public Board() {
        tiles = new Tile[3][4];
        moveHistory = new ArrayList<>();
        pieces = new ArrayList<>();
    }
    public Board(String key, List<Move> moveHistory) {
        this.moveHistory = moveHistory;
        this.tiles = new Tile[3][4];
        this.pieces = new ArrayList<Knight>();

        int c = 0;
        Tile t;
        for(int y = 4; y >= 1; y--){
            for(int x = 1; x <= 3; x++){
                t = new Tile(x,y);
                this.addTile(t);

                switch(key.toCharArray()[c]){
                    case '.':
                        break;
                    case 'w':
                        Knight k = new Knight(t, true);
                        pieces.add(k);
                        break;
                    case 'b':
                        Knight K = new Knight(t, false);
                        pieces.add(K);
                        break;
                }
                c++;
            }
        }
        if (pieces.size() != 6) {
            throw new IllegalStateException("Number of pieces is incorrect");
        }
    }

    //Changes it from starting from 0,0 to starting from (1,1)
    public Tile getTile(int x, int y){
        if (x <= 0 || x > 3 || y <= 0 || y > 4) { return null; }
        return tiles[x-1][y-1];
    }
    public void addTile(Tile tile) { tiles[tile.getXPosition()-1][tile.getYPosition()-1] = tile; }
    public List<Move> getMoveHistory() { return moveHistory; }
    public void setPieces(List<Knight> pieces) { this.pieces.addAll(pieces); }
    public List<Knight> getPieces() { return pieces; }

    @Override
    public String toString(){
        StringBuilder board = new StringBuilder();
        for (int row = 3; row >= 0; row--) {
            for (int col = 0; col < 3; col++) {
                board.append(tiles[col][row].toString());
            }
        }
        return board.toString();
    }
}
