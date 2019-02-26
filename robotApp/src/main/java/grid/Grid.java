package main.java.grid;

import main.java.DirectionType;
import main.java.obstacles.EmptyObstacle;
import main.java.obstacles.HoleObstacle;
import main.java.obstacles.Obstacle;
import main.java.obstacles.RockObstacle;
import main.java.obstacles.SpinnerObstacle;
import main.java.obstacles.UnknownObstacle;
import main.java.robots.Robot;
import obstacles.ObstacleType;

import java.util.ArrayList;
import java.util.List;


/**
 * A grid object, represente teh universe that obstacles and robots (and whatever else) will be
 * loaded onto.
 */
public class Grid {


  private int maxRows;
  private int maxColumns;


  // robots
  private List<Robot> robots = new ArrayList<>();
  // obstacles
  private List<Obstacle> grid = new ArrayList<>();

  /**
   * Ctor.
   *
   * @param maxRows Max # of rows.
   * @param maxColumns Max # of columns.
   */
  public Grid(int maxRows, int maxColumns) {
    this.maxRows = maxRows;
    this.maxColumns = maxColumns;

    initializeGrid();
  }

  /**
   * initialize the grid with empty obstacles
   */
  private void initializeGrid() {
    for (int row = 0; row < maxRows; row++) {
      for (int column = 0; column < maxColumns; column++) {
        grid.add(new EmptyObstacle(new Position(row, column)));
      }
    }
  }

  /**
   * add an robot to the grid.
   *
   * @param robot the robot to add.
   */
  public boolean addRobot(Robot robot) {

    // check if the coordinates are out of bounds
    if (isOutofBounds(robot.getLocation())) {
      System.out.println(
          "Cannot add robot '" + robot.getRobotName() + "' - out of bounds: " + robot.getLocation()
              .getXPos() + ","
              + robot.getLocation().getYPos());
      return false;
    }
    robots.add(robot);
    return true;
  }

  /**
   * add a list of obstacles to the grid.
   *
   * @param obstacles list o obstacles to add.
   */
  public void addAllObstacles(List<Obstacle> obstacles) {
    for (Obstacle ob : obstacles) {
      addObstacle(ob);
    }
  }

  /**
   * add an obstacle to the grid.
   *
   * @param newObstacle the obstacle to add.
   */
  public boolean addObstacle(Obstacle newObstacle) {

    // check if the coordinates are out of bounds
    if (isOutofBounds(newObstacle.getLocation())) {
      System.out.println(
          "Cannot add obstacle " + newObstacle.getObstacleType().getObstacleName()
              + "- out of bounds: "
              + newObstacle.getLocation().getXPos() + ","
              + newObstacle.getLocation().getYPos());
      return false;
    }

    int x = newObstacle.getLocation().getXPos();
    int y = newObstacle.getLocation().getYPos();

    for (Obstacle gridPoint : grid) {
      if (gridPoint.getLocation().getXPos() == x && gridPoint.getLocation().getYPos() == y) {
        int idx = grid.indexOf(gridPoint);
        grid.remove(idx);
        grid.add(idx, newObstacle);
        return true;
      }
    }

    return false;
  }

  /**
   * find the obstacle at the requested position on the grid
   *
   * @param curPos the position object ot look
   * @return the obstacle obejct that was found
   */
  public Obstacle getObstacle(Position curPos) {
    for (Obstacle gridPoint : grid) {
      if (gridPoint.getLocation().getXPos() == curPos.getXPos()
          && gridPoint.getLocation().getYPos() == curPos.getYPos()) {
        return gridPoint;
      }
    }
    return null;
  }

  /**
   * Check if a set of coordinates are within the realm of the grid.
   *
   * @param psn coordinates to check
   * @return true if coordinates are out of bounds, false otherwise.
   */
  public boolean isOutofBounds(Position psn) {

    if ((psn.getXPos() < 0 || psn.getXPos() > maxRows - 1)
        || (psn.getYPos() < 0 || psn.getYPos() > maxColumns - 1)) {
      return true;
    }

    return false;
  }


  /**
   * Move all of the robots that hav ebeen loaded on the grid. Each robot will move according to its
   * full execution string given.  If a move allows the robot to go out of bounds, that will be
   * reported to the user.
   */
  public void traverse() {
    for (Robot robot : robots) {
      for (DirectionType dir : robot.getDirections()) {
        Position psn = null;
        switch (dir) {
          case FORWARD:
            psn = move(robot.getLocation().getOrientation(), robot.getLocation());
            break;
          case LEFT:
            psn = move(robot.getLocation().getOrientation() + 270, robot.getLocation());
            break;
          case RIGHT:
            psn = move(robot.getLocation().getOrientation() + 90, robot.getLocation());
            break;
        }

        if (isOutofBounds(psn)) {
          robot.getTrack().add(dir.getName() + " out of bounds - " + psn.toString());
        } else {
          // check the obstacle in the new spot
          Obstacle obs = getObstacle(psn);
          if (obs instanceof RockObstacle || obs instanceof UnknownObstacle) {
            // rock obstacle - can't move here
            robot.getTrack().add(dir.getName() + " obstacle found - " + psn.toString());
          } else if (obs instanceof SpinnerObstacle) {
            // spinner obstacle - rotate the orientation by teh amount specified
            int orient = ((SpinnerObstacle) obs).getAction().getOrientation();
            robot.setLocation(psn);
            robot.getLocation().setOrientation(robot.getLocation().getOrientation() + orient);
            robot.getTrack().add(dir.getName() + "=" + psn.toString());
          } else if (obs instanceof HoleObstacle) {
            // hole obstacle - transport the robot to the new location
            robot.getTrack().add(dir.getName() + "=" + psn.toString());
            robot.setLocation(obs.getAction());
            robot.getTrack().add(dir.getName() + "=" + obs.getAction().toString());
          } else {
            robot.setLocation(psn);
            robot.getTrack().add(dir.getName() + "=" + psn.toString());
          }
        }
      }
    }
  }

  /**
   * Executes a move on the grid, based on hte orientation of the robot.
   *
   * @param dirFacing the direction which we are facing
   * @param currPosition the robot's current position
   * @return the new grid coordinate where the robot is located.
   */
  private Position move(int dirFacing, Position currPosition) {
    int x = currPosition.getXPos();
    int y = currPosition.getYPos();

    switch (dirFacing) {
      case 0:
        x += 1;
        break;
      case 90:
        y += 1;
        break;
      case 180:
        x -= 1;
        break;
      case 270:
        y -= 1;
        break;
      default:
    }

    return new Position(x, y);
  }

  public List<Robot> getRobots() {
    return robots;
  }

  public void setRobots(List<Robot> robots) {
    this.robots = robots;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nGRID SIZE:" + maxRows + "x" + maxColumns);
    sb.append("\nObstacles loaded:\n");

    for (Obstacle gridpoint : grid) {
      if (!gridpoint.getObstacleType().equals(ObstacleType.EMPTY)) {
        sb.append(gridpoint.toString());
        sb.append("\n");
      }
    }
    sb.append("\nRobots loaded:\n");
    for (Robot bot : robots) {
      sb.append(bot.toString());
      sb.append("\n");
    }
    return sb.toString();
  }

}
