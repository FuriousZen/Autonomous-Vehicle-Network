import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;
public class Intersection extends JPanel implements KeyListener{
    ArrayList<Car> cars;
    int delay;
    boolean simEnd, toggle;
    long start, end;
    public Intersection(){
        simEnd = false;
        //set toggle to false for control, true for AVN
        toggle = false;
        delay = 10;
        start = System.currentTimeMillis();
        cars = new ArrayList<Car>();
        for(int i = 0; i < 5; i++){
            cars.add(new Car(i * 30 + 10));
        }
    }

    //KeyListener methods
    public void keyPressed(KeyEvent e){
        char c;
        c = e.getKeyChar();
        if(c == 't'){
            toggle = ! toggle;
        }
    }
    public void keyReleased(KeyEvent e) {
        char c;
        c = e.getKeyChar();
    }
    public void keyTyped(KeyEvent e) {
        char c;
        c = e.getKeyChar();     
    }
    
    //Car interactions
    public static int distance(Car a, Car b){
        return Math.abs((b.getX() - 10) - (a.getX() + 10));
    }
    public void followOrTrail(){
        for(int i = cars.size()-1; i >= 1; i--){
            Car a = cars.get(i), b = cars.get(i-1);
            double dx1 = a.getDX(), dx2 = b.getDX();
            if(distance(a, b) > 60){
                if(dx1 > dx2 && dx2 < 3.0){
                    b.accelerate(0.1);
                }
            }else if(b.getX() > (i-1) * 30 + 10 && dx1 < dx2){
                b.accelerate(-0.5);
            }

            b.move();
        }
    }
    
    public void toggle(){
        toggle = !toggle;
    }

    //Run code
    public void paint(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 1000, 400);
        g.setColor(Color.GRAY);
        g.fillRect(0, 180, 1000, 40);
        g.setColor(Color.WHITE);
        for(int i = 15; i < 985; i+=25){
            g.fillRect(i, 198, 15, 4);
        }
        g.fillRect(cars.size() * 30 + 10, 180, 10, 40);
        g.fillRect(970 - cars.size() * 30, 180, 10, 40);
        for(Car c : cars){
            c.paint(g);
        }
        if(simEnd){
            g.setFont(new Font("Courier", Font.BOLD, 20));
            g.drawString(String.valueOf(end-start), 50, 50);
        }
    }
    
    public void run(){
        while(!simEnd){
            try{
                Thread.sleep(delay);
            }catch(InterruptedException e){}

            if(!toggle){
                Car a = cars.get(cars.size()-1);
                if(a.getDX() < 3 && a.getX() < 900){
                    a.accelerate(0.1);
                }else if(a.getDX() > 0){
                    a.accelerate(-0.1);
                }else{
                    a.accelerate(0.1);
                }
                a.move();
            
                followOrTrail();
            }else{
                for(int i = 0; i < cars.size(); i++){
                    Car c = cars.get(cars.size() - 1 - i);
                    if(c.getDX() < 3 && c.getX() < 900 - i * 30){
                        c.accelerate(0.1);
                    }else if(c.getDX() > 0){
                        c.accelerate(-0.1);
                    }
                    c.move();
                }
            }
            //avoidCollision();

            paintImmediately(0, 0, 1000, 400);
            if(cars.get(0).getX() > 970 - cars.size() * 30){
                end = System.currentTimeMillis();
                simEnd = true;
            }
        }
        System.out.println((double)(end - this.start) / 1000 + " seconds");
    }
    public static void main(String [] arg){
        JFrame runner = new JFrame("Game Title");
        runner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        runner.setLocationRelativeTo(null);
        runner.setSize(1000, 400);
        runner.setLayout(null);
        runner.setLocation(0, 0);
        
        Intersection i = new Intersection();
        i.setSize(1000, 400);
        i.setLocation(0, 0);
        runner.getContentPane().add(i);
        
        runner.setVisible(true);

        runner.addKeyListener(i);
        i.run();
    }
}
