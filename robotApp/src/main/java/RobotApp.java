package main.java;


import main.java.grid.Grid;
import main.java.grid.Position;
import main.java.obstacles.HoleObstacle;
import main.java.obstacles.Obstacle;
import main.java.obstacles.RockObstacle;
import main.java.obstacles.SpinnerObstacle;
import main.java.obstacles.UnknownObstacle;
import main.java.robots.Robot;
import obstacles.ObstacleType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RobotApp {

  /**
   * Main program
   *
   * @param args 1: grid information file 2: file with robots to load
   */
  public static void main(String[] args) {

    int maxColumns = 5;
    int maxRows = 5;

    // ex if we do not have the right amount of arguments
    if (args.length != 2) {
      System.out.println("Usage: RobotApp <grid file> <root app> ");
      System.exit(1);
    }
    RobotApp roboApp = new RobotApp();
    Grid grid = null;


    // initialize a new grid
    try {
      System.out.println("Loading grid information (including obstacles)  from " + args[0]);
      grid = roboApp.loadGridAttributesFromFile(args[0]);
    } catch (Exception exc) {
      System.out.println("Unable to create grid. Exiting");
      System.exit(1);
    }

    // initialize a new grid
    try {
      System.out.println("Loading robot information  from " + args[1]);
      roboApp.loadRobotsFromFile(args[1], grid);
    } catch (Exception exc) {
      System.out.println("Unable to load robots. Exiting");
      System.exit(1);
    }

    System.out.println(grid.toString());

    // set the robots loose on the grid
    grid.traverse();

    for (Robot robot : grid.getRobots()) {
      // display robot's course through the grid
      System.out.println(robot.getTrack());
    }

  }

  /**
   * load the grid attributes from input file. File contains the grid size and a listing of
   * obstacles to load on the grid.
   *
   * @param fileName name of file to load
   * @return a populated Grid object
   * @throws Exception could not load file
   */
  public Grid loadGridAttributesFromFile(String fileName) throws Exception {
    BufferedReader br = null;
    Grid grid = null;

    try {

      br = new BufferedReader(new FileReader(fileName));

      String lineItem = br.readLine();

//      Rock,1,3,0,0,0,0
      while (lineItem != null) {
        lineItem = br.readLine();
        if (lineItem == null || lineItem.startsWith("#") || lineItem.trim().isEmpty()) {
          continue;
        } else if (lineItem.toUpperCase().startsWith("GRIDSIZE=")) {
          String[] coords = lineItem.toUpperCase().replace("GRIDSIZE=", "").split(",");
          // grid has to have 2 elements
          if (coords.length != 2) {
            throw new Exception("Invalid grid coordinates given. Unable to parse");
          }
          // create a new Grid
          int maxRows, maxColumns;
          try {
            maxRows = Integer.parseInt(coords[0]);
            maxColumns = Integer.parseInt(coords[1]);
          } catch (NumberFormatException exc) {
            throw new Exception("Invalid grid coordinates:" + coords[0] + "," + coords[1]);
          }
          grid = new Grid(maxRows, maxColumns);
        } else {
          String[] elements = lineItem.split(",", 6);
          if (elements.length < 3) {
            throw new Exception(
                "Invalid obstacle info given. Expecting <type>,<xLoc>,<yLoc>,<newXLoc>,<newYLoc>,<orientation>");
          }

          //required: obstacle type
          String type = elements[0];

          //required: obstacle location
          int xLoc = -1, yLoc = -1, newXLoc = -1, newYLoc = -1, orient = -1;
          try {
            xLoc = Integer.parseInt(elements[1]);
            yLoc = Integer.parseInt(elements[2]);
          } catch (NumberFormatException exc) {
            throw new Exception("Invalid grid coordinates:" + elements[1] + "," + elements[2]);
          }

          // coordinates of new location
          if (!elements[3].trim().isEmpty() && !elements[4].trim().isEmpty()) {
            try {
              newXLoc = Integer.parseInt(elements[3]);
              newYLoc = Integer.parseInt(elements[4]);
            } catch (NumberFormatException exc) {
              throw new Exception("Invalid grid coordinates:" + elements[3] + "," + elements[4]);
            }
          }

          // orientation
          if (!elements[5].trim().isEmpty()) {
            try {
              orient = Integer.parseInt(elements[5]);
            } catch (NumberFormatException exc) {
              throw new Exception("Invalid orientation:" + elements[5]);
            }
          }

          // try to create a new obstacle
          Obstacle obstacle = createObstacle(type, xLoc, yLoc, newXLoc, newYLoc, orient);
          grid.addObstacle(obstacle);
        }
      }
    } catch (IOException exc) {

    } finally {
      try {
        if (br != null) {
          br.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return grid;
  }

  /**
   * load the robot attributes from input file. File contains a listing of robots, starting
   * coordinates, and direction commands.
   *
   * @param fileName name of file to load
   * @param grid thegrid to load the robots onto
   * @throws Exception could not load file
   */
  public void loadRobotsFromFile(String fileName, Grid grid) throws Exception {
    BufferedReader br = null;

    try {

      br = new BufferedReader(new FileReader(fileName));

      String lineItem = br.readLine();

//      name,x,y,directions
      while (lineItem != null) {
        lineItem = br.readLine();
        if (lineItem == null || lineItem.startsWith("#") || lineItem.trim().isEmpty()) {
          continue;
        } else {
          String[] robotStuff = lineItem.split(",");
          // grid has to have 2 elements
          if (robotStuff.length != 5) {
            throw new Exception("Invalid robot format given. Unable to parse");
          }
          String[] elements = lineItem.split(",", 5);

          // required: robot name
          String name = elements[0];

          // required: robot starting point
          int xLoc = -1, yLoc = -1, orient = -1;
          try {
            xLoc = Integer.parseInt(elements[1]);
            yLoc = Integer.parseInt(elements[2]);
          } catch (NumberFormatException exc) {
            throw new Exception("Invalid grid coordinates:" + elements[1] + "," + elements[2]);
          }

          // required: orientation
          if (!elements[3].trim().isEmpty()) {
            try {
              orient = Integer.parseInt(elements[3]);
            } catch (NumberFormatException exc) {
              throw new Exception("Invalid orientation:" + elements[3]);
            }
          }

          // required: robot name
          String directions = elements[4];

          // create the robot object
          Robot robot = new Robot(name);
          Position robotPsn = new Position(xLoc, yLoc, orient);
          robot.setLocation(robotPsn);
          robot.loadDirections(directions);
          grid.addRobot(robot);

        }
      }
    } catch (IOException exc) {

    } finally {
      try {
        if (br != null) {
          br.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * creates obstacles.  This could probably be moved to a Factory.
   *
   * @param type the type of obstacle
   * @param xLoc x coordinate of obstacle
   * @param yLoc y coordinate of obstacle
   * @param newXLoc new x location (if this ObstacleType can re-locate the robot)
   * @param newYLoc new y location (if this ObstacleType can re-locate the robot)
   * @param orient how far to spin the robot (if this obstacle type can rotate the robot)
   * @return an Obstacle object
   */
  public Obstacle createObstacle(String type, int xLoc, int yLoc, int newXLoc, int newYLoc,
      int orient) {

    ObstacleType obsType = ObstacleType.fromString(type.toUpperCase());

    switch (obsType) {
      case ROCK:
        return new RockObstacle(new Position(xLoc, yLoc));
      case HOLE:
        return new HoleObstacle(new Position(xLoc, yLoc), new Position(xLoc, yLoc));
      case SPINNER:
        return new SpinnerObstacle(new Position(xLoc, yLoc), orient);
      case UNKNOWN:
      default:
        return new UnknownObstacle(new Position(xLoc, yLoc));
    }
  }
}
