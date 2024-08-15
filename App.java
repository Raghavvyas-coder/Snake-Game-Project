22
import javax.swing.*; 

public class App {
    public static void main(String[] args) throws Exception {

        int boardWidth = 700 ;
        int boardHeight =  boardWidth ;

        JFrame frame = new JFrame("SNAKE GAME");
        frame.setVisible(true);
        frame.setSize( boardWidth , boardHeight );
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}
