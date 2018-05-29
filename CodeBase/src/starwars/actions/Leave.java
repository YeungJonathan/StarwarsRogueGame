package starwars.actions;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> leave an object.
 * 
 * @author Jonathan
 */
/*
 * Changelog
 * 2017/01/26	- candDo method changed. An actor can only take if it's not holding any items already.
 * 				- act method modified. Take affordance removed from the item picked up, since an item picked up
 * 				  cannot be taken. This is just a safe guard.
 * 				- canDo method changed to return true only if the actor is not carrying an item (asel)
 */
public class Leave extends SWAffordance {

	/**
	 * Constructor for the <code>Leave</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being left
	 * @param m the message renderer to display messages
	 */
	public Leave(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}


	/**
	 * Returns if or not this <code>Leave</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying an item already.
	 *  
	 * @author 	Jonathan (11/05/2018)
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is can leave this item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a.getItemCarried()!=null;
	}

	/**
	 * Perform the <code>Leave</code> action by setting the item carried by the <code>SWActor</code> to null (
	 * the <code>SWActor a</code>'s item carried would be the target of this <code>Leave</code>).
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	Jonathan (11/05/2018)
	 * @author 	Ariffin (27/05/2018) edited for Grenade implementation
	 * @param 	a the <code>SWActor</code> that is leaving the target
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.SWActor#isDead()}
	 */
	@Override
	public void act(SWActor a) {
		// add the target back to the entity manager
		if (target instanceof SWEntityInterface) {
			
			// remove all affordances associated with this item
			Affordance[] itemAffordances = target.getAffordances();
			for (int i = 0; i < itemAffordances.length; i++) {
				target.removeAffordance(itemAffordances[i]);
			}
			
			//add the take affordance
			target.removeAffordance(this);
			Take take = new Take((SWEntityInterface) target, messageRenderer);
			target.addAffordance(take);
			
			
			a.setItemCarried(null);
			SWAction.getEntitymanager().setLocation((SWEntityInterface) target, a.getLocation());
			
		}
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author Jonathan
	 * @return String comprising "Leave " and the short description of the target of this <code>Leave</code>
	 */
	@Override
	public String getDescription() {
		return "leave " + target.getShortDescription();
	}

}
