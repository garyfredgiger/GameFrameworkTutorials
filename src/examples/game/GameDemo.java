package examples.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import game.framework.GameEngine;
import game.framework.entities.Entity;
import game.framework.interfaces.IRender;
import game.framework.primitives.Vector2D;
import game.framework.utilities.GameEngineConstants;
import game.framework.utilities.GameUtility;

public class GameDemo extends GameEngine
{
  /*
   * Class Variables 
   */

  // Fonts to be used
  private static final String INSTRUCTION_FONT_TYPE = "Comic Sans MS";
  private static final String NUM_ENEMIES_FONT_TYPE = "Comic Sans MS";
  private static final String COLLISION_FONT_TYPE   = "Comic Sans MS";

  // Font sizes
  private static final int    INSTRUCTION_FONT_SIZE = 24;
  private static final int    NUM_ENEMIES_FONT_SIZE = 18;
  private static final int    COLLISION_FONT_SIZE   = 18;

  // Instruction messages
  private static final String INSTRUCTION_MSG1      = "Welcome to the Game Demo";
  private static final String INSTRUCTION_MSG2      = "Use Arrow Keys to Move Red Square";
  private static final String INSTRUCTION_MSG3      = "Press 'a' to Add New Enemies";
  private static final String INSTRUCTION_MSG4      = "Press 'd' to Remove Existing Enemies";

  // Screen Text
  private static final String NUM_ENTITIES_MSG      = "Num Entities: ";
  private static final String NUM_COLLISIONS_MSG    = "Total Collisions: ";

  // Entity constants
  private static double       PLAYER_VELOCITY       = 100;
  private static double       ENEMY_VELOCITY        = 75;

  // Game Specific structure - Can go into the subclass
  private static Color[]      colors                = new Color[] { Color.BLUE, Color.CYAN, Color.MAGENTA, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.PINK, Color.WHITE };

  private static Font         instructionFont       = new Font(INSTRUCTION_FONT_TYPE, Font.BOLD, INSTRUCTION_FONT_SIZE);
  private static Font         scoreFont             = new Font(NUM_ENEMIES_FONT_TYPE, Font.BOLD, NUM_ENEMIES_FONT_SIZE);
  private static Font         collisionFont         = new Font(COLLISION_FONT_TYPE, Font.BOLD, COLLISION_FONT_SIZE);

  /*
   * Class Instance variables 
   */

  // Used to record current state of user input 
  private boolean             keyLeftPressed;
  private boolean             keyRightPressed;
  private boolean             keyUpPressed;
  private boolean             keyDownPressed;

  // Flags to Add/Delete elements from the entities list 
  private boolean             addEntity;
  private boolean             removeEntity;

  // Book keeping variables
  private int                 numCollisions;

  public GameDemo(IRender renderer)
  {
    super(renderer);
  }

  @Override
  public void userGameInit()
  {
    // Make the player entity alive and visible.
    // NOTE: By default the player's visible and alive flags are false.
    getPlayer().reset();

    // Enable the ability for the game engine to remove any dead enemy entities from the enemy entity list 
    removeDeadEnemiesFromEntityList();

    numCollisions = 0;
  }

  @Override
  public void userGameStart()
  {
    // NOTE: No logic needs to be added here for this example 
  }

  @Override
  public void userGamePreUpdate()
  {

  }

  @Override
  public void userGameUpdateEntity(Entity entity)
  {
    // Check if the entities are alive and if so, check if they need to be warped on the screen
    if (!entity.isAlive())
    {
      return;
    }

    GameUtility.warp(entity, screenWidth, screenHeight);
  }

  @Override
  public void userHandleEntityCollision(Entity entity1, Entity entity2)
  {
    // If the player entity collides with another entity, kill the other entity
    switch (entity1.getEntityType())
    {
      case PLAYER:

        if (entity2.getEntityType() == GameEngineConstants.EntityTypes.ENEMY)
        {
          //entity1.kill();
          entity2.kill();
          numCollisions++;
        }

        break;

      default:
    }
  }

