package flappybirds;

import pkg2dgamesframework.AFrameOnImage;
import pkg2dgamesframework.Animation;
import pkg2dgamesframework.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FlappyBirds extends GameScreen {

    private BufferedImage birds;
    private Animation bird_anim;

    public static float g = 0.15f; //gia toc huong tam

    private Bird bird;
    private Ground ground;

    private  ChimneyGroup chimneyGroup;
    private int Point = 0;
    private int Best_Point = 0;

    private int BEGIN_SCREEN = 0;
    private int GAMEPLAY_SCREEN = 1;
    private int GAMEOVER_SCREEN = 2;

    private int CurrentScreen = BEGIN_SCREEN;
    private BufferedImage title, bg;
    public FlappyBirds() {
        super(800, 600);
        try {
            birds = ImageIO.read(new File("src/main/resources/Assests/bird_sprite.png"));
        } catch (IOException ex) {}

        bird_anim = new Animation(70);
        AFrameOnImage f;
        f = new AFrameOnImage(0, 0, 60, 60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(60, 0, 60, 60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(120, 0, 60, 60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(60, 0, 60, 60);
        bird_anim.AddFrame(f);

        bird = new Bird(350, 350, 50, 50);
        ground = new Ground();
        chimneyGroup = new ChimneyGroup();

        BeginGame();
    }

    private void resetGame() {
        bird.setPos(360, 250);
        bird.setVt(0);
        bird.setLive(true);
        Point = 0;
        chimneyGroup.resetChimneys();
    }
    public static void main(String[] args) {
        new FlappyBirds();
    }

    @Override
    public void GAME_UPDATE(long deltaTime) {
        if (CurrentScreen == BEGIN_SCREEN) {
            resetGame();
        } else if (CurrentScreen == GAMEPLAY_SCREEN) {

            if(bird.getLive()) bird_anim.Update_Me(deltaTime);
            bird.update(deltaTime);
            ground.Update();
            chimneyGroup.update();

            for (int i = 0; i < ChimneyGroup.SIZE; i++) {
                if(bird.getRect().intersects(chimneyGroup.getChimney(i).getRect())) { // ktra hitbox co cham nhau ko
                    if(bird.getLive()) bird.bupSound.play();
                    bird.setLive(false);
                }
            }

            for (int i = 0; i < ChimneyGroup.SIZE; i++) {
                //ktra bird di qua chimney chua
                if(bird.getPosX() > chimneyGroup.getChimney(i).getPosX() && !chimneyGroup.getChimney(i).getIsBehindBird() && i%2 == 0) {
                    Point ++;
                    bird.getMoneySound.play();
                    chimneyGroup.getChimney(i).setIsBehindBird(true);
                }
            }

            if(bird.getPosY() + bird.getH() > ground.getYGround()) CurrentScreen = GAMEOVER_SCREEN;
        } else {


        }
    }

    @Override
    public void GAME_PAINT(Graphics2D g2) throws IOException {
        title = ImageIO.read(new File("src/main/resources/Assests/title.png"));
        bg = ImageIO.read(new File("src/main/resources/Assests/bg.png"));
        g2.setColor(Color.decode("#b8daef"));
        g2.fillRect(0, 0,  MASTER_WIDTH, MASTER_HEIGHT); //MASTER_ not CUSTOM_ de fill kick co cua so
        g2.drawImage(bg,0,0,null);

        chimneyGroup.paint(g2);
        ground.Paint(g2);

        if (bird.getIsFlying()) {
            bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, -1);
        } else bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, 0);

        if (CurrentScreen == BEGIN_SCREEN) {
            //g2.drawImage(title,0,0,null);
            g2.setColor(Color.red);
            g2.setFont(new Font("SansSerif",Font.BOLD,20));
            g2.drawString("Press space to play game", 270, 400);
        }
        else if (CurrentScreen == GAMEOVER_SCREEN) {
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif",Font.BOLD,50));
            g2.drawString("GAME OVER",250,200);
            g2.setFont(new Font("SansSerif",Font.BOLD,30));
            g2.drawString("Press space turn back begin screen", 150, 560);
            g2.setColor(Color.red);
            g2.setFont(new Font("SansSerif",Font.BOLD,30));
            if (Point > Best_Point){
                Best_Point = Point;
            }
            g2.drawString("Best Point: " + Best_Point, 320 , 250);
        }
        else {
            g2.setColor(Color.red);
            g2.setFont(new Font("SansSerif",Font.BOLD,12));
            g2.drawString("Point: " + Point, 20, 30);
        }

    }

    @Override
    public void KEY_ACTION(KeyEvent e, int Event) {
        if (Event == KEY_PRESSED) {

            if (CurrentScreen == BEGIN_SCREEN) {

                CurrentScreen = GAMEPLAY_SCREEN;

            } else if (CurrentScreen == GAMEPLAY_SCREEN) {

                if(bird.getLive()) bird.fly();

            } else if(CurrentScreen == GAMEOVER_SCREEN){
                CurrentScreen = BEGIN_SCREEN;
            }
        }
    }
}
