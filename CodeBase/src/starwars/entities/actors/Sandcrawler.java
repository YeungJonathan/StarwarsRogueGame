package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWSandcrawlerWorld;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Enter;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.Patrol;
import starwars.swinterfaces.SWGridController;
import starwars.entities.Door;
import starwars.entities.actors.Droid;
/**
 * SandCrawler class which extends SWActor
 * 
 *@author Jonathan Yeung
 */

public class Sandcrawler extends SWActor {

	private Patrol path;							//path of the sandcrawler
	private SWSandcrawlerWorld scWorld;			//the sandcrawler world
	private static final int noForce = 0;
	private SWGridController swuiController;		//ui for the SWWorld
	private SWGridController scuiController;		//ui for the Sandcrawler world
	private Scheduler swScheduler;				//scheduler for the SWWorld
	private Scheduler scScheduler;				//scheduler for the sandcrawler world
	private boolean stay = true;
	
	/**
	 * Contructor for making a new sandcrawler object
	 * Jawas takes in droid objects and put it inside the sandcrawler immobile
	 * SandCrawler is on Team neutral and has no force which cannot attack
	 * @param m: MessageRender 
	 * @param world: the world that the sandcrawler is in
	 * @param moves: the walking pattern that the sandcrawler will be walking in 
	 */
	public Sandcrawler(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.NEUTRAL, 100, m, world, noForce);
		path = new Patrol(moves);
		this.setShortDescription("sandcrawler");
		this.setLongDescription("A sandcrawler, a vehicle used by Jawas to scavenge Droids");
		this.addAffordance(new Enter(this, m));
		this.scWorld = new SWSandcrawlerWorld();	
		this.scScheduler = new Scheduler(1, scWorld);

	}
	
	/**
	 * Method to describe the location of the SandCrawler
	 * @return a string representation of the description of the sandcrawler
	 */
	private String describeLocation() {
		SWLocation location = this.getLocation();
		return this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription();
	}

	@Override
	/**
	 * if sandcrawler is dead, it will say in the same place
	 * if sandcrawler is in the same location as droid, it will take the droid inside the sandcrawlwer
	 * sandcrawler move every 2 turns
	 */
	public void act() {
		// TODO Auto-generated method stub
		
		//if sandcrawler is dead just stay at the location and do nothing
		if (isDead()) {
			return;
		}
		say(describeLocation());
		
		//if the location of droid is the same sandcrawler
		//and if the sandcrawler can be scavenged,
		//remove the droid from the SWWorld and put it inside the sandcrawlerWorld
		
		SWLocation location = this.getLocation();
		List<SWEntityInterface> contents = this.world.getEntityManager().contents(location);
		for (SWEntityInterface entity : contents) {
			if (entity instanceof Droid) {
				if (((Droid) entity).isCanBeScavenged() == true) {
					this.setActorScWorld((SWActor) entity, 1, 1);
					this.say("The jawas have scavenged "+entity.getShortDescription());
				}
			}
		
		//sandcrawler move every 2 turns
		if (this.stay == true){
			this.stay = false;
		}
		
		//sandcrawler moving
		else if (this.stay == false) {
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);
			scheduler.schedule(myMove, this, 1);
			this.stay = true;
		}
	}}
	
	/**
	 * precondition: x and y should be either 0 or 1
	 * method to put a actor of SWActor class inside the sandcrawler
	 * remove actor from the SWWorld 
	 * setlocation of actor in the SWSandcrawlerWorld
	 * @param a: an actor which is in SWActor class
	 * @param x: x coordinate of the 2 by 2 grid
	 * @param y: y coordinate of the 2 by 2 grid
	 */
	private void setActorScWorld(SWActor a, int x, int y) {
		
		if ((x!=0) && (x!=1)) {
			throw new IndexOutOfBoundsException("x should be either 0 or 1");
		}
		if ((y!=0) && (y!=1)) {
			throw new IndexOutOfBoundsException("y should be either 0 or 1");
		}
		
		this.world.getEntityManager().remove(a);
		SWLocation loc = this.scWorld.getGrid().getLocationByCoordinates(x,y);
		this.scWorld.getEntityManager().setLocation(a, loc);
		a.setSc(this);
		a.setWorld(this.scWorld);
	}
	
	/**
	 * method to remove Actor from the SWSandcrawlerWorld
	 * set actor back in the SWWorld with the current position of the Sandcrawler
	 * @param a: An Actor of class SWActor
	 */
	private void removeActorScWorld(SWActor a) {
		this.scWorld.getEntityManager().remove(a);
		a.setSc(null);
		this.world.getEntityManager().setLocation(a, this.getLocation());
		a.setWorld(this.world);
	}

	/**
	 * calls this method when actor enters the SWSandcrawlerWorld
	 * calls setActorScWorld method to put actor inside sandcrawler
	 * makes a new uiController for the SWSandcrawlerWorld
	 * sets the scheduler of the SWSandcrawlerWorld
	 * @param a: An Actor of class SWActor
	 */
	public void enter(SWActor a){
		
		this.setActorScWorld(a, 0, 0);		//sets the Actor to location where the door is 
		this.scuiController = new SWGridController(this.scWorld, false); //added param false to ignore banner
		a.setMessageRenderer(this.scuiController);		//set the Message renderer to the sandcrawler world
		a.resetMoveCommands(this.scWorld.getEntityManager().whereIs(a));	//resets the move command for the actor
		
		//if the actor is of player class
		//sets the scheduler for the SWSandcrawlerWorld
		//initialize the world with the uiController
		if (a instanceof Player) {					
			swScheduler = SWActor.scheduler;
			SWActor.setScheduler(scScheduler);
			this.scWorld.initializeWorld(scuiController);
		}
		while (true) {
			scuiController.render();
			scScheduler.tick();
		}
		}
	
	/**
	 * calls this method when actor exits the SWSandcralwerWorld
	 * calls removeActorSCWorld() method to put actor inside SWWorld
	 * if actor is player, change the uiController and messageRenderer back to the SWWorld
	 * if actor is player, delete the door entity
	 * @param a: An Actor of class SWActor
	 */
	public void exit(SWActor a) {
		this.removeActorScWorld(a);
		a.resetMoveCommands(this.world.getEntityManager().whereIs(a));	//resets the move command for the actor
		
		// if actor is of Player class
		if (a instanceof Player) {
			swuiController = new SWGridController(this.world, false);	//makes a new ui controller for the SWWorld, also automatically sets the ui
			a.setMessageRenderer(swuiController);				//changes the message renderer
			SWActor.setScheduler(swScheduler);				//sets the scheduler for the SWWorld class
			
			//removing the door inside the sandcrawler every time the player leaves the SWSandcrawlerWorld
			SWLocation location = this.scWorld.getGrid().getLocationByCoordinates(0,0);
			List<SWEntityInterface> contents = this.scWorld.getEntityManager().contents(location);
			for (SWEntityInterface entity : contents) {
				if (entity instanceof Door) {
					this.scWorld.getEntityManager().remove(entity);
				}
			while (true) {
				swuiController.render();
				swScheduler.tick();	
			}
			}}

}
}