package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Take;


public class Grenade extends SWEntity {

	/**
	 * Constructor for the <code>Grenade</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Grenade</code></li>
	 * 	<li>Set the short description of this <code>Grenade</code> to "a grenade"</li>
	 * 	<li>Set the long description of this <code>Grenade</code> to "A shiny new grenade"</li>
	 * 	<li>Set the hit points of this <code>Grenade</code> to 100</li>
	 * 	<li>Add a <code>Take</code> affordance to this <code>Grenade</code> so it can be taken</li> 
	 *	<li>Add a <code>THROWABLE Capability</code> to this <code>Grenade</code> so it can be used to <code>Attack</code></li>
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link starwars.actions.Take}
	 * @see {@link starwars.Capability}
	 */
	public Grenade(MessageRenderer m) {
		super(m);
		
		this.shortDescription = "a grenade";
		this.longDescription = "A shiny new grenade";
		this.hitpoints = 20;
		
		this.addAffordance(new Take(this, m));//add the Take affordance so that the blaster can be picked up
		
													//the blaster has capabilities 
		this.capabilities.add(Capability.THROWABLE);   // and WEAPON so that it can be used to attack
	}
	
	
	/**
	 * A symbol that is used to represent the Grenade on a text based user interface
	 * 
	 * @return 	Single Character string "g"
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	public String getSymbol() {
		return "g"; 
	}


}
