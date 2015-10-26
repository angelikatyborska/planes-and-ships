package core;

import stopovers.CivilDestination;
import world.StopoverNotFoundInStopoverNetworkException;
import world.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class PassengerGenerator {
  // TODO: come up with a better way to store (more!) potential names
  private static String[] firstNames = {"Alice", "Bob", "Stephen", "Katherine", "Maurice", "Wallace", "Robert", "Rose", "Paul", "John", "Julie", "Gabrielle", "Anabelle", "Judy", "Jennifer", "Patrick"};
  private static String[] lastNames = {"Moss", "Mills", "Evans", "Harper", "Smith", "Goodman", "Sandberg", "Kowalski", "Nowak", "Rodrigez", "Keys", "Becket", "Ackermann"};
  private WorldMap map;

  public PassengerGenerator(WorldMap map) {
    this.map = map;
  }

  /**
   *
   * @param hometown
   * @return A passenger with randomly filled out fields. Please note that the PESEL number is not correct, it's just a string of random digits and it does not correspond to the random age.
   * @throws StopoverNotFoundInStopoverNetworkException
   */
  public Passenger randomPassenger(CivilDestination hometown) throws StopoverNotFoundInStopoverNetworkException {
    String firstName = firstNames[(int) Math.floor(Math.random() * firstNames.length)];
    String lastName = lastNames[(int) Math.floor(Math.random() * lastNames.length)];
    int age = (int) Math.floor(Math.random() * 50 + 18);
    String PESEL = randomPESEL();
    return new Passenger(map, firstName, lastName, age, PESEL, hometown);
  }

  public List<Passenger> randomPassengers(CivilDestination hometown, int n) throws StopoverNotFoundInStopoverNetworkException {
    ArrayList<Passenger> passengers = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      passengers.add(randomPassenger(hometown));
    }

    return passengers;
  }

  private String randomPESEL() {
    String PESEL = "";

    for (int i = 0; i < 11; i++) {
      PESEL = PESEL.concat(Integer.toString((int) Math.floor(Math.random() * 10)));
    }

    return PESEL;
  }
}
