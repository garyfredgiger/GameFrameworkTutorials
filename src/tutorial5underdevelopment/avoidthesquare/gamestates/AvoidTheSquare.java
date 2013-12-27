package tutorial5underdevelopment.avoidthesquare.gamestates;

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

/**
 * Tutorial 5: Adding Game States
 * 
 * The steps for this tutorial are contained in the comments of this and the other project source files.
 * 
 * This source file contains tutorial steps XXXX.
 * 
 * A few things to note about game states.
 * 
 * There are a set of pre-defined game states (defined as the enumeration GameState) in the class GameEngineConstants.
 * These states include INTRODUCTION, GAME_START, PLAYING, PAUSED, GAMEOVER, LEVEL_NEXT, PLAYER_DEAD,
 * EXIT_PLAYING_GAME, EXIT_GAME and UNDEFINED. Note that not all of these states need to be handled in the game that you
 * may develop, but they are provided if you need them. Note that as ths game frame evolves, more states may be added
 * in future versions. Currently, these are the states that appear to be common among most 2D games. A typical state transition
 * table may be defined as:
 * 
 * Current State Next State Notes
 * 
 * INTRODUCTION GAME_START
 * 
 * GAME_START PLAYING
 * 
 * PLAYING PAUSED
 * PLAYING GAMEOVER
 * PLAYING LEVEL_NEXT
 * PLAYING PLAYER_DEAD
 * PLAYING EXIT_PLAYING_GAME
 * 
 * PAUSED PLAYING
 * 
 * LEVEL_NEXT PLAYING
 * 
 * PLAYER_DEAD PLAYING A transition can occur back to the playing state if there are more player lives left
 * PLAYER_DEAD GAMEOVER If there are no more player lives left, then a transition to game over can occur.
 * 
 * GAMEOVER INTRODUCTION In more classic arcade game, when the game is over, a message is displayed for a
 * few seconds before transitioning to the next state, typically back to the INTRODUCTION state.
 * 
 * EXIT_PLAYING_GAME GAMEOVER
 * 
 * EXIT_GAME
 * 
 * In this tutorial we will implement a subset of these state to give you a feel for a basic game flow.
 * 
 * Current State Next State
 * 
 * INTRODUCTION GAME_START
 * GAME_START PLAYING
 * PLAYING PAUSED
 * PLAYING GAMEOVER
 * PAUSED PLAYING
 * GAMEOVER INTRODUCTION
 * 
 */
public class AvoidTheSquare extends GameEngine
{
  /*
   * From STEP 1 - Adding all Game Variables
   * 
   * For this step, we will add all string constants used for the messages, their corresponding fonts, 
   * state timing and flag variables and the number of enemies for the game.
   * 
   * a) Add the string messages that will be displayed in the various screens throughout the game. 
   * b) Add the corresponding font variables used for the string messages.
   * c) Add the time interval variables for both the game start state and the game over state. These variables
   *    define how much time (in ms) will be spent in this state before moving to the next state.
   * d) Add the time variables that are assigned the time stamp of when the game start and game over states are
   *    entered. The time recorded in these variables plus the respective time interval will be compared to the
   *    current time. When the current time exceeds this sum, a transition will be made to the next state.   
   * e) Add the boolean variable for the user specified game start event. This variable will be 
   *    set to true when the user presses the respective key (e.g., the space bar) to begin the game.
   * f) Add the boolean variable to record whether the player is dead or not. When the player dies (i.e., collides
   *    with an enemy entity) this variable will be set, which will start the transition to the GAMEOVER state.
   * g) Add the constant representing the number of enemies that will exist in the game.
   */

  // Step 1a - String messages used throughout the game on various screens.
  private static final String INTRODUCTION_MSG1      = "Avoid the Sqaure";
  private static final String INTRODUCTION_MSG2      = "Press the Space Bar to Start";
  private static final String GAME_START_MSG         = "Get Ready!!!";
  private static final String GAME_OVER_MSG          = "Game Over";

