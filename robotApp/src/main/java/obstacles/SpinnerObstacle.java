package main.java.obstacles;

import main.java.grid.Position;


/**
 * A Spinner obstacle.  Robots moving onto this obstacle will have their orientation (direction)
 * moved +/- the direction specified.
 */
public class SpinnerObstacle extends Obstacle {

  /**
   * Ctor.
   *
   * @param loc location of the obstacle
   * @param orientation the amount by which to turn the robot. Multiple of 90 expected.
   */
  public SpinnerObstacle(Position loc, int orientation) {
    super(obstacles.ObstacleType.SPINNER, loc);
    action = new Position(0, 0);
    action.setOrientation(orientation);
  }


  @Override
  public Position getAction() {
    return new Position(0, 0, action.getOrientation());
  }


  @Override
  public String toString() {
    return this.obstacleType + ": " + location.toString() + " SPIN: " + action.getOrientation();
  }

}
