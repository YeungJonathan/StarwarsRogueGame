package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.entities.actors.BenKenobi;
import edu.monash.fit2099.simulator.space.Location;

/**
 * Train Class
 * A class that trains an actor to raise their force ability
 * At this stage only Ben can train Luke
 * @author Jonthan 
 */
public class Train extends SWAction implements SWActionInterface{
	
	/**
	 * whenever an actor has been trained, the force level of the trainee will be increased by 20
	 */
	int trainForce = 20;

	/**
	 * Constructor for <code>Train</code> class.
	 * Makes a train class object
	 * set the priority to 1
	 * @param m <code>MessageRenderer</code> to display messages.
	 */
	public Train(MessageRenderer m) {
		super(m);
		priority = 1;
	}
	
	/**
	 * Method that gets the duration of the training
	 * @return the duration of the training ( which is 1 )
	 */
	@Override
	public int getDuration() {
		return 1;
	}

	/**
	 * Method that gets the description of the action
	 * @return a string that prompts user selection when training is available
	 */
	@Override
	public String getDescription() {
		return "Train by Luke";
	}

	/**
	 * A Method that checks if training is available
	 * Checks if location of ben and Luke are the same
	 * Checks if forcelevel of luke is less than 100
	 * checks if they are on the same team
	 * 
	 * @param trainer a SWActor class object that is being trained
	 * @return true if training is available
	 * @return false if training is not available
	 */
	@Override
	public boolean canDo(SWActor trainer) {
		Location benLocation = BenKenobi.getBenKenobi().getLocation();
		Location trainerLocation = trainer.getLocation();
		if (benLocation == trainerLocation) {
			if (trainer.getForceLevel() < 100) {
				if (trainer.getTeam() == BenKenobi.getBenKenobi().getTeam()) {
					return true;
				}
		return false;
			}
		}
		return false;
	}

	/**
	 * Perform the Train command on an actor
	 * This method does not train an actor if 
	 * 1) force level of the actor is already full,
	 * 2) the trainee and trainer are not on the same team
	 * 3) their location are not the same
	 * 
	 * else it will increase the trainee's force level by 20 
	 * or maximum if they already have force level greater than 80 and less than 100
	 * @param trainer a SWActor class object that is being trained
	 * prints out the current force level statement
	 */
	@Override
	public void act(SWActor trainee) {
		// TODO Auto-generated method stub
		if (trainee.getForceLevel() >80 && trainee.getForceLevel() < 100) {
			trainee.setForceLevel(100);
			trainee.say("Luke's force level have been increased to maximum");
		}
		else {
			trainee.setForceLevel(trainee.getForceLevel()+trainForce);	
			trainee.say("Luke's force level have been increased to " + trainee.getForceLevel());
			}
		}
}
