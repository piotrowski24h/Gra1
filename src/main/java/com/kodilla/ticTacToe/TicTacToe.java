package com.kodilla.ticTacToe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Random;

import static com.kodilla.ticTacToe.Styles.*;

    public class TicTacToe extends Application {

        public static final String X = "X";
        public static final String O = "O";
        private final Image imageback = new Image("file:src/main/resources/Plansza.png");   //plansza
        private final GridPane grid = new GridPane();
        private final String[][] valueOfButtons = new String[3][3];
        private final Button[][] buttons = new Button[3][3];
        private final HashSet<String> checkedPlaces = new HashSet<>();   //indexySprawdzonychMiejsc
        private final Random generator = new Random();
        private int randomRow = 0;
        private int randomColumn = 0;
        private boolean isTurnX = true;
        private boolean endGame = false;
        private String wybierzXorO = "";

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {

            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
            Background background = new Background(backgroundImage);
            grid.setBackground(background);
            grid.setPrefSize(450, 450);

            startApp();

            Scene scene = new Scene(grid, 450, 500);

            primaryStage.setTitle("Tic-Tac-Toe");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public void startApp() {
            grid.setAlignment(Pos.BOTTOM_CENTER);

            Button start = new Button("START");

            Label label = new Label("\n\n\n\n\n\n  3....2....1..X!");

            label.setFont(Font.font(22));
            label.setStyle( FILL_FIREBRICK + FONT_WEIGHT_BOLD);

            start.setFont(Font.font(50));
            start.setOnMouseClicked(event -> {
                grid.getChildren().clear();

                wybierzXorO();
            });

            grid.addRow(1,start);
            grid.addRow(2, label);
        }

        public void wybierzXorO() {
            grid.setAlignment(Pos.CENTER);

            Button X = new Button(com.kodilla.ticTacToe.TicTacToe.X);
            Button O = new Button(com.kodilla.ticTacToe.TicTacToe.O);

            X.setFont(Font.font(50));
            O.setFont(Font.font(50));

            X.setOnMouseClicked(e1 -> {
                wybierzXorO = com.kodilla.ticTacToe.TicTacToe.X;

                grid.getChildren().clear();
                grid.setAlignment(Pos.TOP_CENTER);

                rozrywka();
                restartApp();
                quitApp();

                System.out.println("Good luck!");
            });
            O.setOnMouseClicked(e2 -> {
                wybierzXorO = com.kodilla.ticTacToe.TicTacToe.O;

                grid.getChildren().clear();
                grid.setAlignment(Pos.TOP_CENTER);

                rozrywka();
                restartApp();
                quitApp();

                System.out.println("Good luck!");
            });

            grid.addColumn(0, X);
            grid.addColumn(1, O);
        }

        public void rozrywka() {

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Button button = new Button();
                    button.setText("");
                    button.setMinSize(150, 150);
                    button.setStyle(BACKGROUND_TRANSPARENT + PADDING_0 + BACKGROUND_SIZE_0);

                    buttons[i][j] = button;

                    buttons[i][j].setOnMouseClicked(event -> {
                        System.out.println(endGame);
                        if (!endGame) {
                            button.setText(wybierzXorO);
                            button.setFont(Font.font(70));
                            button.setDisable(true);
                            button.setStyle(BACKGROUND_TRANSPARENT + PADDING_0 + BACKGROUND_SIZE_0 + OPACITY_1_0);

                            for (int i1 = 0; i1 < 3; i1++) {
                                for (int j1 = 0; j1 < 3; j1++) {
                                    if (buttons[i1][j1].getText().equals(wybierzXorO)) {
                                        valueOfButtons[j1][i1] = wybierzXorO;
                                    }
                                }
                            }

                            if (wybierzXorO.equals(X)) {
                                isTurnX = false;
                            } else if (wybierzXorO.equals(O)) {
                                isTurnX = true;
                            }

                            show();
                            sprawdzWygraj();
                            computerSmart();
                        }
                    });

                    grid.add(buttons[i][j], i, j);
                }
            }
            if (wybierzXorO.equals(O) && isTurnX) {

                randomRow = generator.nextInt(3);
                randomColumn = generator.nextInt(3);

                buttons[randomRow][randomColumn].setText(X);
                buttons[randomRow][randomColumn].setFont(Font.font(70));
                buttons[randomRow][randomColumn].setDisable(true);
                buttons[randomRow][randomColumn].setStyle(BACKGROUND_TRANSPARENT + PADDING_0 + BACKGROUND_SIZE_0 + OPACITY_1_0);

                valueOfButtons[randomColumn][randomRow] = X;

                show();

                isTurnX = false;
            }
        }

        private void computerSmart() {

            if (!endGame) {
                randomRow = generator.nextInt(3);
                randomColumn = generator.nextInt(3);

                if (wybierzXorO.equals(X)) {
                    while (checkedPlaces.size() != 9 && !isTurnX) {
                        if (buttons[randomRow][randomColumn].getText().equals("")) {
                            buttons[randomRow][randomColumn].setText(O);
                            buttons[randomRow][randomColumn].setFont(Font.font(70));
                            buttons[randomRow][randomColumn].setDisable(true);
                            buttons[randomRow][randomColumn].setStyle(BACKGROUND_TRANSPARENT + PADDING_0 + BACKGROUND_SIZE_0 + OPACITY_1_0);

                            valueOfButtons[randomColumn][randomRow] = O;
                            show();
                            sprawdzWygraj();

                            checkedPlaces.clear(); //checkedPlaces to HashSet<String>

                            isTurnX = true;
                        } else {
                            checkedPlaces.add(Integer.toString(randomRow) + randomColumn);
                            randomRow = generator.nextInt(3);
                            randomColumn = generator.nextInt(3);
                        }
                    }
                } else if (wybierzXorO.equals(O)) {
                    while (checkedPlaces.size() != 9 && isTurnX) {
                        if (buttons[randomRow][randomColumn].getText().equals("")) {
                            buttons[randomRow][randomColumn].setText(X);
                            buttons[randomRow][randomColumn].setFont(Font.font(70));
                            buttons[randomRow][randomColumn].setDisable(true);
                            buttons[randomRow][randomColumn].setStyle(BACKGROUND_TRANSPARENT + PADDING_0 + BACKGROUND_SIZE_0 + OPACITY_1_0);

                            valueOfButtons[randomColumn][randomRow] = X;
                            show();
                            sprawdzWygraj();

                            checkedPlaces.clear(); //checkedPlaces to HashSet<String>

                            isTurnX = false;
                        } else {
                            checkedPlaces.add(Integer.toString(randomRow) + randomColumn);
                            randomRow = generator.nextInt(3);
                            randomColumn = generator.nextInt(3);
                        }
                    }
                }

                if (checkedPlaces.size() == 9 && !endGame) {
                    remis();
                }
            }
        }

        public void sprawdzWygraj() {

            for (int i = 0; i < 3; i++) {
                //wygrana poziome
                if (valueOfButtons[i][0] != null && valueOfButtons[i][0].equals(valueOfButtons[i][1])
                        && valueOfButtons[i][0].equals(valueOfButtons[i][2])) {

                    winner(i, 0);
                }

                //wygrana pionowe
                else if (valueOfButtons[0][i] != null && valueOfButtons[0][i].equals(valueOfButtons[1][i])
                        && valueOfButtons[0][i].equals(valueOfButtons[2][i])) {

                    winner(0, i);
                }
            }

            //wygrana przekątna
            if (valueOfButtons[0][0] != null && valueOfButtons[0][0].equals(valueOfButtons[1][1])
                    && valueOfButtons[0][0].equals(valueOfButtons[2][2])) {

                winner(0, 0);

            } else if (valueOfButtons[0][2] != null && valueOfButtons[0][2].equals(valueOfButtons[1][1])
                    && valueOfButtons[0][2].equals(valueOfButtons[2][0])) {

                winner(0, 2);
            }
        }

        private void winner(int i, int j) {

            System.out.println("You Win!: " + valueOfButtons[i][j] + " player.");

            Label winner = new Label("You Win: " + valueOfButtons[i][j]);

            endGame = true;

            winner.setFont(Font.font(22));
            winner.setStyle("-fx-text-fill: FIREBRICK; -fx-font-weight: bold" );

            grid.add(winner, 1,1);
        }

        private void remis() {
            System.out.println("REMIS!");

            Label remis = new Label("REMIS!");

            remis.setFont(Font.font(40));
            remis.setStyle("-fx-text-fill: FIREBRICK; -fx-font-weight: bold" );

            grid.add(remis, 1,1);
        }

        public void show() {

            System.out.println("----New move----");
            for (int i = 0; i < 3 ; i++) {
                for (int j = 0; j < 3 ; j++) {
                    if (valueOfButtons[i][j] == null) {
                        System.out.print("-\t");
                    } else {
                        System.out.print(valueOfButtons[i][j] + "\t");
                    }
                }
                System.out.println();
            }
        }

        public void restartApp() {
            Button newGame = new Button("NEW GAME");

            newGame.setMinSize(150,50);
            newGame.setOnMouseClicked(event -> {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j ++) {
                        valueOfButtons[i][j] = null;
                    }
                }

                isTurnX = true;
                endGame = false;

                checkedPlaces.clear();
                grid.getChildren().clear();

                wybierzXorO();

                System.out.println("Next New game!");
            });

            grid.add(newGame,0,3);
        }

        private void quitApp() {
            Button quit = new Button("QUIT");

            quit.setMinSize(120, 40);
            quit.setOnMouseClicked(event -> {
                Platform.exit();

                System.out.println("Goodbye!");
            });

            grid.add(quit,2,3);
        }


}
