package myGameEngine.CameraControl.ObjectControl;

import myGameEngine.MyCamera;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rage.scene.SceneNode;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class MoveLeftYawNodeAction extends AbstractInputAction {
	private Camera camera;
	private SceneNode node;
	private Vector3f camObjectDistance;
	private MyCamera mycam;
	private SceneNode cameraNode;
	public MoveLeftYawNodeAction(Camera c, SceneNode n,Vector3f camObjectDistance){
		camera = c;
		node = n;
		this.camObjectDistance = camObjectDistance;
	}
	public MoveLeftYawNodeAction(MyCamera mycam, SceneNode n,Vector3f camObjectDistance){
		camera = mycam.getCamera();
		node = n;
		this.camObjectDistance = camObjectDistance;
		this.mycam = mycam;
		this.cameraNode = mycam.getCameraNode();
	}
	@Override
	public void performAction(float arg0, Event arg1) {

		System.out.println("Move Yaw Left");
		System.out.println("CameraNode Location" + cameraNode.getLocalPosition());
		Angle angle = Degreef.createFrom(1.0f);
		node.yaw(angle);
		Vector3f local = (Vector3f) node.getLocalUpAxis();
		System.out.println(node.getLocalPosition());
		Vector3f addPosition = (Vector3f) Vector3f.createFrom(camObjectDistance.x(), camObjectDistance.y(), camObjectDistance.z());
		Vector3f newPo = (Vector3f) node.getLocalPosition();
		newPo = (Vector3f) newPo.sub(addPosition);
		
		Vector3 rn = node.getLocalRotation().row(0);
		Vector3 fn = node.getLocalRotation().row(2);
		camera.setFd((Vector3f)Vector3f.createFrom(fn.x(),fn.y(),fn.z()));
		camera.setRt((Vector3f)Vector3f.createFrom(rn.x(),rn.y(),rn.z()));
		
		//this.cameraNode.setLocalPosition(newPo);
		//this.cameraNode.yaw(angle);
		//camera.setPo((Vector3f) this.cameraNode.getLocalPosition());
		
		System.out.println("Rotation " + node.getLocalRotation());
		System.out.println("Z axis " + node.getLocalForwardAxis());
		System.out.println("X axis " + node.getLocalRightAxis());
		System.out.println("Y axis " + node.getLocalUpAxis());	
		System.out.println("NodeCamera " + mycam.getCameraNode().getLocalPosition());
		System.out.println("Camera " + camera.getPo());
		
	}
}