  // Step 1b - The corresponding font variables used for the string messages
  private static final Font   INTRODUCTION_FONT_MSG1 = new Font("Comic Sans MS", Font.BOLD, 68);
  private static final Font   INTRODUCTION_FONT_MSG2 = new Font("Comic Sans MS", Font.BOLD, 24);
  private static final Font   GAME_START_FONT_MSG    = new Font("Comic Sans MS", Font.BOLD, 32);
  private static final Font   GAME_OVER_FONT_MSG     = new Font("Comic Sans MS", Font.BOLD, 48);

  // Step 1c - The interval variables that define how much time is spent in the game start and game over states.
  private static final long   GAME_START_INTERVAL    = 3000;
  private static final long   GAME_OVER_INTERVAL     = 2000;

  // Step 1d - Stores current time for timed event state transitions
  private long                stateGameOverTime;
  private long                stateGameStartTime;

  // Step 1e - Stores current input state for user event state transitions
  private boolean             startGameRequest;
 // private boolean             pauseGameRequest;
  
  // Step 1f - Indicates if the player is dead or alive.
  private boolean             playerDead;
  
  // Step 1g - The number of enemies that will exist in the game.
  private static final int    STARTING_NUMBER_OF_ENEMIES = 10;

  private final double ENEMY_SPEED  = 75;
  private final double PLAYER_SPEED = 100;

  // Used to record current state of user input 
  private boolean      keyLeftPressed;
  private boolean      keyRightPressed;
  private boolean      keyUpPressed;
  private boolean      keyDownPressed;

  // This will go away
  //private boolean      playerResetRequest = false;

  public AvoidTheSquare(IRender renderer)
  {
    super(renderer);
  }

  public void addRandomEnemy()
  {
    Entity enemy = new Entity();
    enemy.setPosition(GameUtility.random.nextDouble() * GameEngineConstants.DEFAULT_CANVAS_WIDTH, GameUtility.random.nextDouble() * GameEngineConstants.DEFAULT_CANVAS_HEIGHT);
    enemy.setColor(Color.BLUE);

    Vector2D velocity = GameUtility.computeRandomVelocity();
    velocity.scaleThisVector(ENEMY_SPEED);
    enemy.setVelocity(velocity);

    addEnemy(enemy);
  }

  /*
   * STEP 2 - Creating the Method to Add the Enemy Entities
   * 
   * This step involves writing the method that will add multiple enemies to the game when called.
   * Since the method addRandomEnemy() to add a single random enemy already exists, this method
   * can be called from a loop to add multiple enemies.
   * 
   * a) Add a for loop that executes from [0, STARTING_NUMBER_OF_ENEMIES)
   * b) Place a call to the method addRandomEnemy() inside of the for loop body. 
   * 
   */
  public void addEnemiesToGame()
  {
    for(int i=0; i<STARTING_NUMBER_OF_ENEMIES; i++)
    {
      addRandomEnemy();
    }
  }

  /*
   * STEP 3: Initializing the Game 
   * 
   * Since several game states will exist in the game, we will want to initialize the state variables 
   * so that when the game is executed, it will enter the defined starting state. In this tutorial
   * the starting state will be INTRODUCTION.
   * 
   * a) Given the body of method userGameInit() from tutorial 4, remove the following lines of code.
   * 
   *      resetPlayer();
   *      addRandomEnemy();
   * 
   * b) Add a line that assigns the starting state of INTRODUCTION to the state variable. NOTE: The 
   *    state variable is defined in the GameEngine class and is of type GameEngineConstants.GameState.
   * c) Initialize the variable startGameRequest to false. If this variable is set to true when the game
   *    begins, the game state will immediately change from INTRODUCTION to GAME_START. The variable
   *    startGameRequest is the flag that is set when the user chooses to start the game (in this case
   *    by pressing the space bar, which the logic will be added later in this tutorial).
   * d) Initialize the variables stateGameOverTime and stateGameStartTime to zero.
   * 
   */
  public void userGameInit()
  {
    // Step 3a - This line (added to this method from tutorial 4) can be removed since it will be called from another place in this game.
    //resetPlayer();

    // NOTE: These lines were added is tutorial 2.
    keyLeftPressed = false;
    keyRightPressed = false;
    keyUpPressed = false;
    keyDownPressed = false;

    // Step 3b - Defining the initial state when the game begins. 
    this.state = GameEngineConstants.GameState.INTRODUCTION;

    // Step 3c -Initializing the input state variables 
    startGameRequest = false;
    //pauseGameRequest = false; // TODO: Add this step to Tutorial 6

    // Step 3d - Initialize the timed event state transitions variables
    stateGameOverTime = 0;
    stateGameStartTime = 0;

    // Step 3a - This line (added to this method from tutorial 4) can also be removed since it is being called from within the method addEnemiesToGame().
    //addRandomEnemy();
  }

