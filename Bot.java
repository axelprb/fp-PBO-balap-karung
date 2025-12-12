import java.awt.*;
import java.util.Random;

public class Bot extends Racer {
    private Random rand;
    private int difficulty; // 0 = Easy, 1 = Medium, 2 = Hard

    public Bot(int startX, int startY, int difficulty) {
        super("BOT", Color.RED, startX, startY);
        this.difficulty = difficulty;
        rand = new Random();
    }

    public void autoMove() {
        int chanceToMove = 0; 
        int speed = 0;

        if (difficulty == 0) { 
            
            chanceToMove = 30;
            speed = 2 + rand.nextInt(3); 
        } 
        else if (difficulty == 1) { 
            
            chanceToMove = 50; 
            speed = 3 + rand.nextInt(5);
        } 
        else { 
            
            chanceToMove = 60;
            speed = 5 + rand.nextInt(7);
        }

        
        if (rand.nextInt(100) < chanceToMove) { 
            this.x += speed; 
            this.jumpFrame = 4; // Set animasi lompat
        }
    }
}