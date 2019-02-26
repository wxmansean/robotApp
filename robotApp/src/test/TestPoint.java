package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import main.java.grid.Position;
import org.junit.Test;

public class TestPoint {

  @Test
  public void testCreatePoint_Success() {
    Position position = new Position(1, 2, 90);

    assertEquals("Mismatch on X coordinate", position.getXPos(), 1);
    assertEquals("Mismatch on Y coordinate", position.getYPos(), 2);
    assertEquals("Mismatch on orientation", position.getOrientation(), 90);
  }

  @Test
  public void testOrientation_isValid() {
    Position position = new Position(1, 2);

    boolean isValid = position.isValidOrientation(180);
    assertTrue(isValid);
  }

  @Test
  public void testOrientation_notDivBy90() {
    Position position = new Position(1, 2);

    boolean isValid = position.isValidOrientation(57);
    assertFalse(isValid);
  }

  @Test
  /**
   * Test that values greater than 360 are 'normalized' to fit our compass.
   * If the orientation is 270 and 180 is added o it, this test expects the
   * resulting orientation to be 90.
   */
  public void testOrientation_enteredGreaterThan360() {
    Position position = new Position(1, 2);

    position.setOrientation(450);
    assertEquals("Mismatch on orientation", position.getOrientation(), 90);
  }

}
