package com.example.boxdemo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;



public class BoxDemo2 extends Application {
    public final static double SQUARE_SIZE = 45;
    public double positionX;
    public double positionY;
    public double pointX;
    public double pointY;
    public Canvas canvas;
    public GraphicsContext g;
    public  ArrayList<Square> squares = new ArrayList<>();
    public int count, squarePoint;
    public boolean dragging, dragCheck;


    public void start(Stage stage) {
        canvas = new Canvas(450, 450);
        
        drawCanvasItems();
        canvas.setOnMousePressed(this::mousePressed);
        canvas.setOnMouseDragged(this::mouseDragged);
        canvas.setOnMouseReleased(this::mouseReleased);

        StackPane sPane = new StackPane(canvas);
        sPane.setStyle("-fx-border-width: 2px; -fx-border-color: CYAN");
        Pane root = new Pane(sPane);
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(evt -> {
            if(evt.getCode() == KeyCode.SHIFT)
                dragCheck = true;
        });

        scene.setOnKeyReleased(evt ->
                dragCheck = false);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Box Demo v2.0");
        stage.show();
    }

    public void mousePressed(MouseEvent evt) {
        double x = evt.getX();
        double y = evt.getY();



        if(evt.isSecondaryButtonDown() || (evt.isPrimaryButtonDown() && dragCheck)) {
            for(Square square : squares) {
                if(x >= square.getPositionX() && x <= square.getPositionX() + SQUARE_SIZE) {
                    if(y >= square.getPositionY() && y <= square.getPositionY() + SQUARE_SIZE) {
                        dragging = true;
                        squarePoint = squares.indexOf(square);
                        pointX = x - square.getPositionX();
                        pointY = y - square.getPositionY();
                    }
                }
            }

        }
        else if(evt.isPrimaryButtonDown()) {
            squares.add(new Square());
            positionX = evt.getX() - (SQUARE_SIZE/2);
            positionY = evt.getY() - (SQUARE_SIZE/2);
            squares.get(count).setPositionX(positionX);
            squares.get(count).setPositionY(positionY);
            squares.get(count).draw(g);
            count++;
        }

    }

    public void mouseDragged(MouseEvent evt) {
        if(!dragging)
            return;
        double x = evt.getX();
        double y = evt.getY();

        squares.get(squarePoint).setPositionX(x-pointX);
        squares.get(squarePoint).setPositionY(y-pointY);

        drawCanvasItems();

    }

    public void mouseReleased(MouseEvent evt) {
        dragging = false;

    }

    public void drawCanvasItems() {
        g = canvas.getGraphicsContext2D();
        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0,0,canvas.getWidth(), canvas.getHeight());

        for(Square square : squares) {
            square.draw(g);
            if(squares.get(squarePoint).getPositionX() + SQUARE_SIZE <= 0 || squares.get(squarePoint).getPositionX() >= canvas.getWidth()) {
                if(squares.get(squarePoint).getPositionY() + SQUARE_SIZE <= 0 || squares.get(squarePoint).getPositionY() >= canvas.getHeight()) {
                    squares.remove(squarePoint);
                }
            }
        }



    }

    public static class Square {
        private double positionX;

        private double positionY;
        private final int r = (int)(255*Math.random());
        private final int g = (int)(255*Math.random());
        private final int b = (int)(255*Math.random());
        private final Color color = Color.rgb(r,g,b,0.5+(0.5*Math.random()));
        void draw(GraphicsContext g) {

            g.setStroke(Color.BLACK);
            g.setFill(color);
            g.fillRect(positionX,positionY,SQUARE_SIZE,SQUARE_SIZE);
            g.strokeRect(positionX,positionY,SQUARE_SIZE,SQUARE_SIZE);

        }

        double getPositionX() {
            return positionX;
        }
        double getPositionY() {
            return positionY;
        }

        void setPositionX(double n) {
            positionX = n;
        }

        void setPositionY(double n) {
            positionY = n;
        }
    }
    public static void main(String[] args) {
        launch();
    }
}