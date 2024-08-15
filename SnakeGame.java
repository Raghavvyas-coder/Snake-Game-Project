
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class SnakeGame extends JPanel implements ActionListener , KeyListener  {


    private class Tile {

        int x;
        int y;

        // Constructor.
        Tile( int x , int y ){
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;


    //Snake Head
    Tile snakeHead;
    ArrayList<Tile> snakeBody; 
    

    //Snake Food
    Tile food;

    //Random  for placing food at diffrent place.
    Random random;

    //Game Logic
    Timer gameLoop;
    int velocityx;
    int velocityy;
    boolean gmaeOver = false;


    // Constructor.
    SnakeGame(int boardWidth , int boardHeight){


        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth , this.boardHeight));
        setBackground(Color.DARK_GRAY);

        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(4,4);
        snakeBody = new ArrayList<Tile>();

           food = new Tile(10,10);
           random = new Random();
           placefood();

           velocityx = 0;
           velocityx = 0;

           gameLoop = new Timer(100,this);
           gameLoop.start();

    }
    
    public void paintComponent(Graphics g){

     super.paintComponent(g);
     draw(g);

    }
public void draw(Graphics g ){
    //Grid
    for(int i = 0;i <boardWidth/tileSize;i++){
       g.drawLine(i*tileSize,0,i*tileSize,boardHeight);
       g.drawLine(0,i*tileSize,boardWidth,i*tileSize);

    }

   
     //Food
     g.setColor(Color.yellow);
     g.fillRect( food.x*tileSize, food.y*tileSize, tileSize, tileSize );


    //sanke body.
    for(int i = snakeBody.size()-1; i>=0;i--){
        Tile snakePart = snakeBody.get(i);
        if(i == 0){
            snakePart.x = snakeHead.x;
            snakePart.y = snakeHead.y;
        }
        else{
            Tile prevSnakePart =snakeBody.get(i-1);
            snakePart.x = prevSnakePart.x;
            snakePart.y = prevSnakePart.y;
        }
    }

 
    //SNAKE HEAD
    g.setColor(Color.red);
    g.fillRect( snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize );

    // SNAKE BODY
    for(int i = 0;i< snakeBody.size();i++){
        Tile snakePart = snakeBody.get(i);
        g.fillRect(snakePart.x*tileSize,snakePart.y*tileSize , tileSize, tileSize);
    }


    // make score board.
    g.setFont(new Font("Arial" , Font.PLAIN,16));
    if(gmaeOver){
        g.setColor(Color.GREEN);
        g.drawString("Game Over: "+String.valueOf(snakeBody.size()), tileSize -16, tileSize );
    }
    else{
        g.drawString("Score: "+String.valueOf(snakeBody.size()),tileSize - 16, tileSize);
    }


}
// Food place at diffrent place.
public void placefood(){
    food.x = random.nextInt(boardWidth/tileSize);
    food.y = random.nextInt(boardHeight/tileSize);
}



//Main method to run the game.
public static void main(String[] args) {
    JFrame frame = new JFrame("Snake Game");
    SnakeGame gamePanel = new SnakeGame(700, 700); // 500x500 board size
    frame.add(gamePanel);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}


/// Collision in btw snake head and food.
public boolean collision(Tile tilel, Tile tile2){
    return tilel.x == tile2.x && tilel.y == tile2.y; 
}


public void move(){

//Eating food.
if(collision(snakeHead, food)){
    snakeBody.add(new Tile(food.x , food.y));
    placefood();
}

    // snake head velicity.
    snakeHead.x += velocityx;
    snakeHead.y += velocityy; 
    
    //Gmae over condition.
    for (int i=0;i<snakeBody.size();i++){
        Tile snakePart = snakeBody.get(i);
        //Collision with snake head.
        if(collision(snakeHead, snakePart)){
            gmaeOver = true;
        }
    }

    //Game over condition.
    if(snakeHead.x*tileSize <0 || snakeHead.x*tileSize>boardWidth || 
    snakeHead.y*tileSize<0 || snakeHead.y*tileSize> boardHeight){

        gmaeOver = true;
    }


}


@Override
public void actionPerformed(ActionEvent e) {

    move();
    repaint();
    if(gmaeOver){
    gameLoop.stop();
    }
}





@Override
public void keyPressed(KeyEvent e) {

    if(e.getKeyCode() == KeyEvent.VK_UP && velocityy !=1){

        velocityx = 0;
        velocityy = -1;
    }
    else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityy != - 1){
        
        velocityx = 0;
        velocityy = 1;

    }
    else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityy !=1){

        velocityx = -1;
        velocityy = 0;
    }
    else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityy != - 1){
        velocityx = 1;
        velocityy = 0;
    }
}

// Do not need
@Override
public void keyTyped(KeyEvent e) {}

// Do not need
@Override
public void keyReleased(KeyEvent e) {}



}
