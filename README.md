GameFrameworkTutorials
======================

A set of short tutorials showing how to use the GameFramework

When you clone this project, make sure you also clone the *[GameFramework](https://github.com/garyfredgiger/GameFramework.git)* project in this same repo. The GameFramework project is required for these tutorials and examples. Import both projects into Eclipse and before building the project, make sure you add the GameFramework project to this project under the menu options as described below.

<ol>
<li>Right click on the project and select the option Build Path -> Configure Build Path</li>
<li>Click on the Projects tab and add the GameFramework project, then click the OK button.</li>
</ol>

Note: If you are new to Eclipse and need instructions on how to install and set it up, refer to this link *[here](http://wiki.eclipse.org/Eclipse/Installation)*.

You will find the following tutorials in this project. Note that the steps for each tutorial are included in each tutorial's source files. When you review the source files for each tutorial, you can review each step, which provides instructions and in some cases an explanation of the respective code.

<dl>

<dt>tutorial1.avoidthesquare.creatinganemptygame</dt>
<dd>This tutorial shows how to create an empty game project using the game framework.</dd>

<dt>tutorial2.avoidthesquare.addingtheplayer</dt>
<dd>This tutorial shows how to add a player entity that can be moved around on the screen using the arrow keys.</dd>

<dt>tutorial3.avoidthesquare.addingenemies</dt>
<dd>This tutorial shows how to create and add an enemy entities to the game and also discusses a bit about collisions.</dd>

</dl>

NOTE: More Tutorials to come as my time permits. Future tutorials will include handling collisions, adding a scoring system and adding game states.

You will find the following examples below.

<dl>
<dt>examples.game</dt>
<dd>Contains a sample game that extends the game framework's game engine</dd>

<dt>examples.game.applet</dt>
<dd>Shows how to deploy the game as an Applet</dd>

<dt>examples.game.japplet</dt>
<dd>Shows how to deploy the game as a JApplet</dd>

<dt>examples.game.jframe</dt>
<dd>Shows how to deploy the game as an JFrame</dd>
</dl>



NOTE: I included the eclipse project files (.project and .classpath) in this project so you can simply import this project without having to create a new project from your cloned copy of the code. Some suggest it is not a good idea to include project specific files, but I thought I would be a rebel and do it anyway.

This game was developed using Eclipse Kepler Release (Build id: 20130614-0229) with Java 1.6. 

