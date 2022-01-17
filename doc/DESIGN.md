# Cell Society Design Final

### Names

* Cate Schick
* Michelle Zhang
* Kyle White
* Samuel Li

## Team Roles and Responsibilities

* Cate Schick
    * Model/Controller: Worked mainly on the parser classes

* Samuel Li
    * Model: Worked mainly on the model classes

* Michelle Zhang
    * View/Controller: Worked mainly on the view and controller classes

* Kyle White
    * View/Controller: Worked mainly on the view and controller classes

## Design goals

#### What Features are Easy to Add

Our design uses abstraction hierarchy to encapsulate each feature and make it easier to add new
ones. For example, all the game models are in an abstract hierarchy so if a new game was added all
we need to do was make a subclass that implemented the specific rules for that game. It also uses a
grid class to handle interacting with the grid data structure. This makes it easy to add new edge
policies and neighbor arrangements.

## High-level Design

#### Core Classes

* View - There are multiple views that interact with the user
    * FileInputView - Allows the user to click on or drag a file
    * GridView - Allows the user to see the animation of the game they uploaded
* Controller - Passes information between the front end and the back end
* Parser - Parses sim and csv files
* Game - Models the various applications

## Assumptions that Affect the Design

#### Features Affected by Assumptions

There is no error checking in the parser therefore we have to assume the sim and csv files are
valid. Also, we have to assume that all the values given are valid as well.

## Significant differences from Original Plan

The only significant difference between what we initially planned and what we have currently is the
addition of the controller class. In the middle of the project we decided that we needed a dedicated
controller class that would pass information between the front end and the back end. Before making
the controller class, the view interacted directly with the parser and model classes.

## New Features HowTo

#### Easy to Add Features

* New Cell Shapes
* New Games
* New Edge Policies
* New Arrangements of Neighbors
* New Simulation Views with About, New File, and Save File buttons

#### Other Features not yet Done

* We were not able to implement making the cells show up as images
    * We would create a properties file with image file names, and read from that properties file to
      create ImageViews. Then we would just fill the grid cell with the ImageView.
* Changing settings/parameters through the UI
    * We would create methods in the controller that would get and set (with restrictions and input
      checks on the get and set respectively) the necessary values that are provided by the view UI
      through text fields and text areas.
* Changing grid cell shapes, edge policies/grid properties, new seed states (random map, CSV, etc.)
  through a configuration file
    * Although we did not get to these, we implemented all of them separately already. We just did
      not get to hooking it up and parsing the keys through the sim file.
    * Thus, the way we would do this would just be to integrate the extra parameters into the
      parameter map that we keep in the parser. The parameter map is just a mapping of extra
      non-essential parameters, that we can then pass into the individual models. Then, in the
      models, we just check the keys of the parameters and use them to set configurations like edge
      policies and neighbor arrangements.
    * Initial configuration via sim file keys (Load CSV vs. random CSV vs. informed CSV)
        * getRandomGrid and getInformedGrid were methods created in the Controller class
        * Ideally, the Parser class would contain a method that sets the initial configuration to
          either CSVParser, Random, or Informed. This abstract setConfiguration method would use
          reflection call setupGrid() for whichever configuration type is specified in the .sim
          file.
    * Changing grid cell outlines and shapes via sim file
        * We would have created a method in the view that would toggle the grid cell outline by
          adding a css selector to it via getStyleclass().add(). In the CSS, this selector would
          have all the outlines on. If we detect this key in the sim file, then we call the method
          that toggles the selector on.
        * To change grid cell shapes via sim file, we currently sort of hard code it in the
          controller in the getShape() method. To do this through sim file, we just have to include
          the parsing into the map, and then key into the parameter map to get the appropriate value
          in the controller.