package main.java.obstacles;


import main.java.grid.Position;

/**
 * An empty obstacle. These are the default obstacles (robots simply move over these obstacles)
 */
public class EmptyObstacle extends Obstacle {

  public EmptyObstacle(Position loc) {
    super(obstacles.ObstacleType.EMPTY, loc);
  }

  @Override
  public Position getAction() {
    return new Position(1, 1, 0);
  }
}
