package kyra.me.knights;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.*;

public class GameManager {
    public Clip moveSound;
    public Board board = new Board();
    public List<Knight> pieces = new ArrayList<>();
    public List<String> answerMoves = new ArrayList<>();

    public void gameStart(){
        Tile t;
        for (int i = 1; i <= 3; i++){
            t = board.getTile(i,1);
            new Knight(t, true, t.getStackPane());
            t = board.getTile(i,4);
            new Knight(t, false, t.getStackPane());
        }
        board.setPieces(pieces);

        try {
            BufferedInputStream moveStream = new BufferedInputStream(
                    GameManager.class.getResourceAsStream("/kyra/me/knights/move.wav")
            );
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(moveStream);
            Clip moveClip = AudioSystem.getClip();
            moveClip.open(audioInputStream);
            moveSound = moveClip;
        }
        catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {System.out.println(ex);}

        //To calculate the time it took for the algorithm to run
        long l = System.currentTimeMillis();
        String answer = "www......bbb";
        answerMoves = KnightSwapApp.adversarialSearchAlgorithm(board.toString(), answer);
        if (answerMoves.isEmpty()){
            System.out.println("No solution found");
        } else {
            System.out.println("Solution found");
            System.out.println("in " + answerMoves.size() + " moves");
        }
        System.out.println("Time taken: " + (System.currentTimeMillis() - l) + "ms");
    }

    public void playAudio(){
        moveSound.stop();
        moveSound.setFramePosition(0);
        moveSound.start();
    }

    public static void moveCreation(List<Move> moves, Board board){
        for (Knight piece: board.getPieces()){
            piece.createMoves(moves, board);
        }
    }
}
