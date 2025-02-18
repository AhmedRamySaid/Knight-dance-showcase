package kyra.me.knights;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    public int count;
    public Clip moveSound;

    public Tile[][] tiles = new Tile[3][4];
    public List<Knight> pieces = new ArrayList<>();
    public List<Move> moves = new ArrayList<>();
    public List<Move> answerMoves = new ArrayList<>();
    public boolean answerFound = false;

    public void gameStart(){
        new Knight(tiles[0][0], true);
        new Knight(tiles[1][0], true);
        new Knight(tiles[2][0], true);

        new Knight(tiles[0][3], false);
        new Knight(tiles[1][3], false);
        new Knight(tiles[2][3], false);

        try {
            BufferedInputStream moveStream = new BufferedInputStream(
                    GameManager.class.getResourceAsStream("")
            );
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(moveStream);
            Clip moveClip = AudioSystem.getClip();
            moveClip.open(audioInputStream);
            moveSound = moveClip;
        }
        catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {System.out.println(ex);}

        turnStart();
        if (findSolution(1)){
            System.out.println("Solution found");
            System.out.println("in " + answerMoves.size() + " moves");
        } else {
            System.out.println("No solution found");
        }
        System.out.println(count);
    }

    public static void playAudio(){
        moveSound.stop();
        moveSound.setFramePosition(0);
        moveSound.start();

    }

    public void turnStart(){
        moveCreation(moves);
    }

    public void moveCreation(List<Move> moves){
        for (Knight piece: pieces){
            piece.createMoves(moves);
        }
    }

    //brute force finding the solution (not currently possible due to the high complexity)
    public boolean findSolution(int depth){
        if (depth > 10) { count++; return false; }
        List<Move> tempMoves = new ArrayList<>();
        moveCreation(tempMoves);

        for (Move move: tempMoves){
            move.doMoveTemporary();

            if (isStartPos()) {
                move.undoMove();
                return false;
            }
            if (isSolution()) {
                move.undoMove();
                return true;
            }
            if (findSolution(depth + 1)) {
                move.undoMove();
                return true;
            }

            move.undoMove();
        }
        return false;
    }


    //the algorithm looped back to the start position, cut off the branch
    private boolean isStartPos(){
        for (Knight piece: pieces){
            if (piece.getOccupiedTile().getYPosition() != 1 && piece.isWhite()) return false;
            if (piece.getOccupiedTile().getYPosition() != 4 && !piece.isWhite()) return false;
        }
        return true;
    }
    //found the solution
    private boolean isSolution(){
        for (Knight piece: pieces){
            if (piece.getOccupiedTile().getYPosition() != 1 && !piece.isWhite()) return false;
            if (piece.getOccupiedTile().getYPosition() != 4 && piece.isWhite()) return false;
        }
        return true;
    }

    public Tile getTile(int x, int y){
        if (x <= 0 || x > 3 || y <= 0 || y > 4) { return null; }
        return tiles[x-1][y-1]; //starts from 0,0
    }
    public void addTile(Tile tile) { tiles[tile.getXPosition()-1][tile.getYPosition()-1] = tile; }
}
