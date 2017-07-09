import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Gameplay extends JPanel implements KeyListener,ActionListener{

    private boolean play = false;
    private MapGenerator MGobj;

    private Timer timer;
    // create all objects
    private int player = 310, delay = 8, totalBricks = 21, ballPositionX = 120,
                score = 0, ballPositionY = 350, ballDirX = -1, ballDirY = -2;

    public Gameplay(){
        MGobj = new MapGenerator( 3, 7 );
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        //create a timer
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g){
        //background
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);

        //call to the MapGen draw method
        MGobj.draw( ( Graphics2D ) g);

        //borders
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //adding the score
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif",Font.BOLD, 25));
        g.drawString("Score: "+score, 570, 30);

        //create paddle
        g.setColor(Color.GREEN);
        g.fillRect(player,550,100,8);

        //create the ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballPositionX,ballPositionY,20,20);

        //check if game over
        if(ballPositionY > 570){
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30) );
            g.drawString("GAME OVER  Score: "+score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20) );
            g.drawString("Press enter to restart", 230, 350);
        }

        //tells the user they have won
        if(totalBricks == 0){
            g.setColor(Color.YELLOW);
            g.setFont(new Font("serif", Font.BOLD, 30) );
            g.drawString("Congratulations, you have won!", 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20) );
            g.drawString("Press enter to restart", 230, 350);
        }

        g.dispose();

    }

    public void actionPerformed(ActionEvent e) {
        timer.start();
        //makes sure the ball collides with the paddle
        if(play){
            if( new Rectangle( ballPositionX, ballPositionY, 20, 20)
                .intersects( new Rectangle( player, 550, 100, 8 ) ) ){
                ballDirY = - ballDirY;
            }
            // beginning of the code with an error in it, the rectangle around the ball
            //is intersecting incorrectly the rectangle of the brick
            A:for(int i = 0; i < MGobj.map.length; i++){
                for(int j = 0; j < MGobj.map[0].length; j++){
                    if(MGobj.map [i] [j] > 0){
                        int brickX = j * MGobj.brickWidth + 80;
                        int brickY = i * MGobj.brickHeight + 50;
                        int brickWidth = MGobj.brickWidth;
                        int brickHeight = MGobj.brickHeight;

                        //create the rectangle around the brick
                        Rectangle rect = new Rectangle( brickX, brickY,
                                                        brickWidth, brickHeight );

                        //create the rectangle around the ball, so we can detect the intersection
                        Rectangle ballRect = new Rectangle( ballPositionX, ballPositionY, 20, 20 );

                        Rectangle brickRect = rect;
                        //when intersecting, delete the brick off the map
                        if( ballRect.intersects( brickRect )){
                            MGobj.setBrickValue( 0, i, j );
                            totalBricks--;
                            score += 5;
                            if( ballPositionX + 19 <= brickRect.x ||
                                ballPositionX + 1  >=  brickRect.x + brickRect.width  ){
                                ballDirX = - ballDirX;
                            }else{
                                ballDirY = - ballDirY;
                            }
                            //creates a label that says when this code executes, get out of the
                            //outer loop
                            break A;
                        }
                    }
                }
            }
            //end of the code with an error in it

            //makes sure the ball is moving
            ballPositionX += ballDirX;
            ballPositionY += ballDirY;

            //makes sure the ball isn't going beyond the borders
            if(ballPositionX < 0){
                ballDirX = -ballDirX;
            }
            if(ballPositionY < 0){
                ballDirY = -ballDirY;
            }
            //think this code doesn't work
            if(ballPositionX > 670){
                ballDirX = -ballDirX;
            }
        }

        repaint();

    }

    public void keyPressed(KeyEvent e) {
        //lets us press right key
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(player >= 600){
                player = 600;
            }else{
                moveRight();
            }
        }
        //lets us press left key
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(player < 10){
                player = 10;
            }else{
                moveLeft();
            }
        }
    //lets us restart the game by pressing enter
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play || totalBricks == 0){
                play = true;
                ballPositionX = 120;
                ballPositionY = 350;
                ballDirX = -1;
                ballDirY = -2;
                player = 310;
                score = 0;
                totalBricks = 21;
                MGobj = new MapGenerator(3,7);
                repaint();
            }else{
                moveLeft();
            }
        }
    }

    //makes the paddle move left or right as needed
    public void moveRight(){
        play = true;
        player += 20;
    }

    public void moveLeft(){
        play = true;
        player -= 20;
    }

    //methods not needed
    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}
