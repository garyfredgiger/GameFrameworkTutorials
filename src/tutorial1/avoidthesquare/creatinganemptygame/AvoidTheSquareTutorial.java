package tutorial1.avoidthesquare.creatinganemptygame;

import game.framework.interfaces.IRender;
import game.framework.utilities.GameEngineConstants;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * STEP 3: Creating the Applet that will contain the game. 
 * 
 * a) Create the class AvoidTheSquareTutorial, have it extend the Applet class and have it also implement the KeyListener interface.
 * b) Be sure to add the following import command:
 * 
 *    import java.applet.Applet;
 *    import java.awt.event.KeyEvent;
 *    import java.awt.event.KeyListener;
 * 
 * b) Add the unimplemented methods listed below that belong to the KeyListenerInterface.
 * 
 *    @Override
 *    public void keyPressed(KeyEvent e)
 *    
 *    @Override
 *    public void keyReleased(KeyEvent e)
 *    
 *    @Override
 *    public void keyTyped(KeyEvent e)
 *
 * c) Override the Applet methods below.
 * 
 *    @Override
 *    public void init()
 * 
 *    @Override
 *    public void paint(Graphics g)
 * 
 *    @Override
 *    public void update(Graphics g)
 *   
 * d) Be sure to add the following import command:
 * 
 *   import java.awt.Graphics;
 *
 */
public class AvoidTheSquareTutorial extends Applet implements KeyListener
{
  /*
   * STEP 5: Adding Instance Variables to the Applet Class
   * 
   * a) Add the variables below:
   * 
   *    image - Will store an off-screen drawable image to be used for double buffering that is 
   *            acquired using the createImage method from the class Component, a parent class of 
   *            the Applet class.
   *            
   *    doubleBuffer - Will store the graphics object from the image, which is assigned using the
   *                   getGraphics() method.
   *                    
   *    avoidTheSquares - An instance of the class from STEP 1 that extends the GameEngine class.
   *    
   *    renderer - An instance of the Renderer class that implements the IRender interface. This is
   *               needed by the game engine class to render each frame of the game. Since the game
   *               engine does not know how go render a frame (this is dependent on the type of Java
   *               application in which the game engine is included), it simply calls the method
   *               responsible for rendering (the renderScreen() method as part of the class 
   *               implementing the IRender interface). Any specific render logic can then be placed
   *               within the renderScreen() method.
   *               
   * b) Be sure to add the following import command:
   * 
   *    import java.awt.Image;
   */
  private Image             image;
  private Graphics          doubleBuffer;

  private AvoidTheSquare    avoidTheSquare;
  private Renderer          renderer;

  /*
   * STEP 6: Initializing the Applet 
   * 
   * a) The key listener needs to be defined for the Applet so input can be passed to the game. To do
   *    this call the method addKeyListener() passing it the parameter this. The parameter this represents
   *    an instance of the Applet class, which implements the KeyListener interface.
   * b) Call the method requestFocus(), which starts the Applet with it having the input focus.
   * c) Create a new instance of the Renderer class.
   * d) Create an instance of the AvoidTheSquare class (passing it an instance of the Renderer class) and
   *    call the methods to initialize and start the game. 
   */
  @Override
  public void init()
  {
    // Start the input listeners
    addKeyListener(this);
    requestFocus();

    renderer = new Renderer();

    avoidTheSquare = new AvoidTheSquare(renderer);
    avoidTheSquare.gameInit();
    avoidTheSquare.gameStart();
  }

  /*
   * STEP 7: Creating the Double Buffer
   * 
   * a) Since we will need to access specific game engine constants, add the following import 
   *    statement at the top of this file.  
   * 
   *    import game.framework.utilities.GameEngineConstants;
   *
   * b) Create the off-screen drawable image by calling the method createImage(), passing it the 
   *    dimensions of the image to create. The dimension are the default game width and height 
   *    that can be accessed from the class GameEngineConstants. This off-screen drawable image
   *    will be used to create the double buffer.
   *    
   * c) Create the graphics object using the off-screen drawable image. This can be done by calling
   *    the method getGraphics() from the image object.
   *    
   * d) Call the paint() method (the method in this Applet) and pass it the graphics object that was 
   *    just created from the off-screen drawable image.
   *    
   * e) The last thing to do is to display the off-screen drawable image.
   * 
   * NOTE: Essentially what is happening here is that a new image is created, the graphics object for 
   *       this image is acquired, which is used by the paint method to draw all of the game entities
   *       to, the newly drawn image is then displayed to the screen using the graphics object passed
   *       into the update method.   
   * 
   */
  @Override
  public void update(Graphics g)
  {
    image = createImage(GameEngineConstants.DEFAULT_CANVAS_WIDTH, GameEngineConstants.DEFAULT_CANVAS_HEIGHT);
    doubleBuffer = image.getGraphics();

    paint(doubleBuffer);
    g.drawImage(image, 0, 0, this);
  }
  