  /*
   * STEP 12 - Resetting the Player Dead Flag
   * 
   * When the game is initialzed and when a new game starts, the flag playerDead will need to be cleared.
   * 
   * a) Add the line to clear the player dead flag to the body of method resetPlayer().
   * 
   */
  public void resetPlayer()
  {
    // Step 1a - Clearing the playerDead flag.
    playerDead = false;
    
    keyLeftPressed = false;
    keyRightPressed = false;
    keyUpPressed = false;
    keyDownPressed = false;
    
    getPlayer().reset();

    /*
     * Optional line of code to add if player is to start in center of screen.
     * In this tutorial we decided to use this line.
     */
    getPlayer().setPosition(screenWidth / 2, screenHeight / 2);
  }

  public void userGameStart()
  {}

  public void userGameShutdown()
  {}

  /* (This step is not complete yet)
   * STEP 8 - Processing User Key Presses
   * 
   * This step will process the user input flags that were set in the method gameKeyPressed().
   * 
   * a) Again, add a switch statement based on the state variable, the same as in both methods
   *    gameKeyPressed() and gameKeyReleased().
   * b) Add cases to the switch statement to handle game states INTRODUCTION and PLAYING.
   * c) In the INTRODUCTION case add a condition statement to check if the flag startGameRequest is true.
   *    This conditional statement will begin the transition into the state GAME_START.
   * d) In the block of the conditional statement, please add logic to transition into the state GAME_START.
   *    This will involve first setting the flag startGameRequest to false (We do not want to execute this 
   *    block again the next time the method is entered). Next, the current time will need to be recorded 
   *    in the variable stateGameStartTime. This time will be used to determine how long the game will remain
   *    in the state GAME_START before transitioning into the PLAYING state, which will be checked in the
   *    method userGamePreUpdate(). Lastly, assign the state GAME_START to the state variable. Now, when the 
   *    current game state is INTRODUCTION and the user presses the space bar, the flag startGameRequest is set
   *    in the method gameKeyPressed(). When the method userProcessInput() is entered, the condition statement
   *    in the INTRODUCTION case will be executed and the transition from the state INTRODUCTION to GAME_START
   *    will take place.
   * e) In the PLAYING case,  
   * 
   */
  public void userProcessInput()
  {
    // This block (which was added in tutorial 4) will be removed since it will no longer be needed in this tutorial.
    //    if (playerResetRequest)
    //    {
    //      playerResetRequest = false;
    //      resetPlayer();
    //    }

    switch (this.state)
    {
      case INTRODUCTION:

        if (startGameRequest)
        {
          startGameRequest = false;
          stateGameStartTime = System.currentTimeMillis();
          state = GameEngineConstants.GameState.GAME_START;
        }

        break;

      case PLAYING:

        double deltaX = 0;
        double deltaY = 0;

        // This will be moved into the PLAYING state.
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

        break;

      default:
    }
  }

