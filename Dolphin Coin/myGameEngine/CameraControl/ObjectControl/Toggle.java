package myGameEngine.CameraControl.ObjectControl;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;

public class Toggle extends AbstractInputAction {
	private boolean  bol = false;
	private int count = 0;
	@Override
	public void performAction(float arg0, Event arg1) {
		
	}
	
	public boolean getTogle(){
		return bol;
	}
	
}
