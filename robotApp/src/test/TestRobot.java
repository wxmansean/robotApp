package test;


import static org.junit.Assert.assertEquals;

import main.java.DirectionType;
import main.java.grid.Grid;
import main.java.grid.Position;
import main.java.robots.Robot;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestRobot {

  @Test
  public void testLoadRobot_InvalidLocation() {
    Grid grid = new Grid(3, 3);

    Robot robot = new Robot("testRobot");
    robot.setLocation(new Position(4, 6, 0));

    boolean actual = grid.addRobot(robot);

    assertEquals("Expected false", false, actual);

  }

  @Test
  public void testDirections_Success() {
    List<DirectionType> expected = new ArrayList<>();
    expected.add(DirectionType.FORWARD);
    expected.add(DirectionType.LEFT);
    expected.add(DirectionType.RIGHT);

    Robot robot = new Robot("testRobot");
    robot.loadDirections("FLR");
    List<DirectionType> actual = robot.getDirections();

    assertEquals("Mismatch on list contents", expected, actual);
  }

  @Test
  public void testDirections_InvalidEntry() {
    List<DirectionType> expected = new ArrayList<>();
    expected.add(DirectionType.FORWARD);
    expected.add(DirectionType.LEFT);

    Robot robot = new Robot("testRobot");
    robot.loadDirections("FLX");
    List<DirectionType> actual = robot.getDirections();

    assertEquals("Mismatch on list contents", expected, actual);
  }

}
