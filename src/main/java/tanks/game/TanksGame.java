package tanks.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class TanksGame extends JPanel implements KeyListener {

    private static final int GAME_WIDTH = 640;
    private static final int GAME_HEIGHT = 480;

    private static final int TANK_FORWARD_SPEED = 20;
    private static final int TANK_TURNING_SPEED = 20;

    private Tank playerTank; // controls: arrow keys
    private Tank player2Tank; // controls: wasd

    private LinkedList<Bullet> bullets;

    private Timer repaintTimer;

    public TanksGame() {
        super();
        setSize(GAME_WIDTH, GAME_HEIGHT);

        this.playerTank = new Tank(this, this.getWidth() / 2 + 100, this.getHeight() / 2 - 40,
                "./assets/blue_tank/blue_tank_1.png");

        this.player2Tank = new Tank(this, this.getWidth() / 2 - 100, this.getHeight() / 2 + 40,
                "./assets/green_tank/green_tank_1.png");

        bullets = new LinkedList<>();

        setFocusable(true);
        addKeyListener(this);

        // repaint
        TanksGame game = this;
        repaintTimer = new Timer(1 / 30, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.repaint();
            }
        });
        repaintTimer.start();
    }

    @Override
    public void paint(Graphics g) {
        // move player tank
        playerTank.move();
        player2Tank.move();

        // update scene
        Graphics2D g2d = (Graphics2D) g;

        // move and draw all bullets
        for (Bullet b : bullets) {
            b.move();
            b.draw(g2d);
        }

        this.playerTank.draw(g2d);
        this.player2Tank.draw(g2d);

        // check collisions
        for (Bullet b : bullets) {

            if (b.checkCollision(playerTank)) {
                System.out.println("Player 1 died!");
                repaintTimer.stop();
            }

            else if (b.checkCollision(player2Tank)) {
                System.out.println("Player 2 died!");
                repaintTimer.stop();
            }

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // player 1 tank
        if (keyCode == KeyEvent.VK_UP) {
            playerTank.forwardSpeed = TANK_FORWARD_SPEED;
        }
        else if (keyCode == KeyEvent.VK_DOWN) {
            playerTank.forwardSpeed = -TANK_FORWARD_SPEED;
        }
        else if (keyCode == KeyEvent.VK_LEFT) {
            playerTank.turningSpeed = TANK_TURNING_SPEED;
        }
        else if (keyCode == KeyEvent.VK_RIGHT) {
            playerTank.turningSpeed = -TANK_TURNING_SPEED;
        }

        // player 1 fires
        if (keyCode == KeyEvent.VK_M) {
            Bullet b = new Bullet(playerTank.x, playerTank.y, playerTank.bearing, playerTank);
            for (int i = 0; i <= 5; i++) {
                b.move();
            }
            bullets.add(b);
        }

        // player 2 tank
        if (keyCode == KeyEvent.VK_W) {
            player2Tank.forwardSpeed = TANK_FORWARD_SPEED;
        }
        else if (keyCode == KeyEvent.VK_S) {
            player2Tank.forwardSpeed = -TANK_FORWARD_SPEED;
        }
        else if (keyCode == KeyEvent.VK_A) {
            player2Tank.turningSpeed = TANK_TURNING_SPEED;
        }
        else if (keyCode == KeyEvent.VK_D) {
            player2Tank.turningSpeed = -TANK_TURNING_SPEED;
        }

        // player 2 fires
        if (keyCode == KeyEvent.VK_C) {
            Bullet b = new Bullet(player2Tank.x, player2Tank.y, player2Tank.bearing, player2Tank);
            for (int i = 0; i <= 5; i++) {
                b.move();
            }
            bullets.add(b);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // player 1 tank
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            playerTank.forwardSpeed = 0;
        }
        else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            playerTank.turningSpeed = 0;
        }

        // player 2 tank
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S) {
            player2Tank.forwardSpeed = 0;
        }
        else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D) {
            player2Tank.turningSpeed = 0;
        }
    }

    @Override
    public void keyTyped (KeyEvent e) { }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ShittyTanks v1.0");
        frame.add(new TanksGame());
        frame.setSize(GAME_WIDTH, GAME_HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
