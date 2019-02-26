package main.java.robots;


import main.java.DirectionType;
import main.java.grid.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * A robot class. Holds information about hte robot's current location on the grid an a set of
 * instruction, also tracks where it has been.
 */
public class Robot {


  private String robotName;
  private Position location;
  private List<DirectionType> directions = new ArrayList<>();
  private List<String> track = new ArrayList<>();

  public Robot(String name) {
    this.robotName = name;
  }

  public void setLocation(Position pos) {
    this.location = pos;
  }

  public Position getLocation() {
    return this.location;
  }

  public List<DirectionType> getDirections() {
    return directions;
  }

  /**
   * Loads the direction string from the caller program.
   *
   * @param dirString a string of directions ('FLRFLR', for example)
   */
  public void loadDirections(String dirString) {

    for (int pos = 0; pos < dirString.length(); pos++) {
      char chr = dirString.charAt(pos);
      DirectionType dir = DirectionType.fromString(String.valueOf(chr));
      if (dir != null) {
        directions.add(dir);
      }
    }
  }

  public List<String> getTrack() {
    return track;
  }


  public String getRobotName() {
    return robotName;
  }


  /**
   * Reports the track the robot took through the grid.
   *
   * @return string of its track.
   */
  public String getTrackHistory() {
    StringBuilder sb = new StringBuilder();
    sb.append(robotName);
    for (String item : track) {
      sb.append(item);
    }
    return sb.toString();
  }

  public String toString() {
    return "Name: " + robotName + "\n" + "Loc: " + location.toString();
  }

}
