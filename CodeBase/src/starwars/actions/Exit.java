package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.Door;
/**
 * Enter Class
 * Called when an Actor is in inside the sandcrawler 
 * and is on the same spot as the door
 * Code that lets an Actor exits the SWSandcrawlerWorld
 * and enter the SWWorlds
 * 
 * @author Jonathan Yeung
 */
public class Exit extends SWAffordance{

	/**
	 * Constructor for the <code>Exit</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param m the message renderer to display messages
	 */
	public Exit(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * will return true all the time 
	 * because the SWActor a can choose to exit anytime he wants
	 * as long as it is on the door.
	 * does not need to check if actor is alive or have force or not
	 * because it was checked when the actor enters the sandcrawler
	 * 
	 * @param a: An Actor of SWActor class
	 */
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * if the target is door
	 * leave the sandcrawler
	 * does not need to check if actor is alive or have force or not
	 * because it was checked when the actor enters the sandcrawler
	 * calls the exit method in SWActor class
	 * 
	 * @param a: An Actor of SWActor class
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		if (target instanceof Door) {
//			 exit the sandcrawler
			a.exitsc();
		}
	}
//	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author Jonathan
	 * @return String comprising "Exit " and the short description of the target of this <code>Exit</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "exit " + target.getShortDescription();
	}

}
