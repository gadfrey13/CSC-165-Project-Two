package myGameEngine.CameraControl.ObjectControl;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3f;

public class MoveForwardNodeAction extends AbstractInputAction {
	
	private SceneNode node,cameraNode;
	private Camera camera;
	private Vector3f camObjectDistance;
	public MoveForwardNodeAction(Camera c,SceneNode n,Vector3f camObjectDistance){
		node = n;
		camera = c;
		this.camObjectDistance = camObjectDistance;
	}
	
	public MoveForwardNodeAction(Camera c, SceneNode n){
		this.node = n;
		this.camera = c;
	}

	@Override
	public void performAction(float time, Event e) {
		System.out.println("Move Node Forward");
		System.out.println("Move the dolphin forward ");
		System.out.println("Move the " + e.getComponent());


		node.moveForward(0.1f);
	}

}