  /*
   * STEP 6 - Adding key handlers for different states (Part 1)
   * 
   * Key handlers need to be added for the different game states. For this game the user will need to enter
   * different input for the INTRODUCTION state and the PLAYING state. For the INTRODUCTION state the only input
   * the user can provide is the Space Bar to begin the game. When this happens the game state will transition
   * from INTRODUCTION to GAME_START to PLAYING. When the game state is PLAYING the only input the user can enter
   * are the arrow keys to move the player's entity. To be able to handle different types of input for different
   * game states, a switch statement will be used.  
   *
   * a) Add a switch statement based on the state variable, which is the enumerated type GameEngineConstants.GameState.
   * b) Add cases to the switch statement to handle the game states INTRODUCTION and PLAYING.
   * c) In the INTRODUCTION case statement, add a conditional statement that will check when the space bar is pressed.
   *    Note, this method gameKeyPressed() is called from within the method keyPressed(), which is part of the
   *    KeyListener interface, which is implemented by the Applet class AvoidTheSquareTutorial5. When the Applet
   *    detects a key press the method keyPressed() is called, which then calls this method, gameKeyPressed(). The
   *    parameter keyCode is the passed in from the event handler that contains the ASCII code of the pressed key.
   *    The value stored in keyCode will be compared to the constant for the space bar (KeyEvent.VK_SPACE). When the
   *    space bar is pressed the block of the conditional statement will be executed.
   * d) Inside of the conditional statement block, set the flag stateGameRequest to true. This flag will be checked
   *    in the method userProcessInput() to handle the user's request to start the game. 
   * e) In the PLAYING case statement, add the logic to handle the arrow key movement. Note that this logic was added
   *    to this method back in tutorial 2. This logic also consisted of a case statement that operated on the keyCode
   *    parameter. Simply copy this switch statement block from the method gameKeyPressed() in tutorial 2 and paste it
   *    in the body of the PLAYING case below. 
   *
   */
  public void gameKeyPressed(int keyCode)
  {
    // Step 6a - Switch statement to handle user input for different game states.
    switch (this.state)
    {
      // Step 6b - Adding the INTRODUCTION case 
      case INTRODUCTION:

        // Step 6c - Adding the conditional statement to handle the users request to begin the game.
        if (keyCode == KeyEvent.VK_SPACE)
        {
          // Step 6d - Setting the start game request flag to true.
          startGameRequest = true;
        }

        break;
      
      // Step 6b - Adding the PLAYING case 
      case PLAYING:

        // Step 6e - Adding the logic to handle the arrow keys. Note, this code was copied from the method 
        //           gameKeyPressed() from tutorial 2. 
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

        break;

      default:
    }
  }

