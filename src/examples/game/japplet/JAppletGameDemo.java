package examples.game.japplet;

import examples.game.GameDemo;
import game.framework.interfaces.IRender;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class JAppletGameDemo extends JApplet
{
  private GameDemo     gameDemo;
  private GameScreen   gameScreen;

  public JAppletGameDemo()
  {
    // Setup the JFrame and panel used in this game
    gameScreen = new GameScreen();
    gameScreen.setPreferredSize(gameScreen.getPreferredSize());

    this.setContentPane(gameScreen);
    this.setVisible(true);

    gameDemo = new GameDemo(gameScreen);
    gameDemo.gameInit();
    gameDemo.gameStart();
  }

  @Override
  public void init()
  {
    // Use the event dispatch thread to build the UI for thread-safety.
    SwingUtilities.invokeLater(new Runnable()
    {
      @Override
      public void run()
      {
        new JAppletGameDemo();
      }
    });
  }

  /////////////////////////////////////////////////////////////////////////////
  //   ___                          ____ _               
  //  |_ _|_ __  _ __   ___ _ __   / ___| | __ _ ___ ___ 
  //   | || '_ \| '_ \ / _ \ '__| | |   | |/ _` / __/ __|
  //   | || | | | | | |  __/ |    | |___| | (_| \__ \__ \
  //  |___|_| |_|_| |_|\___|_|     \____|_|\__,_|___/___/
  //                                                     
  //   ____                                     _   _             
  //  |  _ \ ___ _ __  _ __ ___  ___  ___ _ __ | |_(_)_ __   __ _ 
  //  | |_) / _ \ '_ \| '__/ _ \/ __|/ _ \ '_ \| __| | '_ \ / _` |
  //  |  _ <  __/ |_) | | |  __/\__ \  __/ | | | |_| | | | | (_| |
  //  |_| \_\___| .__/|_|  \___||___/\___|_| |_|\__|_|_| |_|\__, |
  //            |_|                                         |___/ 
  //       _ ____                  _ 
  //      | |  _ \ __ _ _ __   ___| |
  //   _  | | |_) / _` | '_ \ / _ \ |
  //  | |_| |  __/ (_| | | | |  __/ |
  //   \___/|_|   \__,_|_| |_|\___|_|
  //                                 
  /////////////////////////////////////////////////////////////////////////////

  //Custom drawing panel, written as an inner class.
  public class GameScreen extends JPanel implements KeyListener, IRender
  {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // Constructor
    public GameScreen()
    {
      setFocusable(true);  // so that can receive key-events
      requestFocus();
      addKeyListener(this);
    }

    // Override paintComponent to do custom drawing.
    // Called back by repaint().
    @Override
    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);   // paint background

      // Draw the game objects
      gameDemo.gameDraw((Graphics2D) g);

      Toolkit.getDefaultToolkit().sync();
    }

    /////////////////////////////////////////////////////////////////////////////
    //   _  __            _____                 _   
    //  | |/ /___ _   _  | ____|_   _____ _ __ | |_ 
    //  | ' // _ \ | | | |  _| \ \ / / _ \ '_ \| __|
    //  | . \  __/ |_| | | |___ \ V /  __/ | | | |_ 
    //  |_|\_\___|\__, | |_____| \_/ \___|_| |_|\__|
    //            |___/                             
    //   _   _                 _ _               
    //  | | | | __ _ _ __   __| | | ___ _ __ ___ 
    //  | |_| |/ _` | '_ \ / _` | |/ _ \ '__/ __|
    //  |  _  | (_| | | | | (_| | |  __/ |  \__ \
    //  |_| |_|\__,_|_| |_|\__,_|_|\___|_|  |___/
    //                                             
    /////////////////////////////////////////////////////////////////////////////

    // KeyEvent handlers
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
      gameDemo.gameKeyTyped(e.getKeyCode());
    }

    @Override
    public void renderScreen()
    {
      repaint();
    }
  }
}
