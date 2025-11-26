import java.awt.*;
import java.util.Random;

public class Bot extends Racer {
    private Random rand;
    private int difficulty; // 0 = Easy, 1 = Medium, 2 = Hard

    // Constructor sekarang menerima parameter 'difficulty'
    public Bot(int startX, int startY, int difficulty) {
        super("BOT", Color.RED, startX, startY);
        this.difficulty = difficulty;
        rand = new Random();
    }

    public void autoMove() {
        int chanceToMove = 0; // Persentase kemungkinan bergerak (0-100)
        int speed = 0;        // Seberapa jauh loncatnya

        // ATUR LOGIKA KESULITAN DI SINI
        if (difficulty == 0) { 
            // --- EASY (MUDAH) ---
            chanceToMove = 30; // Jarang gerak (cuma 15%)
            speed = 2 + rand.nextInt(3); // Langkah pendek (3-5 pixel)
        } 
        else if (difficulty == 1) { 
            // --- MEDIUM (SEDANG) ---
            chanceToMove = 50; // Standar (30%)
            speed = 3 + rand.nextInt(5); // Langkah sedang (5-9 pixel)
        } 
        else { 
            // --- HARD (SULIT) ---
            chanceToMove = 60; // Sering gerak! (60%)
            speed = 5 + rand.nextInt(7); // Langkah jauh & ngebut (8-14 pixel)
        }

        // Eksekusi Gerakan
        if (rand.nextInt(100) < chanceToMove) { 
            this.x += speed; 
            this.jumpFrame = 4; // Set animasi lompat
        }
    }
}