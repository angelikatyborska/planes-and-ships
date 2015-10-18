package core;

import stopovers.Destination;
import world.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class PassengerGenerator {
  // TODO: come up with a better way to store (more!) potential names
  private static String[] firstNames = {"Anna", "Barbara", "Czesław", "Dariusz", "Elbżbieta"};
  private static String[] lastNames = {"Andrzejewicz", "Baranowicz", "Czekaj", "Dębicz", "Ezofowicz"};

  public static Passenger randomPassenger(WorldMap map, Destination hometown) {
    String firstName = firstNames[(int) Math.floor(Math.random() * firstNames.length)];
    String lastName = lastNames[(int) Math.floor(Math.random() * lastNames.length)];
    String PESEL = randomPESEL();
    return new Passenger(map, firstName, lastName, PESEL, hometown);
  }

  public static List<Passenger> randomPassengers(WorldMap map, Destination hometown, int n) {
    ArrayList<Passenger> passengers = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      passengers.add(randomPassenger(map, hometown));
    }

    return passengers;
  }

  private static String randomPESEL() {
    String PESEL = "";

    for (int i = 0; i < 11; i++) {
      PESEL = PESEL.concat(Integer.toString((int) Math.floor(Math.random() * 10)));
    }

    return PESEL;
  }
}
