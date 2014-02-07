package tutorial2.avoidthesquare.addingtheplayer;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import game.framework.GameEngine;
import game.framework.entities.Entity2D;
import game.framework.entities.shapes.EntityRectangle;
import game.framework.interfaces.IRender;
import game.framework.utilities.GameEngineConstants;
import game.framework.utilities.GameUtility;

/**
 * Tutorial 2: Adding the Player Entity
 * 
 * The steps for this tutorial are contained in the comments of this and the other project source files.
 * 
 *   This source file contains tutorial steps 1, 2, 3, 4, 5, 6, 7 and 8.
 *   
 */

/*
 * STEP 1:
 * 
 * a) Create the class MovingSquare and have it extend the base class GameEngine
 * b) Be sure to add the following import command:
 * 
 * import game.framework.GameEngine;
 * 
 * c) Since the class GameEngine is being sub-classed, the abstract methods in this class will need to be implemented in the class MovingSquare
 * 
 * public abstract void userGameInit();
 * public abstract void userGameStart();
 * public abstract void userGamePreUpdate();
 * public abstract void userGameUpdateEntity(Entity2D entity);
 * public abstract void userHandleEntityCollision(Entity2D entity1, Entity2D entity2);
 * public abstract void userProcessInput();
 * public abstract void userGamePreDraw(Graphics2D g);
 * public abstract void userGamePostDraw(Graphics2D g);
 * public abstract void userGameShutdown();
 * public abstract void gameKeyPressed(int keyCode);
 * public abstract void gameKeyReleased(int keyCode);
 * public abstract void gameKeyTyped(int keyCode);
 * 
 * d) Be sure to add the following import commands since some of the implemented methods require these classes:
 * 
 * import game.framework.entities.Entity2D;
 * import java.awt.Graphics2D;
 */
public class AvoidTheSquare extends GameEngine
{
  /*
   * STEP 2:
   * 
   * a) Add the constructor.
   * b) Be sure to add the following import command:
   * 
   *    import game.framework.interfaces.IRender;
   *    
   */
  public AvoidTheSquare(IRender renderer)
  {
    super(renderer);
  }

  /*
   * Part of STEP 7b
   */
  private final double PLAYER_SPEED = 100; 

  /*
   * STEP 4:
   * 
   * a) Add the variables below to keep track of the players input state.
   * b) Be sure to initialize these variables in the method userGameInit().
   *  
   */

  // Used to record current state of user input 
  private boolean      keyLeftPressed;
  private boolean      keyRightPressed;
  private boolean      keyUpPressed;
  private boolean      keyDownPressed;

  /*
   * STEP 3:
   *
   * a) Prepare the player entity by creating a new shape entity.
   *
   * NOTE: The player entity needs to be created from one of the entity sub classes (either and image or 
   *       shape entity such as rectangle or square). Why? The base entity class (Entity2D) can be 
   *       instantiated, but its draw() method does not contain any logic and needs to be overridden and 
   *       implemented by a sub class. When a sub class of the Entity2D class is created and its draw() 
   *       method defined, it can be added as the player entity by using the method setNewPlayerEntity().
   *       By default the player's visible and alive flags are true. If the player entity is created, but
   *       you do not want to display it right away, set either the visible or alive flag to false. If either
   *       the visible or alive flags of an entity are set to false, the entity will not be drawn to the 
   *       screen during the render operation.
   */
  public void userGameInit()
  {
    /*
     * Part of STEP 3a - Adding the player entity.
     * 
     * Create a new instance of the class EntityRectangle and...
     * 
     *    1) Give it the entity type PLAYER from the Enum EntityTypes
     *    2) Define its dimensions 16 by 16 for its width and height respectively
     *    3) Set its position to the center of the screen.
     * 
     * NOTE: The visible and alive flags are true by default and the default color of a EntityShape (rectangle,
     *       line or oval) is RED.
     *       
     * NOTE: The variables screenWidth and screenHeight are protected variables defined in the GameEngine class and are assigned their 
     *       respective value when the GameEngine class is instantiated.
     */
    EntityRectangle player = new EntityRectangle(GameEngineConstants.EntityTypes.PLAYER, 16, 16);
    player.setPosition(screenWidth/2, screenHeight/2);
    this.setNewPlayerEntity(player);

    /*
     * Part of STEP 4b - Initializing the user input state variables.
     */
    keyLeftPressed = false;
    keyRightPressed = false;
    keyUpPressed = false;
    keyDownPressed = false;
  }

  public void userGameStart()
  {}

  public void userGameShutdown()
  {}

