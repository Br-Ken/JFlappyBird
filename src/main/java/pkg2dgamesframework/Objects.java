package pkg2dgamesframework;

public class Objects {
    
    private float posX, posY;
    private float w, h;
    
    public Objects(float x, float y, float w, float h){
        this.posX = x;
        this.posY = y;
        this.w = w;
        this.h = h;
    }

    public boolean isCollisionHappenWith(float x, float y, float w, float h){
        if(x < posX + this.w && x + w > posX && y < posY + this.h && h + y > posY)
            return true;
        return false;
    }

    public void setPos(float x, float y){
        posX = x;
        posY = y;
    }
    public void setPosX(float x){
        posX = x;
    }

    public void setPosY(float y){
        posY = y;
    }

    public float getPosX(){
        return posX;
    }

    public float getPosY(){
        return posY;
    }

    public float getH(){
        return h;
    }

}
