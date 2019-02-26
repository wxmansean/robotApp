package test;


import static org.junit.Assert.assertEquals;

import main.java.grid.Grid;
import main.java.grid.Position;
import main.java.obstacles.HoleObstacle;
import main.java.obstacles.RockObstacle;
import main.java.obstacles.SpinnerObstacle;
import main.java.robots.Robot;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestGrid {

  // used for testing expected exceptions. Will be populated by the tests that need it.
  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Test
  public void testInitGrid() {
    Grid grid = new Grid(5, 5);


  }

  @Test
  public void testAddObstacle() {
    Grid grid = new Grid(5, 5);
    grid.addObstacle(new RockObstacle(new Position(1, 3)));
  }


  @Test
  public void testAddObstacleOutFfBounds_X() {
    Grid grid = new Grid(5, 5);
    boolean actual = grid.addObstacle(new RockObstacle(new Position(-1, 3)));

    assertEquals("Expected false", false, actual);
  }

  @Test
  public void testAddObstacleOutFfBounds_Y() {
    Grid grid = new Grid(5, 5);
    boolean actual = grid.addObstacle(new RockObstacle(new Position(2, 7)));
    assertEquals("Expected false", false, actual);

  }

  /**
   * Successful traversal of 3x3 grid with no obstacles.
   */
  @Test
  public void testTraverse_Success() {

    String expected = "F=X:1 Y:0 DIR:0R=X:1 Y:1 DIR:0L=X:1 Y:0 DIR:0";
    Grid grid = new Grid(3, 3);
    Robot robot1 = new Robot("johnny5");
    robot1.setLocation(new Position(0, 0));
    robot1.loadDirections("FRL");
    grid.addRobot(robot1);
    grid.traverse();
    String actual = robot1.getTrackHistory();

    assertEquals("Mismatch on list contents", expected, actual);

  }


  /**
   * The second direction, L, should be ignored, as it would end out out of bounds of the grid
   * (1,-1)
   */
  @Test
  public void testTraverse_MoveOutofBounds() {

    String expected = "F=X:1 Y:0 DIR:0R=X:1 Y:1 DIR:0";
    Grid grid = new Grid(3, 3);
    Robot robot1 = new Robot("johnny5");
    robot1.setLocation(new Position(0, 0));
    robot1.loadDirections("FLR");
    grid.addRobot(robot1);
    grid.traverse();
    String actual = robot1.getTrackHistory();

    assertEquals("Mismatch on list contents", expected, actual);

  }

  /**
   * Successful traversal of 3x3 grid with no obstacles.
   */
  @Test
  public void testTraverse_FoundRock() {

    String expected = "R=X:0 Y:2 DIR:0";
    Grid grid = new Grid(3, 3);

    boolean addOk = grid.addObstacle(new RockObstacle(new Position(1, 1)));
    assertEquals("Expected true", true, addOk);

    Robot robot1 = new Robot("johnny5");
    robot1.setLocation(new Position(0, 1));
    robot1.loadDirections("FR");
    grid.addRobot(robot1);
    grid.traverse();
    String actual = robot1.getTrackHistory();

    assertEquals("Mismatch on list contents", expected, actual);

  }

  /**
   * Successful traversal of 3x3 grid with no obstacles.
   */
  @Test
  public void testTraverse_FoundSpinner() {

    String expected = "F=X:1 Y:1 DIR:90"
        + "R=X:0 Y:1 DIR:0";
    Grid grid = new Grid(3, 3);

    boolean addOk = grid.addObstacle(new SpinnerObstacle(new Position(1, 1), 90));
    assertEquals("Expected true", true, addOk);

    Robot robot1 = new Robot("johnny5");
    robot1.setLocation(new Position(0, 1));
    robot1.loadDirections("FR");
    grid.addRobot(robot1);
    grid.traverse();
    String actual = robot1.getTrackHistory();

    assertEquals("Mismatch on list contents", expected, actual);

  }

  @Test
  public void testTraverse_FoundHole() {

    String expected =
        "F=X:1 Y:0 DIR:0F=X:2 Y:2 DIR:0L=X:2 Y:1 DIR:0";
    Grid grid = new Grid(3, 3);

    boolean addOk = grid.addObstacle(new HoleObstacle(new Position(1, 0), new Position(2, 2)));
    assertEquals("Expected true", true, addOk);

    Robot robot1 = new Robot("johnny5");
    robot1.setLocation(new Position(0, 0));
    robot1.loadDirections("FL");
    grid.addRobot(robot1);
    grid.traverse();
    String actual = robot1.getTrackHistory();

    assertEquals("Mismatch on list contents", expected, actual);

  }

}
