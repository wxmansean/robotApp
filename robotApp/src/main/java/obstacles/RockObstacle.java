package main.java.obstacles;

import main.java.grid.Position;

/**
 * A Rock obstacle. This obstacle cannot be moved onto.
 */
public class RockObstacle extends Obstacle {

  public RockObstacle(Position loc) {
    super(obstacles.ObstacleType.ROCK, loc);
  }

  @Override
  public Position getAction() {
    return new Position(0, 0, 0);
  }
}
