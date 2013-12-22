package tutorial1.avoidthesquare.creatinganemptygame;

import java.awt.Graphics2D;

import game.framework.GameEngine;
import game.framework.entities.Entity;
import game.framework.interfaces.IRender;

/**
 * Tutorial 1: Creating an Empty Game Project
 * 
 * The steps for this tutorial are contained in the comments of this and the other project source files.
 * 
 *   This source file contains tutorial steps 1 and 2.
 *   
 */

/*
* STEP 1: Extending the GameEngine class 
* 
* a) Create the class MovingSquare and have it extend the base class GameEngine
* b) Be sure to add the following import command:
* 
*    import game.framework.GameEngine;
*
* c) Since the class GameEngine is being sub-classed, the abstract methods in this class will need to be implemented in the class MovingSquare
*
*  public abstract void userGameInit();
*  public abstract void userGameStart();
*  public abstract void userGamePreUpdate();
*  public abstract void userGameUpdateEntity(Entity entity);
*  public abstract void userHandleEntityCollision(Entity entity1, Entity entity2);
*  public abstract void userProcessInput();
*  public abstract void userGamePreDraw(Graphics2D g);
*  public abstract void userGamePostDraw(Graphics2D g);
*  public abstract void userGameShutdown();
*  public abstract void gameKeyPressed(int keyCode);
*  public abstract void gameKeyReleased(int keyCode);
*  public abstract void gameKeyTyped(int keyCode);
*
* d) Be sure to add the following import commands since some of the implemented methods require these classes:
* 
*    import game.framework.entities.Entity;
*    import java.awt.Graphics2D;
*    
*/
public class AvoidTheSquare extends GameEngine
{
  /*
   * STEP 2: Adding the constructor
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

  @Override
  public void userGameInit()
  {}

  @Override
  public void userGameStart()
  {}

  @Override
  public void userGamePreUpdate()
  {}

  @Override
  public void userGameUpdateEntity(Entity entity)
  {}

  @Override
  public void userHandleEntityCollision(Entity entity1, Entity entity2)
  {}

  @Override
  public void userProcessInput()
  {}

  @Override
  public void userGamePreDraw(Graphics2D g)
  {}

  @Override
  public void userGamePostDraw(Graphics2D g)
  {}

  @Override
  public void userGameShutdown()
  {}

  @Override
  public void gameKeyPressed(int keyCode)
  {}

  @Override
  public void gameKeyReleased(int keyCode)
  {}

  @Override
  public void gameKeyTyped(int keyCode)
  {}
}
