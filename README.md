# Snake ML

A __snake__ game which can be played by using a neural network.   
Created as FFHS project in fall 2020 in collaboration with [@ramonator00](https://github.com/ramonator00) and [@SandroBuerki](https://github.com/SandroBuerki).
  
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
![screenshot of snake](https://raw.githubusercontent.com/lpapailiou/SnakeML/master/src/main/resources/img/screenshot_application.png)
  
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
* ``ui``                            all ui related code  
* ``webserver``         all webserver related code  
  
## How to get it
  
Clone the repository with:

    git clone https://github.com/lpapailiou/snakeML your-target-path

For further help, click [here](https://gist.github.com/lpapailiou/d4d63338ccb1413363970ac571aa71c9).
  
