## Lab Discussion

### Team 3

### Michelle Zhang (xmz), Samuel Li, Kyle White (kjw47), Cate Schick

### Issues in Current Code

#### Parser

* Duplication

#### Wator

* Long Method

#### Wator

* Long Method

#### Game

* LinkedHashMap -> Map?

#### Controller

* SRP

### Refactoring Plan

* What are the code's biggest issues?
    * long methods
    * duplications
    * exception handlers should preserve original exceptions
    * declarations should use java collection interfaces instead of specifics
    * magic numbers -> feed through as property
    * control flow statements shouldn't be nested too deep
    * <> operator
    * SRP
    * wildcard inputs
    * complex logic expressions in if statements
    * need to make getters immutable!!!!!

* Which issues are easy to fix and which are hard?
    * easy:
        * wildcard inputs - **done**
        * complex logic expressions - **done**
        * <> operator - **done**
        * magic numbers - **done**
        * declarations should use java collection interfaces instead of specifics - **done**
        * long methods? - **done**
        * exception handlers - 
        * immutable getters - **done**
        * duplications - **done**
    * hard:
        * SRP
        * control flow statements shouldn't be nested too deep

* What is your plan to implement the changes without losing control of the process?
    * to implement changes:
        * create abstraction hierarchy for getting neighbors list, call classes through reflection
          from key from sim file
        * create abstraction hierarchy to display different shapes in grid

### Refactoring Work

* Doing all minor issues that are easy to fix during lab
* SRP: Fix and Alternatives
    * We were using the parser as the controller, which meant it was too coupled to other classes
    * The fix was that we created a new controller class which uses the Parser and CSV classes to do
      their respective jobs, preserving SRP.
    * Now, the controller is also raising flags for SRP, but given that it is the central hub for
      everything, we determine it to be justified.
    * Some View classes were also flagged as overly coupled. Given that there are only 4 classes in
      the View, and they all interact with each other, this momentarily makes sense. However, we
      plan to refactor it into an abstraction hierarchy to accommodate for the changes that we have
      to implement.
* Duplicated nested control flow loops: Fix and Alternatives
    * To search through the data structure, we are looping through the grid in multiple places in
      the model.
    * Fix alternative 1:
        * instead of 2 for loops, we turn it into a while loop with two ints for row and col that
          behave as indexes.
    * Fix alternative 2:
        * extract loops as method and reuse that one search method everywhere.
        * would need to pass in the operation that we need to perform within the loop