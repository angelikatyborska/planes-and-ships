package core;

import destinations.Destination;

public class Passenger {
  private final String firstName;

  private final String lastName;
  private final String PESEL;
  private Coordinates coordinaes;
  public Passenger(String firstName, String lastName, String PESEL, Coordinates coordinates){
    this.firstName = firstName;
    this.lastName = lastName;
    this.PESEL = PESEL;
    this.coordinaes = coordinates;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPESEL() {
    return PESEL;
  }

  public Coordinates getCoordinaes() {
    return coordinaes;
  }

  public void arriveAt(Destination destination) {

  }
}
