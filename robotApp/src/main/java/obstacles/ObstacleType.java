package obstacles;

import main.java.DirectionType;


/**
 * Enumeration of all the obstacle types the program supports. Default obstacle will be returned as
 * UNKNOWN
 */
public enum ObstacleType {
  ROCK("R"),
  HOLE("H"),
  SPINNER("S"),
  EMPTY("E"),
  UNKNOWN("?");

  private String obstacleName;

  ObstacleType(String obstacleName) {
    this.obstacleName = obstacleName;
  }

  public String getObstacleName() {
    return this.obstacleName;
  }


  /**
   * Return the obstacle type for the given string
   *
   * @param testValue the string, could be camel case
   * @return obstacle type found, UNKNOWN if not found
   */
  public static ObstacleType fromString(String testValue) {
    for (ObstacleType obstacle : ObstacleType.values()) {
      if (obstacle.name().equals(testValue)) {
        return obstacle;
      }
    }
    return UNKNOWN;
  }

}
