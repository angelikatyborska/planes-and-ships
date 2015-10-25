package vehicles;

import gui.Drawer;
import stopovers.Airport;
import stopovers.CivilDestination;
import stopovers.InvalidVehicleAtStopoverException;
import stopovers.Stopover;

public abstract class Airplane extends Vehicle {
  private final double fuelCapacity;
  private final double fuelBurningRate = 1;
  private double fuel;
  private int personnel;

  public Airplane(double velocity, double fuelCapacity) {
    super(velocity);
    this.personnel = (int) Math.floor(Math.random() * 10 + 3);
    this.fuelCapacity = fuelCapacity;
    this.fuel = fuelCapacity;
  }

  public double getFuel() {
    return fuel;
  }

  public int getPersonnel() {
    return personnel;
  }

  public void refillFuel() {
    fuel = fuelCapacity;
  }

  public void burnFuel(double distance) {
    if (fuel > distance * fuelBurningRate) {
      fuel -= distance * fuelBurningRate;
    }
  }

  public void crashLanding() {
    // should reroute to next nearest airport and disappear (it's damaged!) (what about passengers?)
  }

  public Airport getNextAirport() {
    for (int i = previousStopoverNumber; i < route.size(); i++) {
      if (route.get(i) instanceof Airport) {
        return (Airport) route.get(i);
      }
    }

    return null;
  }

  @Override
  public void draw(Drawer drawer) {
    drawer.drawAirplane(this);
  }
}
