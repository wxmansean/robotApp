package main.java.obstacles;

import main.java.grid.Position;
import obstacles.ObstacleType;

/**
 * Unknown obstacle.  Its behavior is similar to a ROCK obstacle, so we inherit from there
 */
public class UnknownObstacle extends RockObstacle {

  public UnknownObstacle(Position loc) {
    super(loc);
    this.obstacleType = ObstacleType.UNKNOWN;
  }
}
