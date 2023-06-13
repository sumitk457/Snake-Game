import javax.swing.*;

public class SnakeGame extends JFrame {
    Board board;
    SnakeGame(){
        board = new Board();
        add(board);
        //Adjust the frame to the board size
        pack();
        setResizable(false);
        setTitle("Snake Game");
        setVisible(true);
    }
    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
    }
}