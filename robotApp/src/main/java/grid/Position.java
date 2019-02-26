package main.java.grid;


/**
 * A class to hold the grid coordinate and the orientation of the object being placed on the grid.
 * Orientation is limited to multiples of 90, to mimic a compass rose ordinal direction (0=N, 90=E,
 * 180=S, 270=W)
 */
public class Position {

  private int xPos;
  private int yPos;
  private int orientation;


  public Position(int xPos, int yPos) {
    this(xPos, yPos, 0);
  }

  public Position(int xPos, int yPos, int orientation) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.orientation = doOrientation(orientation);
  }

  public int getXPos() {
    return xPos;
  }

  public void setXPos(int xPos) {
    this.xPos = xPos;
  }

  public int getYPos() {
    return yPos;
  }

  public void setYPos(int yPos) {
    this.yPos = yPos;
  }

  public int getOrientation() {
    return orientation;
  }

  public void setOrientation(int orientation) {
    this.orientation = doOrientation(orientation);
  }

  /**
   * we want ot keep the orientation within hte number of degrees on a compass (0-360).  This keeps
   * us within our range.
   */
  private int doOrientation(int orient) {
    if (orient >= 360) {
      orient -= 360;
    }
    return orient;
  }

  /**
   * Checks that the orientation is a multiple of 90, for compass directions
   *
   * @param orient the orientation to check
   * @return true if this is a valid direction, false otherwise.
   */
  public boolean isValidOrientation(int orient) {
    if (orient < 0 || orient % 90 != 0) {
      return false;
    }
    return true;
  }


  public String toString() {
    return "X:" + xPos + " Y:" + yPos + " DIR:" + orientation;
  }

}
