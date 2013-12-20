package examples.game.applet;

import examples.game.GameDemo;
import game.framework.interfaces.IRender;
import game.framework.utilities.GameEngineConstants;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AppletGameDemo extends Applet implements KeyListener
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Image             image;
  private Graphics          doubleBuffer;

  private GameDemo          gameDemo;
  private Renderer          renderer;

  @Override
  public void init()
  {
    System.out.println("init() Called.");

    // Start the input listeners
    addKeyListener(this);
    requestFocus();

    renderer = new Renderer();
    gameDemo = new GameDemo(renderer);
    gameDemo.gameInit();
    gameDemo.gameStart();
  }

  public void start()
  {
    System.out.println("start() Called.");
  }

  public void paint(Graphics g)
  {
    // Draw the game objects
    gameDemo.gameDraw((Graphics2D) g);
  }

  public void update(Graphics g)
  {
    //System.out.println("update() Called.");
    image = createImage(GameEngineConstants.DEFAULT_CANVAS_WIDTH, GameEngineConstants.DEFAULT_CANVAS_HEIGHT);
    doubleBuffer = image.getGraphics();

    paint(doubleBuffer);
    g.drawImage(image, 0, 0, this);
  }

  public void stop()
  {
    System.out.println("stop() Called.");
  }

  public void destroy()
  {
    System.out.println("destroy() Called.");
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    gameDemo.gameKeyPressed(e.getKeyCode());
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    // Process Debug key    
    if (e.getKeyCode() == KeyEvent.VK_BACK_QUOTE && e.getModifiersEx() == KeyEvent.SHIFT_DOWN_MASK)
    {
      gameDemo.displayDebugInfo = !gameDemo.displayDebugInfo;

      if (gameDemo.displayDebugInfo)
      {
        System.out.println("Debugging Enabled.");
      }
      else
      {
        System.out.println("Debugging Disabled.");
      }
    }

    gameDemo.gameKeyReleased(e.getKeyCode());
  }

  @Override
  public void keyTyped(KeyEvent e)
  {

  }

  public class Renderer implements IRender
  {

    @Override
    public void renderScreen()
    {
      repaint();
    }
  }
}
