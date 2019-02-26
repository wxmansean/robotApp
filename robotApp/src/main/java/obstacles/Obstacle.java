package main.java.obstacles;

import main.java.grid.Position;

/**
 * An obstacle class. This is the base class from which all obstacle types can extend to modify their
 * behavior as desired.
 */
public abstract class Obstacle {


  protected obstacles.ObstacleType obstacleType;

  protected Position location;
  protected Position action;


  /**
   * Ctor.
   *
   * @param obstacleType {@link obstacles.ObstacleType} expected
   * @param loc {@link Position} object that houses current location on the grid of the object
   */
  public Obstacle(obstacles.ObstacleType obstacleType, Position loc) {
    this.obstacleType = obstacleType;
    this.location = loc;
  }

  public Position getLocation() {
    return location;
  }


  public abstract Position getAction();

  public obstacles.ObstacleType getObstacleType() {
    return obstacleType;
  }

  public String toString() {
    return this.obstacleType + ": " + location.toString();
  }

}