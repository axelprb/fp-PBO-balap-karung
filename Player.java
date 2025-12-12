import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Racer {
    private int lastKey = -1; 
    
    public Player(String name, int startX, int startY) {
        
        super(name, Color.BLUE, startX, startY);
    }

    public void handleInput(int key) {
        
        boolean move = false;

        if (key == KeyEvent.VK_RIGHT && lastKey != KeyEvent.VK_RIGHT) {
            move = true;
            lastKey = KeyEvent.VK_RIGHT;
        } 
        else if (key == KeyEvent.VK_LEFT && lastKey != KeyEvent.VK_LEFT) {
            move = true;
            lastKey = KeyEvent.VK_LEFT;
        }

        if (move) {
            this.x += 12; 
            this.jumpFrame = 5; 
        }
    }
}