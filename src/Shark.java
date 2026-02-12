import java.awt.*;

public class Shark {

    /**
     * Created by chales on 11/6/2017.
     */


    //VARIABLE DECLARAT ION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;//a boolean to denote if the hero is alive or dead.
    public Image pic;
    public Rectangle hitBox;
    public int health; //gives shark health so it isn't unkillable



    



    public Shark(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx = 10;
        dy = 0;
        width = 150;
        height = 150;
        isAlive = true;
        pic = Toolkit.getDefaultToolkit().getImage("Shark.jpg");
        hitBox= new Rectangle(xpos, ypos, width, height);
        health = 15; //sets health


    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {

        if (xpos >= 950 - width) {
            xpos = 50;

        }

        if (xpos <= 0) {
            xpos = 850;

        }
        if (ypos <= 0) {
            ypos = 1000;
        }
        if (ypos >= 700 - height) {
            ypos = 1;

        }
        xpos = xpos + dx;
        ypos = ypos + dy;
        hitBox= new Rectangle(xpos, ypos, width, height);


    }
}










