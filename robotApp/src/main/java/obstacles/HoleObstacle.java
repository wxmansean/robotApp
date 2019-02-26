package main.java.obstacles;

import main.java.grid.Position;


/**
 * A hole obstacle. If a robot moves onto this obstacle, it is transported somewhere else on the
 * grid.
 */
public class HoleObstacle extends Obstacle {

  /**
   * Ctor.
   *
   * @param loc the location of the obstacle
   * @param newLoc the new location to which the robot will be moved.
   */
  public HoleObstacle(Position loc, Position newLoc) {
    super(obstacles.ObstacleType.HOLE, loc);
    action = newLoc;
  }

  @Override
  public Position getAction() {
    return action;
  }

  @Override
  public String toString() {
    return this.obstacleType + ": " + location.toString() + " XPRT: (" + action.toString() + ")";
  }

}
