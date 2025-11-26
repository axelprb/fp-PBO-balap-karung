import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Racer {
    private int lastKey = -1; // Mengingat tombol terakhir
    
    public Player(String name, int startX, int startY) {
        // Kirim nama ke class Induk (Racer)
        super(name, Color.BLUE, startX, startY);
    }

    public void handleInput(int key) {
        // Logika: Harus pencet KANAN dan KIRI bergantian
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
            this.x += 16; // Maju
            this.jumpFrame = 5; // Set animasi lompat selama 5 frame
        }
    }
}