  /*
   * STEP 7:
   * 
   * a) Add two local variables to this method representing the X and Y components of the velocity.
   * b) Add a constant PLAYER_SPEED to this class (scroll to the top) that represents the players velocity 
   * c) Add conditions to handle each of the input state variables. For each condition add the respective
   *    velocity representing the motion in that direction (e.g., if the input state variable keyLeftPressed
   *    is true, then add the PLAYER_SPEED to the local variable representing the y component of the player's
   *    velocity).
   *    
   *    NOTE: Up and left motion are represented as negative values where as down and right motion are
   *          represented as positive values. 
   * 
   * d) Call the method to set the player's velocity (both x and y components), which can be accessed using the getPlayer() method. 
   * 
   */
  public void userProcessInput()
  {
    double deltaX = 0;
    double deltaY = 0;

    if (keyLeftPressed)
    {
      deltaX -= PLAYER_SPEED;
    }

    if (keyRightPressed)
    {
      deltaX += PLAYER_SPEED;
    }

    if (keyUpPressed)
    {
      deltaY -= PLAYER_SPEED;
    }

    if (keyDownPressed)
    {
      deltaY += PLAYER_SPEED;
    }

    getPlayer().setVelocity(deltaX, deltaY);
  }

  /*
   * STEP 5
   * 
   * a) Add the switch statement that keyCode of the user input passed in as a parameter.
   * b) Add cases for each arrow keys.
   * c) Be sure to add the following import command:
   * 
   *    import java.awt.event.KeyEvent;
   *
   * d) Set each of the corresponding variables from STEP 4 to true when the respective arrow key is pressed.
   * 
   */
  public void gameKeyPressed(int keyCode)
  {
    //System.out.println("gameKeyPressed: " + keyCode);
    switch (keyCode)
    {
      case KeyEvent.VK_UP:
        keyUpPressed = true;
        break;

      case KeyEvent.VK_DOWN:
        keyDownPressed = true;
        break;

      case KeyEvent.VK_LEFT:
        keyLeftPressed = true;
        break;

      case KeyEvent.VK_RIGHT:
        keyRightPressed = true;
        break;
    }
  }

  /*
   * STEP 6
   * 
   * a) Add the switch statement that keyCode of the user input passed in as a parameter.
   * b) Add cases for each arrow keys.
   * c) Set each of the corresponding variables from STEP 4 to false when the respective arrow key is pressed.
   * 
   */
  public void gameKeyReleased(int keyCode)
  {
    //System.out.println("gameKeyReleased: " + keyCode);
    switch (keyCode)
    {
      case KeyEvent.VK_UP:
        keyUpPressed = false;
        break;

      case KeyEvent.VK_DOWN:
        keyDownPressed = false;
        break;

      case KeyEvent.VK_LEFT:
        keyLeftPressed = false;
        break;

      case KeyEvent.VK_RIGHT:
        keyRightPressed = false;
        break;
    }
  }

  public void gameKeyTyped(int keyCode)
  {}

  @Override
  public void userHandleEntityCollision(Entity2D entity1, Entity2D entity2)
  {}

  /*
   * STEP 8: Warping the Player Across the Screen.
   * 
   * a) Add logic to check if the current entity is not alive, then exits the method id this is the case. 
   * b) Be sure to include the import statement
   * 
   *    import game.framework.utilities.GameUtility
   *    
   * c) Call the method warp() from the game utility class.
   * 
   *    NOTE: The method userGameUpdateEntity() is called once for each entity in the game.
   *          This allows for operations to be performed on each entity during each iteration 
   *          of the game loop. In this case if the current entity is alive, its position is 
   *          check to see if it crosses any of the visible screen boundaries (i.e., is the 
   *          entity off the screen). If this is the case the x-y position components of the 
   *          entity are updated so it will appear on the other side of the visible screen 
   *          (e.g., if the entity goes off the right side of the screen, it will appear on
   *          the left side).
   *    
   * d) Build and run the Applet, you should see the Applet window with a black background and a red
   *    square in the center of the Applet window. Move the arrow keys and the square should move (if
   *    you do not get any movement you may need to click in the Applet window with the mouse). Move the
   *    square off one side of the screen and it should appear on the other side.
   *    
   *    NOTE: If the Applet is executed within Eclipse, the HEIGTH and WIDTH parameters may 
   *          need to be specified by right-clicking on the project, then selecting the menu 
   *          items Run As -> Run Configurations, then clicking on the Parameters tab and
   *          entering 800 for the Width and 600 for the Height.
   */
  @Override
  public void userGameUpdateEntity(Entity2D entity)
  {
    if (!entity.isAlive())
    {
      return;
    }

    // NOTE: screenWidth and screenHeight are defined in the GameEngine class. If the default constructor is used
    //       these variables are assigned the default values DEFAULT_CANVAS_WIDTH and DEFAULT_CANVAS_HEIGHT that
    //       can be found in the class GameEngineConstants in the package game.framework.utilities.
    GameUtility.warp(entity, screenWidth, screenHeight);
  }

  @Override
  public void userGamePreUpdate()
  {}

  @Override
  public void userGamePreDraw(Graphics2D g) // Changed from graphics
  {}

  @Override
  public void userGamePostDraw(Graphics2D g) // Changed from graphics
  {}
}
