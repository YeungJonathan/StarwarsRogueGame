package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Exit;

/**
 * An entity that lets an actor of SWActor class exit the sandcrawler
 * 
 * @author J3
 */
public class Door extends SWEntity{

	/**
	 * Constructor for the Door class
	 * makes a door entity
	 * has capability of door 
	 * has affordance to exit the sandcrawler
	 * 
	 * actor must be on the same location of the door in order to exit the sandcrawler
	 * the door does not move
	 * the door is position at (0,0) inside the sandcrawler
	 * @param m: the MessageRenderer
	 */
	public Door(MessageRenderer m) {
		super(m);
		this.shortDescription = "a door";
		this.longDescription = "A door for entering and exiting the Sandcrawler";
		capabilities.add(Capability.DOOR);
		this.addAffordance(new Exit(this,m));
		
		// TODO Auto-generated constructor stub
	}

}
