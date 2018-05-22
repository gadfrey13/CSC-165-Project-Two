package myGameEngine.CameraControl.ObjectControl;

import myGameEngine.MyCamera;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Vector3f;

public class MoveBackwardNodeAction extends AbstractInputAction {
	private Camera camera;
	private SceneNode node;
	private Vector3f camObjectDistance;
	private MyCamera mycam;
	public MoveBackwardNodeAction(Camera c, SceneNode n, Vector3f camObjectDistance){
		camera = c;
		node = n;
		this.camObjectDistance = camObjectDistance;
	}
	
	public MoveBackwardNodeAction(Camera mycam, SceneNode n){
		camera = mycam;
		node = n;
	}
	@Override
	public void performAction(float arg0, Event arg1) {
		System.out.println("Move Node Backward");
		node.moveBackward(0.02f);
		/*
		System.out.println(node.getLocalPosition());
		//Vector3f addPosition = (Vector3f) Vector3f.createFrom(camObjectDistance.x(), camObjectDistance.y(), camObjectDistance.z());
		//Vector3f newPo = (Vector3f) node.getLocalPosition();
		//newPo = (Vector3f) newPo.sub(addPosition);
		//camera.setPo((Vector3f) newPo);
		
		System.out.println("Rotation " + node.getLocalRotation());
		System.out.println("Z axis " + node.getLocalForwardAxis());
		System.out.println("X axis " + node.getLocalRightAxis());
		System.out.println("Y axis " + node.getLocalUpAxis());		
		*/
	}

}
