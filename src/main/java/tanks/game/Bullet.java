package tanks.game;

import java.awt.*;

public class Bullet {

    private int x;
    private int y;
    private int bearing;

    private Tank sender;

    private Image _sprite;

    private static final int BULLET_SPEED = 7;

    public Bullet(int x, int y, int bearing, Tank sender) {
        this.x = x;
        this.y = y;
        this.bearing = bearing;

        this.sender = sender;

        this._sprite = Toolkit.getDefaultToolkit().createImage("./assets/bullet/bullet.png");

    }

    public void move() {
        this.x += BULLET_SPEED * Math.cos(this.bearing *2 * (Math.PI / 360));
        this.y -= BULLET_SPEED * Math.sin(this.bearing *2 * (Math.PI / 360));
    }

    public void draw(Graphics2D g2d) {
        int drawPosX = this.x - this._sprite.getWidth(null) / 2;
        int drawPosY = this.y - this._sprite.getHeight(null) / 2;

        g2d.rotate(-(this.bearing - 90) * 2 * (Math.PI / 360), this.x, this.y);

        g2d.drawImage(this._sprite, drawPosX, drawPosY, null);

        g2d.rotate((this.bearing - 90) * 2 * (Math.PI / 360), this.x, this.y);
    }

    public boolean checkCollision(Tank t) {
        int drawPosX = this.x - this._sprite.getWidth(null) / 2;
        int drawPosY = this.y - this._sprite.getHeight(null) / 2;

        if (t == sender) {
            return false;
        }

        int tankWidth = t.getWidth();
        int tankHeight = t.getHeight();

        if (!(drawPosX >= t.x && drawPosX <= t.x + t.getWidth()))
            return false;
        else if (!(drawPosY >= t.y && drawPosY <= t.y + t.getHeight()))
            return false;
        else
            return true;
    }

}
