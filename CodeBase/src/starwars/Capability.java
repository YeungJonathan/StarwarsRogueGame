package starwars;
/**
 * Capabilities that various entities may have.  This is useful in <code>canDo()</code> methods of 
 * <code>SWActionInterface</code> implementations.
 *  
 * @author 	ram
 * @author 	Ariffin (27/05/2018) added THROWABLE capibility
 * @see 	SWActionInterface
 * @see     starwars.entities.Fillable
 */

public enum Capability {
	CHOPPER,//CHOPPER capability allows an entity to Chop another entity which has the Chop Affordance
	WEAPON,//WEAPON capability allows an entity to Attack another entity which has the Attack Affordance
	FILLABLE,//FILLABLE capability allows an entity to be refilled by another entity that 
	            // has the Dip affordance.  Any FILLABLE Entity MUST implement the Fillable interface
	DRINKABLE,//DRINKABLE capability allows an entity to be consumed by another entity 
	THROWABLE,//THROWABLE capability allows an entity to throw an entity that is THROWABLE
	DOOR 	// a door in which cannot be use to attack nor pick up
	// use for transferring through worlds
}
