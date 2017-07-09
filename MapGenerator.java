import java.awt.*;
import javax.swing.*;

public class MapGenerator{

    public int map [] [];
    public int brickWidth;
    public int brickHeight;
    public MapGenerator(int row, int col){
        //creates the bricks
        map = new int [row] [col];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                //brick = 1 means brick exists, brick = 0 = brick has been broken
                map [i] [j] = 1;
            }
        }
        //set how many bricks we would create
        brickWidth = 540/col;
        brickHeight = 150/row;
    }
    //draws the bricks
    public void draw(Graphics2D g){

        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map [i] [j] > 0){
                    //fills the bricks
                    g.setColor(Color.WHITE);
                    g.fillRect(j * brickWidth + 80, i* brickHeight + 50,
                               brickWidth, brickHeight);

                    //creating borders for our bricks
                    g.setStroke ( new BasicStroke( 3 ) );
                    g.setColor( Color.BLACK );
                    g.drawRect( j * brickWidth + 80, i* brickHeight + 50,
                                brickWidth, brickHeight );
                }
            }
        }
    }
    //sets the brick value to 0, meaning it's broken
    public void setBrickValue(int value, int row, int col){
        map [row] [col] = value;
    }
}
