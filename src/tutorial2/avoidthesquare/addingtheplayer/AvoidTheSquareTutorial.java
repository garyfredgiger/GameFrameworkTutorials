package tutorial2.avoidthesquare.addingtheplayer;

import game.framework.interfaces.IRender;
import game.framework.utilities.GameEngineConstants;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * This class was created in Tutorial 1
 * 
 * See the steps in the source file AvoidTheSquareTutorial.java in the package named
 * tutorial1.avoidthesquare.creatinganemptygame to learn how this class was created.
 * 
 */
public class AvoidTheSquareTutorial extends Applet implements KeyListener
{
  private Image             image;
  private Graphics          doubleBuffer;

  private AvoidTheSquare    avoidTheSquare;
  private Renderer          renderer;

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

  @Override
  public void update(Graphics g)
  {
    image = createImage(GameEngineConstants.DEFAULT_CANVAS_WIDTH, GameEngineConstants.DEFAULT_CANVAS_HEIGHT);
    doubleBuffer = image.getGraphics();

    paint(doubleBuffer);
    g.drawImage(image, 0, 0, this);
  }
  
  @Override
  public void paint(Graphics g)
  {
    avoidTheSquare.gameDraw((Graphics2D) g);
  }

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

  public class Renderer implements IRender
  {
    @Override
    public void renderScreen()
    {
      repaint();
    }
  }
}
