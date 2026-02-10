//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;
    public Image backGroundPic;


    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    private Fish Fish;
    private BigFish BigFish;
    private Shark Shark;
    private FishFood FishFood;
    private Poison Poison;
    private SmallShark SmallShark;
    private FishWin FishWin;
    private SharksWin SharksWin;


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() {






        setUpGraphics();
        // random spot for fish to appear
        int randx = (int)(Math.random()*10) +1;

        randx= (int)(Math.random()*999) + 1;

        int randy =  (int)(Math.random()*699) +1;
        randy=(int)(Math.random()*699) + 1;

        //variable and objects
        //create (construct) the objects needed for the game and load up
        backGroundPic = Toolkit.getDefaultToolkit().getImage("Ocean.jpg");//load the picture
        Fish = new Fish(200, randy);
        BigFish = new BigFish(randx, 450);
        Shark = new Shark(randx, 200);
        FishFood = new FishFood (150,550);
        Poison = new Poison(600,600);
        SmallShark = new SmallShark(randx,randy);
        SharksWin = new SharksWin(250,100);
        FishWin = new FishWin(250,100);




    }// BasicGameApp()


//*******************************************************************************
//User Method Section
//

    // this is the code that plays the game after you set things up
    public void run() {


        while (true) {

            moveThings();  //move all the game objects
            render();  // paint the graphics
            pause(20);
        }
    }


    public void moveThings() {
        //calls the move( ) code in the objects
        Fish.move();
        BigFish.move();
        Shark.move();
        crashing();
        SmallShark.move();

    }

    public void crashing() {
        //check to see if my fish crash into eachother
        if (Shark.hitBox.intersects(BigFish.hitBox) && BigFish.isAlive == true && Shark.isAlive == true) {
            System.out.println("CRASH!!!");
            BigFish.dy = -BigFish.dy;
            BigFish.isAlive = false;
            Shark.health = Shark.health -1;
            Shark.dx = Shark.dx +3;
            System.out.println(Shark.health);


        }
        //can make fish dissapear if they are hitting eachother
        if (Shark.hitBox.intersects(Fish.hitBox) && Fish.isAlive == true && Shark.isAlive == true) {
            System.out.println("CRASH!!!");
            Fish.dy = -Fish.dy;
            Fish.isAlive = false;
            Shark.health = Shark.health -1;
            Shark.dx = Shark.dx +3;
            System.out.println(Shark.health);
        }
        //shark can now die if it hits fish too many times (15 to be exact)
        if (Shark.health == 0){
            Shark.isAlive = false;
        }
        //makes it so that fish can come back to life if he hits the food
        if (FishFood.hitBox.intersects(Fish.hitBox) && Fish.isAlive == false &&FishFood.isAlive == true){
            System.out.println("BANG");
            Fish.dy=-Fish.dy;
            Fish.isAlive = true;
        }
        //makes it so the bigfish can come back to life if it hits the food
        if (FishFood.hitBox.intersects(BigFish.hitBox) && BigFish.isAlive == false && FishFood.isAlive == true){
            System.out.println("CLANG");
            BigFish.dy=-BigFish.dy;
            BigFish.isAlive = true;
        }
        //poison kills the shark :):)
        if (Poison.hitBox.intersects(BigFish.hitBox) && Shark.isAlive == true && BigFish.isAlive == true){
            Shark.isAlive = false;
            SmallShark.isAlive = false;
            Poison.isAlive = false;
            FishWin.isAlive = true;
        }
        // now both fish can eleiminate shark
        if (Poison.hitBox.intersects(Fish.hitBox)&& Fish.isAlive == true ){
            Shark.isAlive = false;
            SmallShark.isAlive = false;
            Poison.isAlive = false;
            FishWin.isAlive = true;

        }
        //can make sharks win be getting rid of the fish food
        if (SmallShark.hitBox.intersects(FishFood.hitBox) && SmallShark.isAlive == true){
            SmallShark.dy=-SmallShark.dy;
            FishFood.isAlive = false;
        }
        if (SmallShark.hitBox.intersects(BigFish.hitBox)&& SmallShark.isAlive == true && BigFish.isAlive == true){
            SmallShark.dx = SmallShark.dx + 2;
            SmallShark.dy = SmallShark.dy + 2;

        }
        if (SmallShark.hitBox.intersects(Fish.hitBox)&&SmallShark.isAlive == true && Fish.isAlive == true){
            SmallShark.dx = SmallShark.dx + 2;
            SmallShark.dy = SmallShark.dy + 2;

        }
        //sharks win
        if (SmallShark.hitBox.intersects(FishFood.hitBox) && SmallShark.isAlive == true && Fish.isAlive == false && BigFish.isAlive == false){
            SmallShark.dy=-SmallShark.dy;
            FishFood.isAlive = false;
            SharksWin.isAlive = true;
            FishWin.isAlive = false;
        }
        //kills smallshark
        if (SmallShark.hitBox.intersects(Poison.hitBox) ){
            SmallShark.isAlive = false;
        }  //displays symbol if sharks win
        // displays simbol if fish win
        if (FishWin.isAlive == false && Shark.isAlive == false && SmallShark.isAlive== false ){
            FishWin.isAlive = true;}
        if (SharksWin.isAlive == false && Fish.isAlive == false && BigFish.isAlive== false && FishFood.isAlive == false){
            SharksWin.isAlive = true;
        }

        // everything disapears so only win screen is visible
       // only win screen is visible

        //makes fish bounce off of eachother
        if (Fish.hitBox.intersects((BigFish.hitBox))&& Fish.isAlive == true && BigFish.isAlive == true){
            Fish.dx=-Fish.dx;
            BigFish.dx=-BigFish.dx;
        }
        //sharks bounce off of eachother
        if (Shark.hitBox.intersects((SmallShark.hitBox))&&Shark.isAlive == true){
            SmallShark.dy=-SmallShark.dy;
            SmallShark.dx=-SmallShark.dx;
        }

        //help with a bug
        if (SharksWin.isAlive == true){

            System.out.println("Sharks Win");
        }
        //same thing here win screens were buggy
        if (FishWin.isAlive == true){
            System.out.println("Fish Win");
        }






    }


    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }


    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(backGroundPic, 0, 0, WIDTH, HEIGHT,null);

        //draw the image of the astronaut
        //makes it so fish can die
        if (Fish.isAlive == true){
        g.drawImage(Fish.pic, Fish.xpos, Fish.ypos, Fish.width, Fish.height, null);}
        // makes it so fish can die
        if (BigFish.isAlive == true){
        g.drawImage(BigFish.pic, BigFish.xpos, BigFish.ypos, BigFish.width, BigFish.height, null);}
        if (Shark.isAlive == true){
            //sharl can die
        g.drawImage(Shark.pic, Shark.xpos, Shark.ypos, Shark.width, Shark.height, null);}
        if (FishFood.isAlive == true){
            //all these if statements mean that things wont draw
        g.drawImage(FishFood.pic, FishFood.xpos, FishFood.ypos, FishFood.width, FishFood.height, null);}
        if (Poison.isAlive == true){
        g.drawImage(Poison.pic, Poison.xpos, Poison.ypos, Poison.width, Poison.height, null);}
        if (SmallShark.isAlive ==true){
        g.drawImage(SmallShark.pic, SmallShark.xpos, SmallShark.ypos, SmallShark.width, SmallShark.height, null);}
        if (FishWin.isAlive == true){
        g.drawImage(FishWin.pic, FishWin.xpos, FishWin.ypos, FishWin.width, FishWin.height, null);}
        if (SharksWin.isAlive == true){
            g.drawImage(SharksWin.pic, SharksWin.xpos, SharksWin.ypos, SharksWin.width, SharksWin.height, null);}

        //g.drawRect(BigFish.hitBox.x, BigFish.hitBox.y, BigFish.hitBox.width, BigFish.hitBox.height);
        // g.drawRect(Shark.hitBox.x, Shark.hitBox.y, Shark.hitBox.width, Shark.hitBox.height);
        g.dispose();

        bufferStrategy.show();
    }
}

