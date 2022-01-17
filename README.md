Cell Society
====

This project implements a cellular automata simulator.

Names:

* Cate Schick
* Michelle Zhang
* Kyle White
* Samuel Li

### Timeline

Start Date: Sunday, Oct 17th

Finish Date: Monday, Nov 1st

Hours Spent: 150

### Primary Roles

* Cate Schick
    * Model/Controller: Worked mainly on the parser classes

* Samuel Li
    * Model: Worked mainly on the model classes

* Michelle Zhang
    * View/Controller: Worked mainly on the view and controller classes

* Kyle White
    * View/Controller: Worked mainly on the view and controller classes

### Resources Used

* For JavaFX file dragging and dropping
  functionality: https://stackoverflow.com/questions/32534113/javafx-drag-and-drop-a-file-into-a-program

### Running the Program

Main class: Main.java

Data files needed: .sim file for simulation configuration and .csv file for grid seed generation.

Features implemented:

* Able to run all 5 simulations: Game of Life, Fire, Segregation, Wator, and Percolation.
* Able to input initial states of the grid via csv file specified in sim file
* Able to configure simulation via sim file. We can change:
    * Simulation type
    * Simulation parameters
    * Simulation accompanying CSV
    * Cell state colors
* Able to see the simulation's "About" information
* Change animation speed via slider
* Change cell state by clicking on it
* Pause animation and step through
* View histogram
* Save the current state of the simulation to both sim and csv files
* Run 2 simulations side by side

### Notes/Assumptions

Assumptions or Simplifications:
There is no error checking in the parser therefore we assume the sim and csv files are accurate. We also have to assume all the values given are valued. However, the parser does check for valid colors.

Interesting data files:
* InvalidColors.sim/InvalidColors2.sim - These two files check the color checking capabilities of the parser.
* data/saved - All the files that are downloaded from the UI are saved to the saved directory

Known Bugs:
* The side by side feature does not work right. A lot of the methods in the back end are static therefore the view is not able to make two instances of the model classes for the side by side feature.

Noteworthy Features:
* Drag File: The view allows the user to drag a file as a method of selecting it
* Color Checking: The parser checks for valid colors inputted in the sim files.

### Impressions
This project taught us the importance of the MVC structure. Adding a dedicated controller class was probably the biggest leap forward for our team. Before adding the controller, we struggled with integrating the back end with the front end. Also, we learned how to use Intellij's Code with me feature as a way to make pair/group programming easier. Our group used this feature a lot which allowed us to minimize the number of merge conflicts we get.
