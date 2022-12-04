import java.awt.*;
import java.util.*;
public class Car {
    //variables
    int x;
    double dx;
    Color c;
    public Car(int x){
        this.x = x;
        dx = 0;
        c = new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));
    }

    //accessor
    public int getX(){return x;}
    public double getDX(){return dx;}

    //movement
    public void move(){x += Math.round(dx);}
    public void accelerate(double i){dx += i;}


    //paint
    public void paint(Graphics g){
        g.setColor(c);
        g.fillRect(x, 193, 20, 14);
    }
}
