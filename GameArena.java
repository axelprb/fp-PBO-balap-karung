import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameArena extends JPanel implements ActionListener, KeyListener {
    private Player player;
    private Bot[] bots; 
    
    private Timer timer;
    private Timer countdownTimer; // Timer untuk hitungan mundur
    
    // STATUS GAME
    private boolean isMenuOpen = true; 
    private boolean isGameOver = false;
    private boolean isCountingDown = false; // Status sedang countdown
    private int countdownValue = 3;         // Angka hitungan
    private String winner = "";
    
    // SETTING KESULITAN
    private int selectedDifficulty = 1; 

    // ASET GAMBAR
    private Image background;

    // KOMPONEN UI
    private JTextField inputNama;
    private JComboBox<String> comboDifficulty; 
    private JButton btnMulai;
    private JLabel labelJudul;

    public GameArena() {
        // SETUP PANEL
        this.setLayout(null); 
        this.setFocusable(true); 
        this.addKeyListener(this); 

        // --- 1. SETUP UI ---
        setupUI();

        // --- 2. LOAD BACKGROUND ---
        try {
            background = ImageIO.read(new File("assets/background.png"));
        } catch (IOException e) {
            System.out.println("Background error");
        }

        // --- 3. INIT KARAKTER AWAL ---
        resetPositions("Player", 1); 

        // --- 4. SETUP WINDOW ---
        JFrame frame = new JFrame("Lomba Balap Karung");
        frame.setSize(1280, 720); 
        
        // --- FITUR BARU: MENGUNCI UKURAN JENDELA ---
        frame.setResizable(false); 
        // -------------------------------------------
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        
        timer = new Timer(30, this); 
        timer.start();
        
        // Letakkan window di tengah layar monitor
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true); 
    }

    // Method Helper untuk Reset Posisi
    private void resetPositions(String namaPlayer, int difficulty) {
        // Player Y: 333
        player = new Player(namaPlayer, 50, 333);
        
        bots = new Bot[3];
        // Bot Y: 346
        int startY = 346; 
        for (int i = 0; i < bots.length; i++) {
            // Jarak Bot: 23
            bots[i] = new Bot(50, startY + (i * 23), difficulty);
        }
    }

    private void setupUI() {
        int centerX = 1280 / 2; // 640
        int centerY = 720 / 2;  // 360

        // Label Judul
        labelJudul = new JLabel("SIAPA NAMAMU?", SwingConstants.CENTER);
        labelJudul.setFont(new Font("Arial", Font.BOLD, 24));
        labelJudul.setForeground(Color.WHITE);
        labelJudul.setBounds(centerX - 150, centerY - 100, 300, 30);
        this.add(labelJudul);

        // Input Text
        inputNama = new JTextField();
        inputNama.setBounds(centerX - 100, centerY - 50, 200, 40);
        inputNama.setFont(new Font("Arial", Font.PLAIN, 18));
        inputNama.setHorizontalAlignment(JTextField.CENTER);
        this.add(inputNama);

        // Dropdown Difficulty
        String[] levels = {"Mudah (Easy)", "Sedang (Medium)", "Sulit (Hard)"};
        comboDifficulty = new JComboBox<>(levels);
        comboDifficulty.setBounds(centerX - 100, centerY + 10, 200, 40);
        comboDifficulty.setSelectedIndex(1); 
        this.add(comboDifficulty);

        // Tombol Mulai
        btnMulai = new JButton("MULAI LOMBA!");
        btnMulai.setBounds(centerX - 80, centerY + 70, 160, 40);
        btnMulai.setBackground(Color.ORANGE);
        btnMulai.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnMulai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                siapkanCountdown();
            }
        });
        this.add(btnMulai);
    }

    private void siapkanCountdown() {
        String nama = inputNama.getText();
        if (nama.trim().isEmpty()) nama = "Jagoan";

        selectedDifficulty = comboDifficulty.getSelectedIndex(); 
        resetPositions(nama, selectedDifficulty);

        isMenuOpen = false;
        labelJudul.setVisible(false);
        inputNama.setVisible(false);
        comboDifficulty.setVisible(false);
        btnMulai.setVisible(false);

        // Mulai Countdown
        isCountingDown = true;
        countdownValue = 3;

        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdownValue--;
                if (countdownValue < 0) {
                    isCountingDown = false;
                    countdownTimer.stop();
                }
                repaint();
            }
        });
        countdownTimer.start();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameArena.this.requestFocusInWindow();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Background otomatis menyesuaikan ukuran
        if (background != null) g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        else { g.setColor(Color.GREEN); g.fillRect(0, 0, getWidth(), getHeight()); }

        player.draw(g);
        for (Bot b : bots) b.draw(g);

        if (isMenuOpen) {
            g.setColor(new Color(0, 0, 0, 180)); 
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // Tampilan Countdown
        if (isCountingDown) {
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 150));
            
            String textTampil;
            if (countdownValue > 0) {
                textTampil = String.valueOf(countdownValue);
            } else {
                textTampil = "GO!";
            }

            FontMetrics fm = g.getFontMetrics();
            int xPos = (getWidth() - fm.stringWidth(textTampil)) / 2;
            int yPos = (getHeight() / 2) + 50;
            
            g.drawString(textTampil, xPos, yPos);
        }

        if (isGameOver) {
            g.setColor(new Color(0, 0, 0, 150)); 
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics fm = g.getFontMetrics();
            int xPos = (getWidth() - fm.stringWidth("PEMENANG: " + winner)) / 2;
            g.drawString("PEMENANG: " + winner, xPos, getHeight()/2);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Tekan SPASI untuk ke MENU", xPos + 20, (getHeight()/2) + 50);
        }

        if (isMenuOpen) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            String instruksi = "Tekan arrow kanan (-->) dan arrow kiri (<--) secara bergantian untuk bergerak!";
            FontMetrics fm = g.getFontMetrics();
            int xPos = (getWidth() - fm.stringWidth(instruksi)) / 2;
            g.drawString(instruksi, xPos, getHeight() - 30);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isMenuOpen && !isGameOver && !isCountingDown) {
            for (Bot b : bots) b.autoMove();
            checkWin();
        }
        repaint();
    }

    private void checkWin() {
        // Garis Finish disesuaikan layar 1280
        int garisFinishX = 1000; 

        if (player.getX() >= garisFinishX) {
            isGameOver = true; 
            winner = player.name + " MENANG!"; 
            return; 
        } 
        for (int i = 0; i < bots.length; i++) {
            if (bots[i].getX() >= garisFinishX) {
                isGameOver = true; winner = "BOT " + (i + 1) + " MENANG!"; 
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isGameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
            isGameOver = false;
            isMenuOpen = true; 
            
            // Visual reset
            resetPositions(player.name, selectedDifficulty);

            labelJudul.setVisible(true);
            inputNama.setVisible(true);
            inputNama.setText(player.name); 
            comboDifficulty.setVisible(true);
            btnMulai.setVisible(true);
            
            repaint();
        } 
        else if (!isMenuOpen && !isGameOver && !isCountingDown) {
            player.handleInput(e.getKeyCode());
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
    
    public static void main(String[] args) {
        new GameArena(); 
    }
}