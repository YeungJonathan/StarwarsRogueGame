package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.Sandcrawler;
/**
 * Enter Class
 * Called when an Actor is in the same location of the sandcrawler
 * Code that lets an Actor enter the SWSandcrawlerWorld
 * 
 * @author Jonathan Yeung
 */
public class Enter extends SWAffordance{

	/**
	 * Constructor for the <code>Enter</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param m the message renderer to display messages
	 */
	public Enter(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * Returns if the action can be performed
	 * Returns true if the user can go inside the SWSandcrawlerWorld
	 * True if only the actor is not dead and force level is not 0
	 * Returns false if the user cannot
	 * 
	 * @param a: An Actor of SWActor class
	 */
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return (!(a.isDead()) && a.hasForce());
	}

	@Override
	/**
	 * if the target is sandcrawler
	 * go inside the sandcrawler
	 * this method should be called if only the actor is not dead and force level not 0
	 * calls the enter method in Sandcrawler class
	 * 
	 * @param a: An Actor of SWActor class
	 */
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		target = this.getTarget();
		if (target instanceof Sandcrawler) {
			// go inside the sandcrawler
			((Sandcrawler) target).enter(a);
		}
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author Jonathan
	 * @return String comprising "Enter " and the short description of the target of this <code>Enter</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "enter " + target.getShortDescription();
	}

}
