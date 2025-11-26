import java.awt.*;
import java.io.File;           // Untuk membaca file
import java.io.IOException;    // Untuk menangani error jika file tidak ketemu
import javax.imageio.ImageIO;  // Alat utama untuk load gambar

public class Racer {
    protected int x, y;
    protected String name;
    protected int jumpFrame = 0;
    
    // Variabel untuk menyimpan gambar
    protected Image imgBerdiri;
    protected Image imgLompat;

    public Racer(String name, Color color, int startX, int startY) {
        this.name = name;
        this.x = startX;
        this.y = startY;
        
        // --- BAGIAN LOAD GAMBAR ---
        try {
            // Membaca file dari folder project
            imgBerdiri = ImageIO.read(new File("assets/berdiri.png"));
            imgLompat = ImageIO.read(new File("assets/lompat.png"));
        } catch (IOException e) {
            System.out.println("GAMBAR TIDAK KETEMU! Cek nama file.");
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        // --- LOGIKA POSISI Y (ANIMASI) ---
        int drawY = y; 
        
        // Kita bagi fase lompat (total 8 frame)
        // Frame 8-5: Sedang Naik (Tampilkan gambar lompat di posisi atas)
        // Frame 4-1: Sedang Turun (Tampilkan gambar lompat di posisi agak bawah)
        // Frame 0  : Di Tanah (Tampilkan gambar berdiri)

        Image gambarSaatIni;

        if (jumpFrame > 0) {
            gambarSaatIni = imgLompat; // Pakai gambar lompat
            
            // Logika ketinggian
            if (jumpFrame > 4) {
                drawY = y - 25; // Posisi Puncak
            } else {
                drawY = y - 10; // Posisi Hampir Mendarat
            }
            
            // Kurangi timer animasi SETIAP kali digambar
            jumpFrame--; 
            
        } else {
            gambarSaatIni = imgBerdiri; // Pakai gambar berdiri
        }

        // --- MENGGAMBAR ---
        // Gambar bayangan dulu (biar realistis)
        if (jumpFrame > 0) {
            g.setColor(new Color(0, 0, 0, 50));
            // Bayangan mengecil saat di puncak
            int lebarBayangan = (jumpFrame > 4) ? 30 : 40; 
            g.fillOval(x + 5, y + 60, lebarBayangan, 10);
        }

        // Gambar Karakter (Pakai null agar ukuran asli)
        if (gambarSaatIni != null) {
            g.drawImage(gambarSaatIni, x, drawY, 99, 217, null);
        } else {
            // Cadangan jika gambar error
            g.setColor(Color.BLUE);
            g.fillRect(x, drawY, 50, 70);
        }

        // Nama
        g.setColor(Color.BLACK);
        g.drawString(name, x + 10, drawY - 10);
    }

    public int getX() {
        return x;
    }
}