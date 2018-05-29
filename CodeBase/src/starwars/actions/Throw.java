package starwars.actions;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;

/**
 * Command to attack entities.
 * 
 * This affordance is attached to all attackable entities
 * 
 * @author David.Squire@monash.edu (dsquire)
 */
/*
 * Change log
 * 2017/02/03	Fixed the bug where the an actor could attack another actor in the same team (asel)
 * 2017/02/08	Attack given a priority of 1 in constructor (asel)
 */
public class Throw extends SWAffordance {
	
	/**
	 * Constructor for the <code>Throw</code> class. Will initialize the <code>messageRenderer</code> and
	 * give <code>Throw</code> a priority of 1 (lowest priority is 0).
	 * 
	 * @param theTarget the target being thrown
	 * @param m message renderer to display messages
	 */
	public Throw(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);	
		priority = 1;
	}
	

	/**
	 * Returns if or not this <code>Throw</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying an item already.
	 *  
	 * @author 	Ariffin (11/05/2018)
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is can throw this item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a instanceof SWActor;
		
	}
	
	
	/**
	 * Perform the <code>Throw</code> command on the location that this <code>SWActor</code> is at.
	 * <p>
	 * This method will perform damage to all entities in the local area up to two spaces from where this <code>SWActor</code> is standing based on the following rules:
	 * <ul>
	 * 	<li>Entities in the location where grenade is thrown gets damaged based on 100% of the grenade's hitpoints
	 * 	<li>Entities in the location next to where grenade is thrown gets 50% damage done
	 * 	<li>Entities in the location two spaces from where grenade is thrown gets 25% damage done
	 * </ul>
	 * <p>
	 * @author 	Ariffin
	 * @param 	a the <code>SWActor</code> who is throwing the grenade
	 * @pre		this method should only be called if actor is carrying an item with the THROWABLE Capability
	 */
	public void act(SWActor a) {
		// location where grenade is thrown
		SWEntityInterface grenade = a.getItemCarried();
		SWLocation location = a.getLocation();
		// grenade damage determined by its hitpoints
		int grenadeDamage = grenade.getHitpoints();
		
		// List of entities in this location
		List<SWEntityInterface> entitiesHere = a.getWorld().getEntityManager().contents(location);

		System.out.println(a.getShortDescription() + " throws the grenade. It explodes releasing a tremendous blast of energy and debris");
		
		// Affecting entities in central blast zone
		for (int i = 0; i < entitiesHere.size(); i++) {
			SWEntityInterface entity = entitiesHere.get(i);
			// does not affect the actor throwing the grenade
			if (entity != a) {
				System.out.println(entity.getShortDescription() + " got caught in the central blast of the grenade!");
				entity.takeDamage(grenadeDamage);
			}
		}
		// Affecting entities in secondary blast zone
		CompassBearing direction = CompassBearing.NORTH;
		// Checking each location around by its direction from central location
		for (int i = 0; i < 8; i++) {
			Location secondaryRing = location.getNeighbour(direction);
			// list of entities in this secondary ring location
			List<SWEntityInterface> entitiesSecondary = a.getWorld().getEntityManager().contents((SWLocation) secondaryRing);
			// if list of entities in this secondary ring location is not empty,
			if (entitiesSecondary != null) {
				// proceed to damage each relevant entity
				for (int j = 0; j < entitiesSecondary.size(); j++) {
					SWEntityInterface entity = entitiesSecondary.get(j);
					System.out.println(entity.getShortDescription() + " got caught in the secondary blast of the grenade!");
					// damage taken in secondary ring is half that of primary damage
					entity.takeDamage(grenadeDamage/2);
				}
			}
			
			// Affecting entities in the tertiary blast zone
			// Get location two spaces from central zone in the same direction
			Location tertiaryRing1 = secondaryRing.getNeighbour(direction);
			// Get location clockwise from that location
			Location tertiaryRing2 = secondaryRing.getNeighbour(direction.turn(45));
			// create list for entities in these two tertiary ring locations
			List<SWEntityInterface> entitiesTertiary = new ArrayList<SWEntityInterface>();
			// entities in tertiary ring location 1
			List<SWEntityInterface> entitiesTertiary1 = a.getWorld().getEntityManager().contents((SWLocation) tertiaryRing1);
			// entities in tertiary ring location 2
			List<SWEntityInterface> entitiesTertiary2 = a.getWorld().getEntityManager().contents((SWLocation) tertiaryRing2);
			// if entities is not null, add to total list
			if (entitiesTertiary1 != null) {
				entitiesTertiary.addAll(entitiesTertiary1);
			}
			if (entitiesTertiary2 != null) {
				entitiesTertiary.addAll(entitiesTertiary2);
			}
			// if the list is not empty, 
			if (entitiesTertiary != null) {
				// proceed to damage the relevant entity
				for (int k = 0; k < entitiesTertiary.size(); k++) {
					SWEntityInterface entity = entitiesTertiary.get(k);
					System.out.println(entity.getShortDescription() + " got hit by some minor debris from the grenade blast!");
					// damage in tertiary ring is a quarter that of the primary damage
					entity.takeDamage(grenadeDamage/4);
				}
			}
			
			
			// check rotate clockwise and repeat on those locations
			direction = direction.turn(45);
		}
		
		// set actor's itemCarried to null
		
		a.setItemCarried(null);
		// remove all grenade affordances after throw (grenade becomes useless)
		Affordance[] grenadeAffordances = grenade.getAffordances();
		for (int i = 0; i < grenadeAffordances.length; i++) {
			grenade.removeAffordance(grenadeAffordances[i]);
		}
	}

	
	/**
	 * A String describing what this <code>Throw</code> action will do, suitable for display on a user interface
	 * 
	 * @return String comprising "throw " and the short description of the target of this <code>Affordance</code>
	 */
	@Override
	public String getDescription() {
		return "throw " + target.getShortDescription();
	}
	
	/**
	 * Returns the time is takes to perform this <code>Throw</code> action.
	 * 
	 * @return The duration of the Throw action. Currently hard coded to return 1.
	 */
	public int getDuration() {
		return 1;
	}
	

	
}