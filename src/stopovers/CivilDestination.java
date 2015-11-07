package stopovers;

import core.PassengerZone;

/**
 * A place that is suitable for civilians.
 * @see PassengerZone
 */
public interface CivilDestination {
  /**
   *
   * @return the PassengerZone where passengers wait for departure
   */
  PassengerZone passengerZone();

  /**
   *
   * @return the PassengerZone where passengers sleep
   */
  PassengerZone hotel();
}
