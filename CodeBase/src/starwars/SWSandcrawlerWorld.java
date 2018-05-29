package starwars;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.entities.Door;

/**
 * the world inside the sandcrawler which extends SWWorld
 * a 2 by 2 grid
 * has a seperate entitymanager from SWWorld
 * 
 * @author J3
 */

public class SWSandcrawlerWorld extends SWWorld{
	
	private static final int x = 2;		//width of the grid
	private static final int y = 2;		//length of the grid
	private static final EntityManager<SWEntityInterface, SWLocation> entityManager = new EntityManager<SWEntityInterface, SWLocation>();
	
	/**
	 * Constructor for the SWSandcrawlerWorld
	 * calls the super class constructor with a grid size of 2 and 2
	 */
	public SWSandcrawlerWorld() {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	/**
	 * initializes the description of different grids in the world
	 * makes a new door entity inside the world for exiting the world
	 * door at location (0,0)
	 * 
	 * @param iface: the messagerenderer for the world
	 */
	public void initializeWorld(MessageRenderer iface) {
		SWLocation loc;		
		this.setLocationString("SandCrawler");
		Door door = new Door(iface);
		door.setSymbol("D");
		loc = this.getGrid().getLocationByCoordinates(0,0);
		this.getEntityManager().setLocation(door, loc);
	}
	/**
	 * getters for entitymanager
	 * @return the entitymanager of the sandcrawler world
	 */
	public static EntityManager<SWEntityInterface, SWLocation> getEntitymanager() {
		return entityManager;
	}
	
}