  /*
   * STEP 7 - Adding key handlers for different states (Part 2)
   *
   * a) Add a switch statement based on the state variable as you did in STEP 6.
   * b) Add the case to the switch statement to handle the game state PLAYING. Note that there is no key handler
   *    for the game state INTRODUCTION. This is because when the startGameRequest flag is set to true in the 
   *    method gameKeyPressed(), logic for this flag is processed in the method userProcessInput(), which clears
   *    this flag. 
   * c) In the PLAYING case statement, add the logic to handle the arrow key movement. Note that this logic was added
   *    to this method back in tutorial 2. This logic also consisted of a case statement that operated on the keyCode
   *    parameter. Simply copy this switch statement block from the method gameKeyReleased() in tutorial 2 and paste it
   *    in the body of the PLAYING case below. Make sure the case statement only handles the arrow keys. In tutorial 2, 
   *    there was the case where the r key was also handled in the gameKeyReleased() method, but this handler can be
   *    removed. 
   * 
   */
  public void gameKeyReleased(int keyCode)
  {
    // Step 6a - Switch statement to handle user input for different game states.
    switch (this.state)
    {      
      // Step 6b - Adding the PLAYING case 
      case PLAYING:

        // Step 6c - Adding the logic to handle the arrow keys. Note, this code was copied from the method 
        //           gameKeyReleased() from tutorial 2. 
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

        // This code (from tutorial 2) can be removed for this tutorial
        //          case KeyEvent.VK_R:
        //            playerResetRequest = true;
        //            break;
        }

        break;

      default:
    }
  }

  public void gameKeyTyped(int keyCode)
  {}

  /* (NOTE: This step might need to go before the step that sets this flag in the userGamePreUpdate())
   * STEP 11 - Setting the Player Dead Flag
   * 
   * Why do we have a seperate flag indicating when the player is dead when there is the alive flag in the 
   * entity class (accessible via the isAlive() method)? Well, the alive flag in the Entity class can be used 
   * to transition to the GAMEOVER state. However, when we give the player multiple lives (three lives in most
   * classic arcade games), there needs to be a way to differentiate when the player has just been killed (e.g.,
   * clearing the alive flag when the player entity collides with an enemy entity) and when the player is
   * actually dead (e.g., no more lives). In this version of the game the player only has one life. In later 
   * tutorials that expand upon this game, the player will be given three lives when the game starts. In this
   * situation the playerDead flag will only be set to true when there are no more player lives, which will then
   * be used to transition to the GAMEOVER state. Also note that additional logic will be added to this method in
   * future tutorials that when the player is killed due to a collision, the playerDead flag will only be set when
   * the player has no more lives. 
   */
  @Override
  public void userHandleEntityCollision(Entity entity1, Entity entity2)
  {
    System.out.println("A collision occured!");

    if (entity1.getEntityType() == GameEngineConstants.EntityTypes.PLAYER)
    {
      if (entity2.getEntityType() == GameEngineConstants.EntityTypes.ENEMY)
      {
        entity1.kill();
        playerDead = true;
      }
    }
  }

  /*
   * (NOTE: THIS STEP IS NOT COMPLETED YET) 
   * STEP 10 - Handling the updating of each entity
   * 
   * The condition for checking if the current entity is alive is the same as before. If the entity
   * is not alive, simply return to the caller.
   *
   * a) Add a new switch statement based on the entity type.
   * b) Add a case for the entity type ENEMY. If the current entity type is an enemy, the entity
   *    should bounce off the side of the screen.
   * c) For the default case, which in this case is the player, should wrap around to the other side
   *    of the screen.
   */
  @Override
  public void userGameUpdateEntity(Entity entity)
  {
    if (!entity.isAlive())
    {
      return;
    }

    switch(entity.getEntityType())
    {
      case ENEMY:
        
        GameUtility.collideWalls(entity, screenWidth, screenHeight);
        break;
        
      default:
        GameUtility.warp(entity, screenWidth, screenHeight);
    }
  }

  /*
   * (NOTE: THIS STEP IS NOT COMPLETED YET) 
   * STEP 9: Adding switch Statement to Handle Different Game States
   * 
   * In this step, the following states will be added INTRODUCTION, GAME_START, PLAYING, PAUSED and GAMEOVER.
   * The following transitions will take place between the different states:
   *
   *     Current State    Next State
   *     INTRODUCTION     GAME_START
   *     GAME_START       PLAYING
   *     PLAYING          PAUSED
   *     PLAYING          GAMEOVER
   *     PAUSED           PLAYING
   *     GAMEOVER         INTRODUCTION
   * 
   * Note that when the game starts the starting state will be INTRODUCTION.
   * 
   */
  @Override
  public void userGamePreUpdate()
  {
    switch (this.state)
    {
      case INTRODUCTION:

        break;

      case GAME_START:

        // Stay in the game start state for a brief period of time 
        if (System.currentTimeMillis() > (GAME_START_INTERVAL + stateGameStartTime))
        {
          resetPlayer();
          addEnemiesToGame();
          this.state = GameEngineConstants.GameState.PLAYING;
        }

        break;

      case PLAYING:

        if (playerDead)
        {
          stateGameOverTime = System.currentTimeMillis();
          state = GameEngineConstants.GameState.GAMEOVER;
        }

        break;
        
      case GAMEOVER:

        if (System.currentTimeMillis() > (GAME_OVER_INTERVAL + stateGameOverTime))
        {
          getEnemies().clear();
          this.state = GameEngineConstants.GameState.INTRODUCTION;
        }
        
        break;

      default:
    }
  }

  /*
   * STEP 4: Adding the display logic (Part 1)
   * 
   * Before adding the display logic, there are two methods that are responsible for displaying items to 
   * the screen. They are userGamePreDraw() and userGamePostDraw(). Both methods are called from the GameEngine
   * method gameDraw(). The method userGamePreDraw() is called before the entity lists are drawn to the screen
   * and the userGamePostDraw() is called after the entity lists are drawn to the screen. In the game engine, the
   * most recent item drawn to the screen is displayed on top. Both of these methods exist to give you the choice
   * of the order you want your items displayed in relation to the entities that are drawn. For instance, certain 
   * messages (such as "Game Paused" or "Game Over") may want to be displayed without the entities (or other items)
   * obstructing these messages. Therefore, the logic to display these messages will go in the method 
   * userGamePostDraw().
   * 
   * a) The code that was added to this method in tutorial 4 can be removed since it will no longer be used.
   * b) Add a switch statement based on the enumerated type GameEngineConstants.GameState. This switch 
   *    statement will handle the display logic for different game states.
   * c) Add cases to the body of the switch statement to handle the display logic for the game states 
   *    INTRODUCTION and GAME_START.
   * d) In the body of the case statement for game state INTRODUCTION, add logic to display the string 
   *    INTRODUCTION_MSG1 defined in step 1 of this tutorial. The string should be centered on the screen
   *    horizontal and appear near the top of the screen (Recall that displaying strings were first introduced 
   *    in tutorial 4). In order to display this string, the font first needs to be set using the g.setFont() 
   *    method and passing it the font variable INTRODUCTION_FONT_MSG1. Next the bounding box of the string 
   *    INTRODUCTION_MSG1 needs to be determined by calling the method g.getFontMetrics(). Next, set the color 
   *    of the message using the g.setColor() method. Lastly, call the method g.drawString() on the string 
   *    INTRODUCTION_MSG1. Since the string needs to be centered horizontally, the following logic to center the
   *    string horizontally should be provided for the x position of the drawString() method.   
   *
   *    (screenWidth - boundingRectangleIntroScreenMsg1.getWidth()) / 2
   *    
   *    Since the string needs to be displayed near the top of the screen, we can simply multiply the screen height
   *    by 0.20 as pass the result for the y coordinate of the drawString() method.
   *    
   * e) In the body of the same case statement for game state INTRODUCTION, add the logic to display the message
   *    INTRODUCTION_MSG2. Remember to set the font using the corresponding font object for this message,
   *    INTRODUCTION_FONT_MSG2. Also remember to get the bounding box for this message and use it to center the
   *    message horizontally again, but vertically set the position to 0.40 of the screen height.
   * f) In the body of the case statement for the GAME_START state, add the display logic to display the message 
   *    GAME_START_MSG. Again set the corresponding font GAME_START_FONT_MSG, determine the bounding box for the
   *    message, set the color and display the message so that it is centered both horizontally and vertically using
   *    the method g.drawString() on this string, GAME_START_MSG.
   * 
   * Note that since there are no other items (both entities and string messages) to be displayed for the states
   * INTRODUCTION and GAME_START, it does no matter which method the display logic for these states is placed (either
   * userGamePreDraw() or userGamePostDraw()).
   */
  @Override
  public void userGamePreDraw(Graphics2D g) // Changed from graphics
  {
    // Step 4a - This code (added in tutorial 4) can be removed since it will no longer be needed.  
    //String instructionMsg = "Press 'r' to Reset the Player Entity after a Collision.";

    // Step 4a - This code (also added in tutorial 4) can be removed since it will no longer be needed.
    //    g.setFont(new Font("Courier", Font.PLAIN, 18));
    //    g.setColor(Color.WHITE);
    //    Rectangle2D boundsExitGame = g.getFontMetrics().getStringBounds(instructionMsg, g);
    //    g.drawString(instructionMsg, (int) ((screenWidth - boundsExitGame.getWidth()) / 2), (int) (screenHeight * 0.20));    
    
    // Step 4b - Switch statement to handle the display logic for different game states.
    switch (this.state)
    {
      // Step 4c - Adding case to handle the display logic for the game state INTRODUCTION.
      case INTRODUCTION:

        // Step 4d - Display the first message for the introduction screen.
        g.setFont(INTRODUCTION_FONT_MSG1);
        Rectangle2D boundingRectangleIntroScreenMsg1 = g.getFontMetrics().getStringBounds(INTRODUCTION_MSG1, g);
        g.setColor(Color.WHITE);
        g.drawString(INTRODUCTION_MSG1, (int) ((screenWidth - boundingRectangleIntroScreenMsg1.getWidth()) / 2), (int) ((screenHeight * 0.20)));

        // Step 4e - Display the second message for the introduction screen.
        g.setFont(INTRODUCTION_FONT_MSG2);
        Rectangle2D boundingRectangleIntroScreenMsg2 = g.getFontMetrics().getStringBounds(INTRODUCTION_MSG2, g);
        g.setColor(Color.YELLOW);
        g.drawString(INTRODUCTION_MSG2, (int) ((screenWidth - boundingRectangleIntroScreenMsg2.getWidth()) / 2), (int) ((screenHeight * 0.40)));
        break;

      // Step 4c - Adding case to handle the display logic for the game state GAME_START.
      case GAME_START:

        // Step 4f - Display the message to the screen when the game is about to start.
        g.setFont(GAME_START_FONT_MSG);
        Rectangle2D boundingRectangleGameStartScreenMsg = g.getFontMetrics().getStringBounds(GAME_START_MSG, g);
        g.setColor(Color.WHITE);
        g.drawString(GAME_START_MSG, (int) ((screenWidth - boundingRectangleGameStartScreenMsg.getWidth()) / 2), (int) ((screenHeight - boundingRectangleGameStartScreenMsg.getHeight()) / 2));
        break;

      default:
    }
  }

  /*
   * STEP 5: Adding the display logic (Part 2)
   * 
   * In this step the display logic for the GAMEOVER state will be added.
   * 
   * a) As done in step 4, add a switch statement that operates on the enumerated type GameEngineConstants.GameState.
   * b) Add a case to the body of the switch statement to handle the display logic for the game state GAMEOVER.
   * c) In the body of the case statement for the GAMEOVER state, add the display logic to display the message 
   *    GAME_OVER_MSG. Again set the corresponding font GAME_OVER_FONT_MSG, determine the bounding box for the
   *    message, set the color and display the message so that it is centered both horizontally and vertically using
   *    the method g.drawString() on this string, GAME_OVER_MSG, the same as the display logic previously add in step 4f.
   *    
   * NOTE: Since the game over message should not be obstructed by any other entities or items drawn to the screen,
   *       this logic exists in this method userGamePostDraw().
   */
  @Override
  public void userGamePostDraw(Graphics2D g) // Changed from graphics
  {
    // Step 4a - Switch statement to handle the display logic for different game states.
    switch (this.state)
    {
      
      case GAMEOVER:

        // When the player has no more lives, display the game over message
        g.setFont(GAME_OVER_FONT_MSG);
        Rectangle2D boundingRectangleGameOverScreenMsg = g.getFontMetrics().getStringBounds(GAME_OVER_MSG, g);
        g.setColor(Color.WHITE);
        g.drawString(GAME_OVER_MSG, (int) ((screenWidth - boundingRectangleGameOverScreenMsg.getWidth()) / 2), (int) ((screenHeight - boundingRectangleGameOverScreenMsg.getHeight()) / 2));
        break;

      default:
    }
  }
}