  @Override
  public void userProcessInput()
  {
    // Check for user input and set the appropriate keys
    double deltaX = 0;
    double deltaY = 0;

    if (keyLeftPressed)
    {
      deltaX -= PLAYER_VELOCITY;
    }

    if (keyRightPressed)
    {
      deltaX += PLAYER_VELOCITY;
    }

    if (keyUpPressed)
    {
      deltaY -= PLAYER_VELOCITY;
    }

    if (keyDownPressed)
    {
      deltaY += PLAYER_VELOCITY;
    }

    getPlayer().setVelocity(deltaX, deltaY);

    // Add a new enemy entity to the enemy entity list 
    if (addEntity)
    {
      addEntity();
      addEntity = false;  // Be sure to clear the add entity flag or enemy entities will be added each iteration of the game loop. 
    }

    if (removeEntity)
    {
      if (getEnemies().size() > 0)
      {
        getEnemies().removeFirst();
        removeEntity = false;
      }
    }
  }

  @Override
  public void userGamePreDraw(Graphics2D g)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public void userGamePostDraw(Graphics2D g)
  {
    // TODO: Add code here to draw instructions on the screen

    // Set the font and color for the instructions 
    g.setFont(instructionFont);
    g.setColor(Color.WHITE);

    // Compute the dimensions of each instruction string so they can be centered on the screen  
    Rectangle2D boundsIntructionsMsg1 = g.getFontMetrics().getStringBounds(INSTRUCTION_MSG1, g);
    Rectangle2D boundsIntructionsMsg2 = g.getFontMetrics().getStringBounds(INSTRUCTION_MSG2, g);
    Rectangle2D boundsIntructionsMsg3 = g.getFontMetrics().getStringBounds(INSTRUCTION_MSG3, g);
    Rectangle2D boundsIntructionsMsg4 = g.getFontMetrics().getStringBounds(INSTRUCTION_MSG4, g);

    // Display Instructions
    g.drawString(INSTRUCTION_MSG1, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntructionsMsg1.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.15)));
    g.drawString(INSTRUCTION_MSG2, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntructionsMsg2.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.20)));
    g.drawString(INSTRUCTION_MSG3, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntructionsMsg3.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.25)));
    g.drawString(INSTRUCTION_MSG4, (int) ((GameEngineConstants.DEFAULT_CANVAS_WIDTH - boundsIntructionsMsg4.getWidth()) / 2), (int) ((GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.30)));

    g.setFont(scoreFont);
    g.setColor(Color.YELLOW);
    g.drawString(NUM_ENTITIES_MSG + getEnemies().size(), (int) (GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.05), (int) (GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.05));

    g.setFont(collisionFont);
    g.setColor(Color.YELLOW);
    g.drawString(NUM_COLLISIONS_MSG + numCollisions, (int) (GameEngineConstants.DEFAULT_CANVAS_WIDTH * 0.75), (int) (GameEngineConstants.DEFAULT_CANVAS_HEIGHT * 0.05));
  }

  @Override
  public void userGameShutdown()
  {
    // NOTE: Nothing will need to be added here for this example    
  }

  @Override
  public void gameKeyPressed(int keyCode)
  {
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

      case KeyEvent.VK_A:
        addEntity = true;
        break;

      case KeyEvent.VK_D:
        removeEntity = true;
        break;
    }
  }

  @Override
  public void gameKeyReleased(int keyCode)
  {
    switch (keyCode)
    {
      case KeyEvent.VK_UP:
        //          snake.setDirection(Snake.Direction.UP);
        keyUpPressed = false;
        break;

      case KeyEvent.VK_DOWN:
        //          snake.setDirection(Snake.Direction.DOWN);
        keyDownPressed = false;
        break;

      case KeyEvent.VK_LEFT:
        keyLeftPressed = false;
        //          snake.setDirection(Snake.Direction.LEFT);
        break;

      case KeyEvent.VK_RIGHT:
        //          snake.setDirection(Snake.Direction.RIGHT);
        keyRightPressed = false;
        break;
    }
  }

  @Override
  public void gameKeyTyped(int keyCode)
  {
    // NOTE: No logic is needed in this method for this example
  }

  /*
   *  Create a new entity and show them on the screen moving at random velocities
   */
  public void addEntity()
  {
    Entity entity = new Entity();
    entity.setEntityType(GameEngineConstants.EntityTypes.ENEMY);
    entity.setPosition(GameUtility.random.nextDouble() * GameEngineConstants.DEFAULT_CANVAS_WIDTH, GameUtility.random.nextDouble() * GameEngineConstants.DEFAULT_CANVAS_HEIGHT);
    entity.setColor(colors[GameUtility.random.nextInt(colors.length)]);

    Vector2D velocity = GameUtility.computeRandomVelocity();
    velocity.scaleThisVector(ENEMY_VELOCITY);
    entity.setVelocity(velocity);

    getEnemies().add(entity);
  }
}
