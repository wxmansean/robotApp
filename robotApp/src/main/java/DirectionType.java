package main.java;


/**
 * Enum of the possible movments a robot can make.
 */
public enum DirectionType {
  LEFT("L"),
  RIGHT("R"),
  FORWARD("F");

  private String name;

  DirectionType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }


  /**
   * get the DirectionType from a String.
   *
   * @param testValue passed in direction string
   * @return DirectionType object
   */
  public static DirectionType fromString(String testValue) {

    for (DirectionType direction : DirectionType.values()) {
      if (direction.getName().equalsIgnoreCase(testValue)) {
        return direction;
      }
    }
    return null;
  }
}
