package pepse.world.avatar;

/** interface representing a subscriber in the Observer Design Pattern
 * classes implementing this interface will be notified of changes in energy
 * * */
public interface EnergyObserver {
    /** used by the publisher to notify observers of an energy change
     * @param currentEnergy  percentage of energy */
    void updateEnergy(float currentEnergy);
}