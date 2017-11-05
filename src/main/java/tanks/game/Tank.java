package tanks.game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

public class Tank {

    public int x;
    public int y;
    public int bearing;

    public int forwardSpeed = 0;
    public int turningSpeed = 0;

    private Image _tankImage;
    private TanksGame _game;

    public Tank(TanksGame game, int initX, int initY, String imagePath) {
        this.x = initX;
        this.y = initY;
        this.bearing = 0;
        this._game = game;

        this._tankImage = Toolkit.getDefaultToolkit().createImage(imagePath);
    }

    public void move() {
        // ADJUST TANK POSITION
        this.bearing += this.turningSpeed;
        this.x += this.forwardSpeed * Math.cos(this.bearing *2 * (Math.PI / 360));
        this.y -= this.forwardSpeed * Math.sin(this.bearing *2 * (Math.PI / 360));

        // check tank hasn't left game borders
        this.x = Math.min(this.x, this._game.getWidth());
        this.x = Math.max(0, this.x);
        this.y = Math.min(this.y, this._game.getHeight());
        this.y = Math.max(0, this.y);

//        System.out.printf("Bearing: %d X: %d Y: %d\n", this.bearing, this.x, this.y);
    }

    public void draw(Graphics2D g2d) {
        int drawPosX = this.x - this._tankImage.getWidth(null) / 2;
        int drawPosY = this.y - this._tankImage.getHeight(null) / 2;

        g2d.rotate(-(this.bearing - 90) * 2 * (Math.PI / 360), this.x, this.y);

        g2d.drawImage(this._tankImage, drawPosX, drawPosY, null);

        g2d.rotate((this.bearing - 90) * 2 * (Math.PI / 360), this.x, this.y);
    }

    public int getWidth() {
        return this._tankImage.getWidth(null);
    }

    public int getHeight() {
        return this._tankImage.getHeight(null);
    }

}
