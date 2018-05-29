package starwars.entities.actors;

import java.util.ArrayList;
import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWSandcrawlerWorld;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;

public class Droid extends SWActor {
	
	private String name;
	private SWActor droidOwner;
	private Direction wanderDirection;
	private static final int noForce = 0;
	private boolean canBeScavenged = true;

	/**
	 * Create a Droid. If owned and hitpoints > 0, Droids will check
	 * neighbouring locations for droidOwner. If so, Droid will move to
	 * location of droidOwner. Else, do nothing.
	 * @param hitpoints
	 * 			the number of hitpoints of this Droid. 
	 * 			If this decreases to zero, Droid will
	 * 			not die but will remain immobile.
	 * @param name
	 * 			this Droid's name. Used in displaying descriptions.
	 * @param m
	 * 			<code>MessageRenderer</code> to display messages.
	 * @param world
	 * 			the <code>SWWorld</code> world to which this
	 * 			Droid belongs to
	 * @param droidOwner
	 * 			the set owner of the droid, if none set to 
	 * 			null
	 */
	public Droid(int hitpoints, String name, MessageRenderer m, SWWorld world, SWActor droidOwner) {
		super(Team.GOOD, hitpoints, m, world, noForce);
		this.droidOwner = droidOwner;
		this.name = name;
	}
	
	/**
	 * main code for droid to act at each of its turn
	 */
	public void act() {
		// print location
		say(describeLocation());		
			
		// get location of droid
		SWLocation droidLocation = this.world.getEntityManager().whereIs(this);

		// if droid is in the sandcrawler, it does not move
		// if the droid is in the same position as it's owner, it gets out of the sandcrawler
		if (this.getWorld() instanceof SWSandcrawlerWorld) {
			if (this.getDroidOwner().getLocation() == droidLocation) {
				this.getSc().exit(this);
				this.canBeScavenged = false; //setting the droid to cannot be scavenged when it gets out of the sandcrawler until the owner is in SWWorld
			}
			else {
				return;
		}}
	
		else {
			
			//if the droidOwner is in SWWorld, droid can be scavenged
			//if not, droid cannot be scavenged
			if (this.getDroidOwner().getWorld() instanceof SWWorld) {
				this.canBeScavenged = true;
				}
		
			// code to check if in badlands and if so, lose health
			if ((droidLocation.getSymbol() == 'b') && (this.getHitpoints() > 0)) {
				this.takeDamage(1);
			}
			// check if droid hitpoints is zero
			if (isDead()) {
				return; // if hp <= 0, do nothing
			}
			// check whether droid has owner
			if (this.droidOwner == null) {
				return; // if no droidOwner, do nothing
			}
			// if droid has owner
			if (this.droidOwner != null) {
				
				//get owner location
				SWLocation ownerLocation = this.world.getEntityManager().whereIs(droidOwner);
				
				//if same as owner location
				if (droidLocation == ownerLocation) {
					return; // if droidLocation same as ownerLocation, do nothing further
				}
				// otherwise, check surrounding locations for owner
				else {
					CompassBearing d = CompassBearing.NORTHWEST;
					for (int i = 0; i <= 8; i++) {
						d = d.turn(45);
						if (droidLocation.getNeighbour(d) == ownerLocation) {
							System.out.println(this.getShortDescription() + " is with " + droidOwner.getShortDescription() + "!");
							// code to move Droid to location of ownerLocation; 
							Move myMove = new Move(d, messageRenderer, world);
							scheduler.schedule(myMove, this, 1);
							return;
						}
					}
					
					//code to move Droid in a new random direction
					if (droidLocation.getNeighbour(this.wanderDirection) == null) {
						
						ArrayList<Direction> possibledirections = new ArrayList<Direction>();
	
						// build a list of available directions
						for (Grid.CompassBearing direction : Grid.CompassBearing.values()) {
							if (SWWorld.getEntitymanager().seesExit(this, direction)) {
								possibledirections.add(direction);
							}
						}
						this.setWanderDirection(possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size()))));
						
					}
					// move droid
					Move myMove = new Move(wanderDirection, messageRenderer, world);
					scheduler.schedule(myMove, this, 1);
				}
			
			}
		}
	}
	
	public void setWanderDirection(Direction d) {
		this.wanderDirection = d;
	}
	
	public Direction getWanderDirection() {
		return this.wanderDirection;
	}
	
	public void setDroidOwner(SWActor droidOwner) {
		this.droidOwner = droidOwner;
	}
	
	public SWActor getDroidOwner() {
		return this.droidOwner;
	}
	
	@Override
	public String getShortDescription() {
		return name + " the Droid";
	}

	@Override
	public String getLongDescription() {
		return this.getShortDescription();
	}

	private String describeLocation() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();

	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * gets a boolean indicating if the droid can be scavenged
	 * @return true if can, false if cannot
	 */
	public boolean isCanBeScavenged() {
		return canBeScavenged;
	}

	/**
	 * sets if droid can be scavenged
	 * @param canBeScavenged true if can, false if cannot
	 */
	public void setCanBeScavenged(boolean canBeScavenged) {
		this.canBeScavenged = canBeScavenged;
	}

}
