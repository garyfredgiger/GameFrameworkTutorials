package tutorial4.avoidthesquare.handlingcollisions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import game.framework.GameEngine;
import game.framework.entities.Entity2D;
import game.framework.entities.shapes.EntityRectangle;
import game.framework.interfaces.IRender;
import game.framework.primitives.Vector2D;
import game.framework.utilities.GameEngineConstants;
import game.framework.utilities.GameUtility;

/**
 * Tutorial 4: Handling Collisions between Entity Types
 * 
 * The steps for this tutorial are contained in the comments of this and the other project source files.
 * 
 *   This source file contains tutorial steps 1, 2, 3, 4, 5 and 6.
 *   
 */

public class AvoidTheSquare extends GameEngine
{
  public AvoidTheSquare(IRender renderer)
  {
    super(renderer);
  }

  private final double ENEMY_SPEED = 75;
  private final double PLAYER_SPEED = 100;
  
  // Used to record current state of user input 
  private boolean      keyLeftPressed;
  private boolean      keyRightPressed;
  private boolean      keyUpPressed;
  private boolean      keyDownPressed;

  /*
   * STEP 3: Adding Flag to Track the Player Reset Action
   * 
   * a) Add a boolean variable named playerResetRequest
   * b) Assign it a value of false;
   * 
   */
  private boolean playerResetRequest = false;
  
  public void addRandomEnemy()
  {
    EntityRectangle enemy = new EntityRectangle();
    enemy.setPosition(GameUtility.random.nextDouble() * GameEngineConstants.DEFAULT_CANVAS_WIDTH, GameUtility.random.nextDouble() * GameEngineConstants.DEFAULT_CANVAS_HEIGHT);
    enemy.setColor(Color.BLUE);

    Vector2D velocity = GameUtility.computeRandomVelocity();
    velocity.scaleThisVector(ENEMY_SPEED);
    enemy.setVelocity(velocity);

    addEnemy(enemy);
  }
  
  /*
   * STEP 2: Moving Code from Method userGameInit() to a New Method reset()
   * 
   * a) Create a new method with the signature public void resetPlayer() {}
   * b) Move the call getPlayer().reset(); from the method body userGameInit() to the body of
   *    the new method resetPlayer().
   * c) Place a call to the resetPlayer() method in the method body userGameInit().
   * d) Run the code to ensure that it behaves the same as before. The resetPlayer() method will 
   *    be called from the userGameInit() method as shown below and when the 'r' key is pressed, 
   *    which will be the next step in this tutorial.
   * e) As an optional exercise, an additional line of code can be added to the method resetPlayer()
   *    that places the player entity at the center of the screen when the resetPlayer method is
   *    called. The code to do this is shown below.
   *    
   *      getPlayer().setPosition(screenWidth / 2, screenHeight / 2);
   *      
   *    Note that the variables screenWidth and screenHeight are protected variables defined in the
   *    GameEngine class and are assigned their respective value when the GameEngine class is instantiated.
   *
   */
  public void userGameInit()
  {
    EntityRectangle player = new EntityRectangle(GameEngineConstants.EntityTypes.PLAYER, 16, 16);
    player.setPosition(GameEngineConstants.DEFAULT_CANVAS_WIDTH/2, GameEngineConstants.DEFAULT_CANVAS_HEIGHT/2);
    this.setNewPlayerEntity(player);

    keyLeftPressed = false;
    keyRightPressed = false;
    keyUpPressed = false;
    keyDownPressed = false;
    
    addRandomEnemy();
  }

  /*
   * Method added from STEP 2a
   */
  public void resetPlayer()
  {
    getPlayer().reset();

    /*
     * Optional line of code to add from step 2e.
     */
    //getPlayer().setPosition(screenWidth / 2, screenHeight / 2);
  }

  public void userGameStart()
  {}

  public void userGameShutdown()
  {}

