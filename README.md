# Snake ML

A __snake__ game which can be played by using a neural network.  
Created as FFHS project in collaboration with [@ramonator00](https://github.com/ramonator00) and SB.

## Table of Contents
1. [About the game](#about-the-game)  
2. [Implemented AI](#implemented-ai)  
3. [Project structure](#project-structure)  
4. [How to get it](#how-to-get-it)  
	4.1 [How to import it to Intellij IDE](#how-to-import-it-to-intellij-ide)   
	4.2 [How to import it to Eclipse IDE](#how-to-import-it-to-eclipse-ide)   
5. [How to run it](#how-to-run-it)  
	5.1 [Within the IDE](#within-the-ide)  
	5.2 [From a Jar file](#from-a-jar-file)  
		5.2.1 [Build Jar in Intellij IDE](#build-jar-in-intellij-ide)    
		5.2.2 [Build Jar in Eclipse IDE](#build-jar-in-eclipse-ide)  
		5.2.3 [Execute Jar](#execute-jar)  
  
## About the game
![screenshot of snake](https://github.com/lpapailiou/snake/blob/master/src/main/resources/img/screenshot_application.png)
  
Following modes are available:  
- arcade mode (classic manual gameplay)
- neural network mode (configure a neural network and see what happens)
- demo mode (trained neural network)
  
Additional features:  
- extended statistics, available by browser via webserver
- various themes available
  
Available key commands:  
| Key          	| Command                                                                            	|
|--------------	|------------------------------------------------------------------------------------	|
| UP, W        	| set direction of next move to 'up'<br>(manual mode only)                           	|
| RIGHT, D     	| set direction of next move to 'right'<br>(manual mode only)                        	|
| DOWN, S      	| set direction of next move to 'down'<br>(manual mode only)                         	|
| LEFT, A      	| set direction of next move to 'left'<br>(manual mode only)                         	|
| SPACE, ENTER 	| start or stop a game                                                               	|
| V            	| 'verbose': activate/deactivate real time statistics<br>(neural network mode only)  	|
| P            	| 'position': changes position of real time statistics<br>(neural network mode only) 	|  
  
## Implemented AI  
The neural network was implemented by using a genetic algorithm.  

## Project structure
  
* ``ai``                     here, all ai related code is in  
* ``game``                 the basic game loic  
* ``main``                 this package contains the main method (in ``Main.java``), configuration and game mode handling    
* ``ui``                         all ui related code  
* ``webserver``         all webserver related code  
  
## How to get it
  
Clone the repository with:

    git clone https://github.com/lpapailiou/snakeML your-target-path

Originally, the project was developed with the Intellij IDE. It also runs with Eclipse.

### How to import it to Intellij IDE
1. Go to ``File > New``
2. Pick ``Maven > Project from Existing Sources...``
3. Now, navigate to the directory you cloned it to
4. Select the ``pom.xml`` file and click ``OK``
5. The project will be opened and build

### How to import it to Eclipse IDE
1. Go to ``File > Import``
2. Pick ``Maven > Existing Maven Project``
3. Now, navigate to the directory you cloned it to
4. Pick the root directory ``snakeML`` and click ``Finish``
5. The project will be opened and build

## How to run it

### Within the IDE
You can directly run it within the IDE.

In case you experience weird UI behavior, it may be a DPI scaling issue known to occur with Windows 10 notebooks.
To fix it, do following steps:
1. Find the ``java.exe`` the application is running with (check Task Manager)
2. Rightclick on the ``java.exe`` and go to ``Properties``
3. Open the ``Compability`` tab
4. Check ``Override high DPI scaling behavior``
5. Choose ``System`` for ``Scaling performed by:``
6. Run the game again

### From a Jar file
You can download the Jar file directly from the [latest release](https://github.com/lpapailiou/SnakeML/releases/latest). Alternatively, you can build it yourself.

#### Build Jar in Intellij IDE 
1. Go to ``File > Project Structure...``
2. Go to the ``Artifacts`` tab and add a new ``Jar > From module with dependencies`` entry
3. Select the main class ``Main`` (here, the main class is in)
4. Click ``Ok`` twice
5. Go to ``Build > Build Artifacts...``
6. Select ``Build``
7. The Jar file is now added to the ``target`` folder within the project structure

#### Build Jar in Eclipse IDE
1. Right click on the project
2. Choose ``Export``
3. Go to ``Java > Runnable JAR file``
4. Click ``Next``
5. Launch configuration: choose ``Main`` (here, the main class is in)
6. Export destination: the place you want to save the Jar
7. Choose ``Extract required libraries into generated JAR``
8. Click ``Finish`` to start the Jar generation

#### Execute Jar
Double click on the Jar file directly. 
If nothing happens, you might need to add Java to your PATH variable.

Alternatively, you can start the Jar file from the console with:

    java -jar snakeML.jar
    
Please make sure you execute it from the correct directory. The naming of the Jar file may vary.
  
