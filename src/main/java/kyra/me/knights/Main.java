package kyra.me.knights;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static GameManager gameManager = new GameManager();
    public static Color tileMainColor =Color.WHITE;
    public static Color tileOffsetColor = Color.SADDLEBROWN;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage = new Stage();

        //Create the GridPane for the chessboard
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        //Create a StackPane to hold the grid
        VBox centerPane = new VBox(gridPane);
        centerPane.setAlignment(Pos.CENTER_RIGHT);
        centerPane.setStyle("-fx-background-color: cyan");

        //Set padding to scale with the smaller side length of the window
        NumberBinding binding = Bindings.min(primaryStage.widthProperty(), primaryStage.heightProperty());

        //Instantiate the tiles
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 4; j++) {
                StackPane stackPane = new StackPane();
                new Tile(i, j, stackPane);
                stackPane.prefWidthProperty().bind(binding.divide(8));
                stackPane.prefHeightProperty().bind(binding.divide(8));

                gridPane.add(stackPane, i, 4-j); //GridPane starts from {0,0} and the top left
            }
        }

        Button moveButton = new Button("Play found moves");
        moveButton.prefHeightProperty().bind(binding.divide(4));
        moveButton.setStyle("-fx-background-color: Green");
        moveButton.setOnAction(e -> {
            if (!gameManager.answerMoves.isEmpty()){
                String move = gameManager.answerMoves.getFirst();
                Tile start = gameManager.board.getTile(move.charAt(0)-'0', move.charAt(1)-'0');
                Tile end = gameManager.board.getTile(move.charAt(2)-'0', move.charAt(3)-'0');
                new Move(start, end).doMove();
                gameManager.answerMoves.removeFirst();
            }
        });

        centerPane.getChildren().add(moveButton);
        primaryStage.setScene(new Scene(centerPane, 600, 400));

        //Start the game
        gameManager.gameStart();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}