  /*
   * STEP 8: Rendering the Game Frame.
   * 
   * a) Call the method gameDraw() from the instance of the game engine class AvoidTheSquare. 
   *    Remember to pass it the Graphics object (a parameter of the paint() method) and also
   *    remember to type cast it as a Graphics2D object since this is what the gameDraw() 
   *    method requires.  
   * 
   * NOTE: When the paint method is called via the call chain repaint() -> update() -> paint(), 
   *       the graphics object (which was acquired from the off-screen drawable image created 
   *       in the update method) will be passed to the gameDraw() method in the game engine class. 
   *       The gameDraw() method will draw all game entities to it.
   */
  @Override
  public void paint(Graphics g)
  {
    // Draw the game objects
    avoidTheSquare.gameDraw((Graphics2D) g);
  }

  /*
   * STEP 9: Adding Keyboard Input to the Game.
   * 
   * NOTE: The key listener was added to this Applet in the init() method. Every time a key is
   *       pressed  and released, the implemented methods associated with the KeyListener
   *       (keyPressed (), keyReleased() and keyTyped()) are called. The respective game engine 
   *       methods need to be called from within these method to enable the game engine to accept
   *       and react to user input.
   *       
   * a) Add the game engine method call gameKeyPressed() to the method keyPressed().
   * b) Add the game engine method call gameKeyReleased() to the method keyReleased().
   * 
   * NOTE: The method keyTyped will not be used in this tutorial. 
   */
  
  @Override
  public void keyPressed(KeyEvent e)
  {
    avoidTheSquare.gameKeyPressed(e.getKeyCode());
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    avoidTheSquare.gameKeyReleased(e.getKeyCode());
  }

  @Override
  public void keyTyped(KeyEvent e)
  {}

  /*
   * STEP 4: Adding an Inner Class Responsible for Rendering the Frames in the Game engine.
   *
   * a) Add the inner public class Rendered that implements the interface IRender
   * b) Be sure to add the following import command: 
   *
   *    import game.framework.interfaces.IRender;
   *
   * c) Have the inner class implement the method renderScreen() IRender. For now the method body can be left blank.
   * 
   * NOTE: This class implements the interface IRender, which contains the method renderScreen(). This method, invoked
   *       by the game loop within the game engine, is responsible for rendering each frame. Since the game engine does
   *       not know the type of Java application in which it will be used, it does not know how to render a game frame.
   *       This is why an interface is used in this case. The game engine knows that when it calls the interface method
   *       renderScreen(), the current frame of the game loop will be rendered to the screen without knowing how the
   *       rendering will happen.
   */
  public class Renderer implements IRender
  {
    @Override
    public void renderScreen()
    {
      /*
       * STEP 10: Invoking the paint and update Methods.
       * 
       * a) Add the method call repaint() to the body of the method renderScreen().
       * 
       *    NOTE: Since the game engine is included within a Java Applet, the process 
       *          of "painting" the screen is initiated by  first calling the method
       *          repaint(). When repaint is called, it will first call the method
       *          update(), which then calls the method paint. This call chain will
       *          result in the current frame displayed to the Applet screen.
       * 
       * b) Build and run the Applet and a blank Applet with a black screen should be displayed.
       * 
       *    NOTE: If the Applet is executed within Eclipse, the HEIGTH and WIDTH parameters may 
       *          need to be specified by right-clicking on the project, then selecting the menu 
       *          items Run As -> Run Configurations, then clicking on the Parameters tab and
       *          entering 800 for the Width and 600 for the Height.  
       * 
 
       */
      repaint();
    }
  }
}
