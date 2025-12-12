import java.awt.*;
import java.io.File;           
import java.io.IOException;    
import javax.imageio.ImageIO;

public class Racer {
    protected int x, y;
    protected String name;
    protected int jumpFrame = 0;
    
    protected Image imgBerdiri;
    protected Image imgLompat;

    public Racer(String name, Color color, int startX, int startY) {
        this.name = name;
        this.x = startX;
        this.y = startY;
        
        try {
            imgBerdiri = ImageIO.read(new File("assets/berdiri.png"));
            imgLompat = ImageIO.read(new File("assets/lompat.png"));
        } catch (IOException e) {
            System.out.println("GAMBAR TIDAK KETEMU! Cek nama file.");
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        int drawY = y; 
        
     

        Image gambarSaatIni;

        if (jumpFrame > 0) {
            gambarSaatIni = imgLompat; 
            
            if (jumpFrame > 4) {
                drawY = y - 25; 
            } else {
                drawY = y - 10; 
            }
            
            jumpFrame--; 
            
        } else {
            gambarSaatIni = imgBerdiri;
        }

        if (jumpFrame > 0) {
            g.setColor(new Color(0, 0, 0, 50));
            int lebarBayangan = (jumpFrame > 4) ? 30 : 40; 
            g.fillOval(x + 5, y + 60, lebarBayangan, 10);
        }

        if (gambarSaatIni != null) {
            g.drawImage(gambarSaatIni, x, drawY, 99, 217, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(x, drawY, 50, 70);
        }

        g.setColor(Color.BLACK);
        g.drawString(name, x + 10, drawY - 10);
    }

    public int getX() {
        return x;
    }
}