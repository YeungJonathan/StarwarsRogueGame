package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAffordance;
import starwars.SWEntity;
import starwars.actions.Dip;

/**
 * Class to represent a water reservoir.  <code>Reservoirs</code> are currently pretty passive.
 * They can be dipped into to fill fillable entities (such as <code>Canteens</code>.  They
 * are assumed to have infinite capacity.
 * 
 * @author 	ram
 * @see 	{@link starwars.entities.Canteen}
 * @see {@link starwars.entites.Fillable}
 * @see {@link starwars.actions.Fill} 
 */
public class Reservoir extends SWEntity {

	/**
	 * Constructor for the <code>Reservoir</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Reservoir</code></li>
	 * 	<li>Set the short description of this <code>Reservoir</code> to "a water reservoir</li>
	 * 	<li>Set the long description of this <code>Reservoir</code> to "a water reservoir..."</li>
	 * 	<li>Add a <code>Dip</code> affordance to this <code>Reservoir</code> so it can be taken</li> 
	 *	<li>Set the symbol of this <code>Reservoir</code> to "T"</li>
	 * </ul>
	 * 
	 * @param 	m <code>MessageRenderer</code> to display messages.
	 * @see 	{@link starwars.actions.Dip} 
	 */
	public Reservoir(MessageRenderer m) {
		super(m);
		SWAffordance dip = new Dip(this, m);
		this.addAffordance(dip);	
		
		this.setShortDescription("a water reservoir");
		this.setLongDescription("A water reservoir, full of cool, clear, refreshing water");
		this.setSymbol("W");
		this.setHitpoints(40);
	}
	/**
	 * Method insists damage on this <code>Reservoir</code> by reducing a certain 
	 * amount of <code>damage</code> from this <code>Reservoir</code> <code>hitpoints</code>
	 * <p>
	 * This method will also change this <code>Reservoir</code>s <code>longDescription</code> to
	 * "A damaged water reservoir, leaking slowly"  and this <code>Reservoir</code>s <code>shortDescription</code> to
	 * "a damaged reservoir" if the <code>hitpoints</code> after taking the damage is between zero and twenty.
	 * This method will also change this <code>Reservoir</code>s <code>longDescription</code> to
	 * "The wreckage of a water reservoir, surrounded by slightly damp soil"  and this <code>Reservoir</code>s <code>shortDescription</code> to
	 * "the wreckage of a water reservoir" if the <code>hitpoints</code> after taking the damage is zero or less.
	 * <p>
	 * If the <code>hitpoints</code> after taking the damage is zero or less, this method will remove the 
	 * <code>Dip</code> and affordance from this <code>Reservoir</code> since a completely damaged reservoir
	 * cannot be used to <code>Dip</code>
	 * <p>
	 * 
	 * @author 	Ariffin
	 * @param 	damage the amount of <code>hitpoints</code> to be reduced
	 * @see 	{@link starwars.actions.Attack}
	 */
	@Override
	public void takeDamage(int damage) {
		super.takeDamage(damage);
		
		// if hitpoints lowers to < 20
		if ((this.hitpoints <= 20) && (this.hitpoints > 0)) {
			this.setShortDescription("a damaged water reservoir");
			this.setLongDescription("A damaged water reservoir, leaking slowly");
			this.setSymbol("V");
		}
		
		// if hitpoints lowers to <= 0 
		if (this.hitpoints <= 0) {
			this.setShortDescription("the wreckage of a water reservoir");
			this.setLongDescription("The wreckage of a water reservoir, surrounded by slightly damp soil");
			this.setSymbol("X");
		}
	}

}
