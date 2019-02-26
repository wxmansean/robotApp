package test;

import main.java.RobotApp;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestRobotApp {

  // used for testing expected exceptions. Will be populated by the tests that need it.
  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  @Test
  public void testLoadApp() {

    RobotApp roboApp = new RobotApp();

    String args[] = {"gridinfo.txt", "robots.txt"};

    roboApp.main(args);
  }


}