  /*
   * STEP 5: Processing the Game Reset Request
   * 
   * a) Add a condition that will check if the variable playerResetRequest is true
   * b) In the body of this conditional statement set this flag to false, we only want to execute 
   *    this conditional block once. If the flag playerResetRequest is not set to false this 
   *    conditional block will be executed in subsequent game loop iterations.
   * c) Add a call for the method resetPlayer() to this conditional block. Now, when the key 'r'
   *    is pressed after the player collides with an enemy entity, the flag playerResetRequest will 
   *    be set to true in the gameKeyReleased() method. The flag playerResetRequest will then be 
   *    check for true in this method, userProcessInput(). If it is true, this flag will be cleared 
   *    and the player entity will be reset, that is, its alive and visible flags will be set to 
   *    true again, causing the player entity to be displayed to the screen.
   * d) Run the game and make sure this behavior is working.
   */
  public void userProcessInput()
  {
    double deltaX = 0;
    double deltaY = 0;

    if (playerResetRequest)
    {
      playerResetRequest = false;
      resetPlayer();
    }
    
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
   * STEP 4: Adding a New keyCode Handler to Reset the Game
   * 
   * a) Add a new case to the switch statement to handle the key event for the key 'r' as shown below
   * 
   *      case KeyEvent.VK_R:
   *      
   * b) Add the variable playerResetRequest and assign it a value of true as shown below
   * 
   *      case KeyEvent.VK_R:
   *      
   *        playerResetRequest = true;
   *        
   * c) Don't forget to add the break statement at the end of the block for processing the key event for 'r'.
   *    Now every time the 'r' key is pressed and released this block of code is executed and will assign the
   *    value true to the variable playerResetRequest. This is only half of it, we need to handle this request
   *    in the method userProcessInput(), which will be STEP 5
   * 
   */
  public void gameKeyReleased(int keyCode)
  {
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
        
      case KeyEvent.VK_R:
        playerResetRequest = true;
        break;
    }
  }

  public void gameKeyTyped(int keyCode)
  {}

  /*   
   * STEP 1: Handling Collision between Player and Enemy
   * 
   * As shown in tutorial 3, the game engine detects collisions between the different entity types such as the 
   * player and an enemy. There are currently four types of entities in this game that get compared to one another. 
   * They include PLAYER, ENEMY, PLAYER_SHOT and ENEMY_SHOT. The game engine first compares entities of type
   * PLAYER_SHOT to entities of type ENEMY. Next, entities of type ENEMY_SHOT are compared to the PLAYER entity.
   * Lastly, entities of type ENEMY are compared to the PLAYER. The game engine checks for these collisions every
   * iteration of the game loop. Now whether you handle the collisions between these different types in the method
   * below is up to you. In this tutorial, we will handle collisions between the PLAYER entity and entities of type
   * ENEMY. 
   * 
   * NOTE: How collisions are handled in the game engine
   * 
   * Collision comparisons between entities are made using a bounding box around each entity. The dimensions of 
   * the entity (its width and height) define its bounding box. The intersects() method (part of the Shape interface 
   * implemented by the Rectangle representing the entity) is used to determine if a collision occurs between the 
   * two entities. 
   * 
   * NOTE: What about collisions between entities of the game type?
   * 
   * There may be situations where you may want to check for collisions between the same entity types (e.g., 
   * checking if ENEMY entities collide with one another for a bouncing ball or elastic collision type effect). This
   * is not currently done in the game engine since these types of comparisons (a list of entities compared to itself)
   * has a polynomial growth rate with respect to the number of elements in the list. For example a list of 20 ENEMY 
   * entities compare with itself using a naive approach would have to perform 20 x 20 = 400 comparisons each iteration
   * of the game loop in addition to the other comparisons that take place. Even with a smarter comparison scheme (if 
   * the ith element is compared to the jth element, there is no need to compare j to i in a nested loop) the number
   * of comparisons will be reduced to n (n - 1)/2 comparisons, (190 instead of 400 for n = 20), it still has a 
   * polynomial growth rate. However, if your game does require this type of comparison, it can be added by simply
   * overriding the game engine method gameDetectCollisions(), accessing the ENEMY list via the getEnemies() method
   * and comparing the elements of the list with one another in its own nested loop. Don't forget to call the parent 
   * method super.gameDetectCollisions() if you want to preserve the original collision behavior. A sample of this code
   * is shown below
   * 
   *    @Override
   *    public void gameDetectCollisions()
   *    {
   *      super.gameDetectCollisions();
   * 
   *      System.out.println("Overriden gameDetectCollisions()");
   *       
   *      // You own collision code can be added here
   *    }
   * 
   * NOTE: Other Entity Types 
   * 
   * If you look at the EntityTypes enumeration in the GameEngineConstants class, you will see a fifth entity
   * type called POWER_UP. Even though it exists as a valid entity type, there is no logic to compare other
   * entity types to this type during the execution of the game engine. I have not made a final decision on
   * whether to create a POWER_UP entity list and add it to the game engine so it too can be compared against
   * the other entity lists for collisions. 
   * 
   * a) Since we need to handle a collision between the player and an enemy, logic must be added to this method
   *    checking the types of both entity parameters to see if one is a PLAYER and the other is an ENEMY. Note that
   *    the parameter named entity1 will either have the types PLAYER or PLAYER_SHOT and the parameter named entity2
   *    will either have the types ENEMY or ENEMY_SHOT. Add a conditional statement that checks if the type of
   *    parameter entity1 is PLAYER. 
   * b) Inside the body of this conditional statement, add a second conditional statement that checks if the type of
   *    parameter entity2 is ENEMY.
   * c) Inside the body of this conditional statement add the command to kill entity1 (e.g., entity1.kill()), which
   *    will kill the PLAYER entity when it collides with an entity of type ENEMY. If you run the game you will see 
   *    both the PLAYER and ENEMY entity. Now when the PLAYER entity collides with the ENEMY, the PLAYER is killed
   *    and disappears from the screen. We are not done yet, we need a way to reset the player entity so it can be
   *    displayed again. On to STEP 2. 
   */
  @Override
  public void userHandleEntityCollision(Entity2D entity1, Entity2D entity2)
  {
    System.out.println("A collision occured!");
    
    if (entity1.getEntityType() == GameEngineConstants.EntityTypes.PLAYER)
    {
      if (entity2.getEntityType() == GameEngineConstants.EntityTypes.ENEMY)
      {
        entity1.kill();
      }
    }
  }

  @Override
  public void userGameUpdateEntity(Entity2D entity)
  {
    if (!entity.isAlive())
    {
      return;
    }

    GameUtility.warp(entity, screenWidth, screenHeight);
  }

  @Override
  public void userGamePreUpdate()
  {}

  /*
   * STEP 6: Displaying instructions to the display  
   * 
   * To add a final touch to this demo, instructions can be displayed to the screen for the player.
   * 
   * a) Add a local string variable and assign it instructions regarding the 'r' key to reset the 
   *    player entity after a collision as shown below.
   *    
   *      String instructionMsg = "Press 'r' to Reset the Player Entity after a Collision.";
   *      
   * b) Before a string can be displayed the font and color must be defined. Create a new local 
   *    font variable as shown below. In this tutorial we are using the Font constructor where the
   *    font type, style and size should be defined.  
   *    
   *      g.setFont(new Font("Courier", Font.PLAIN, 18));
   *      
   * c) Set the color of the font to white.
   * d) Given the size of the font and the length of the string, it would be nice to know the dimensions
   *    of this string so that it can be centered on the screen when displaying. To do this we can use the 
   *    graphics object to get the metric of the font. The following call to getFontMetrics().getStringBounds() 
   *    can be performed to get the metrics of the font given the font size and string length. Pass this method 
   *    the string and the graphics object.
   *    
   *      Rectangle2D boundsExitGame = g.getFontMetrics().getStringBounds(instructionMsg, g);
   *    
   *    Note that the font size should be defined and set first before making the call to the method
   *    getFontMetrics().getStringBounds(). Setting the font after the call to this method could generate the
   *    wrong dimensions, which could result in the string being displayed in the wrong place on the screen.
   * e) The last step is to display the string using the graphics object. the graphics object has a method called 
   *    drawString(). This method has a few different overloaded types. The version we will be using only requires 
   *    the string and the x-y coordinates of where to draw the string. This brings up the next issue, where do we
   *    draw the string? Given that we already have the screen width defined (accessible through the game engine 
   *    variable screenWidth) and the dimensions of the string (the width and height), we can position the string
   *    where we want so that it is centered along the horizontal, the vertical or both. In this example we want to
   *    center the string along the horizontal and place it near the top of the screen. Given the screen width and
   *    string width, the x coordinate of the string can be computed as follows:
   *    
   *      (screenWidth - boundsExitGame.getWidth()) / 2)
   *      
   *    Since we want to position the string near the top of the screen, we need to do is multiply the screen height 
   *    by a scaling factor (in this case 0.20). Why do we multiply the screen height by a scaling factor and simply
   *    not assign the height as a fixed number (e.g., 150)? Well, if at some point in the future we decide to change
   *    the dimensions of the game screen, the position will scale with the new screen height, thus not having to change
   *    the parameters in the draw screen method. If a fixed width was specified, then this value would need to be
   *    changed if the screen dimensions were altered in the future. Add the following line to display the string to
   *    screen. Not that our x and y value may produce a result of type double and the draw string method only accepts
   *    coordinates of type int. Therefore we need to type cast the results of our computed x and y coordinates.
   *    
   *      g.drawString(instructionMsg, (int) ((screenWidth - boundsExitGame.getWidth()) / 2), (int) (screenHeight * 0.20));
   *    
   * f) Run the game and you should now see the instructions displayed on the screen regarding the 'r' key.
   * 
   */
  @Override
  public void userGamePreDraw(Graphics2D g) // Changed from graphics
  {
    String instructionMsg = "Press 'r' to Reset the Player Entity after a Collision.";
    
    g.setFont(new Font("Courier", Font.PLAIN, 18));
    g.setColor(Color.WHITE);
    Rectangle2D boundsExitGame = g.getFontMetrics().getStringBounds(instructionMsg, g);
    g.drawString(instructionMsg, (int) ((screenWidth - boundsExitGame.getWidth()) / 2), (int) (screenHeight * 0.20));
  }

  @Override
  public void userGamePostDraw(Graphics2D g) // Changed from graphics
  {}
}

