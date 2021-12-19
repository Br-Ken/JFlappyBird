package flappybirds;

import pkg2dgamesframework.Objects;
import pkg2dgamesframework.SoundPlayer;

import java.awt.*;
import java.io.File;

public class Bird extends Objects {

    private float vt = 0;

    private boolean isFlying = false;

    private Rectangle rect; //hitbox

    private boolean isLive = true;

    public SoundPlayer flapSound, bupSound, getMoneySound;

    public boolean getLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public  Bird(int x, int y, int w, int h) {
        super(x, y, w , h);
        rect = new Rectangle(x, y, w , h);
        flapSound = new SoundPlayer(new File("src/main/resources/Assests/fap.wav"));
        bupSound = new SoundPlayer(new File("src/main/resources/Assests/fall.wav"));
        getMoneySound = new SoundPlayer(new File("src/main/resources/Assests/getpoint.wav"));
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setVt(float vt) {
        this.vt = vt;
    }

    public void update(long deltaTime) {
        vt += FlappyBirds.g;
        this.setPosY(this.getPosY() + vt); //vi tri len xuoong
        this.rect.setLocation((int)this.getPosX(),(int) this.getPosY());
        if(vt<0) isFlying = true; //neu dang bay
        else isFlying = false; //ngc lai
    }

    public void fly() {
        vt = -3;
        flapSound.play();
    }

    public boolean getIsFlying() {
        return isFlying;
    }
}
