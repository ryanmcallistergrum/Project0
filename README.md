# Project0
## Project Description
Project0 combines Scala with MySQL to create a persistent farming simulator called Farm SIM! The goal of the game is to pay off debt by the end of the in-game year.

## Technologies Used
- Java - version 11.0.10
- Scala - version 2.11.12
- MySQL Community Edition - version 8.0
- Git + GitHub

## Features
- Persistent game state
- Separate save files
- Multiple separate games can play simultaneously
- Colorful command-line interface
- 12 crops, 3 each season, with multiple growth stages of various lengths
- 4 seasons with 30 days per season
- 30 farm plots, with 29 requiring some sort of clearing before use
- Watering Can, Hammer, Hoe, and Axe
- Pleasant descriptions

## Getting Started
- (Note, these instructions only support Windows 10 and above)
- First, download and install Git
    - Navigate to https://git-scm.com/download/win and install
- Run the following Git command to create a copy of the project repository using either Command Prompt or PowerShell:
    - git clone https://github.com/ryanmcallistergrum/Project1.git
- Install Java
    - Navigate to https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html and install
- Install MySQL 8.0 Community Edition
  - Navigate to https://dev.mysql.com/downloads/windows/installer/8.0.html and install
  - Install the Developer version of the server when asked
  - Create DB Admin user farmSim (password farmSim) when setting root password
- Install Database
  - Once MySQL is installed, open up MySQL Workbench and connect as root
  - Run the "Project0 MySQL FarmSim Schema Backup.sql" file inside the root of the repository once connected
- Start FarmSim
  - Open up a command prompt, navigate into the repository, and start the "FarmSim v0.1.jar" by using the command "java -jar "FarmSim v0.1.jar""

## Usage
- From the main menu, either start a new game or load an existing game
  - ![Main Menu](/images/Main%20Menu.png?raw=true)
- After loading or starting a new game, the Farm Menu appears, which provides date info, describes the day, provides a summarized report on crops, and offers a list of actions
  - ![Farm Menu](/images/Farm%20Menu.png?raw=true)
- 