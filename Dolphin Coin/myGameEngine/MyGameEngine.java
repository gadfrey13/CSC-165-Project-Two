package myGameEngine;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import a1.GameObject.GameObject;
import a1.GameObject.ManualMadeGameObjects;
import myGameEngine.CameraControl.CameraControl;
import myGameEngine.CameraControl.ObjectControl.Camera3Pcontroller;
import myGameEngine.CameraControl.ObjectControl.MoveForwardAction;
import myGameEngine.CameraControl.ObjectControl.ObjectControl;
import ray.input.GenericInputManager;
import ray.input.InputManager;
import ray.input.action.Action;
import ray.rage.Engine;
import ray.rage.game.VariableFrameRateGame;
import ray.rage.rendersystem.RenderSystem;
import ray.rage.rendersystem.RenderWindow;
import ray.rage.rendersystem.Renderable.Primitive;
import ray.rage.rendersystem.Viewport;
import ray.rage.rendersystem.gl4.GL4RenderSystem;
import ray.rage.scene.Camera;
import ray.rage.scene.Camera.Frustum.Projection;
import ray.rage.scene.Entity;
import ray.rage.scene.Light;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3f;

public class MyGameEngine extends VariableFrameRateGame {
	

	private MyCamera cameraOne;
	private MyCamera cameraTwo;
	private Camera camera,camera2;
	private SceneNode cameraNode, cameraNode2;
	private CameraControl cameraControl,cameraControl2;
	private InputManager im;
	private GameHub hub1;
	private GameHub hub2;
	private float elapsTime = 0.0f;
	private ObjectControl objectControl;
	private GameObject dolphinOne;
	private Camera3Pcontroller orbitController;
	private Action moveFwdAct;
	private GameObject prize;
	private ManualMadeGameObjects man,man2;
	@Override
	protected void setupWindow(RenderSystem rs, GraphicsEnvironment ge)
	{ 
		rs.createRenderWindow(new DisplayMode(1000, 700, 24, 60), false);
	}

	@Override
	protected void setupCameras(SceneManager sm, RenderWindow rw) {
		SceneNode rootNode = sm.getRootSceneNode();
		
		Camera camera = sm.createCamera("MainCamera",
		Projection.PERSPECTIVE);
		rw.getViewport(0).setCamera(camera);
		camera.setRt((Vector3f)Vector3f.createFrom(1.0f, 0.0f, 0.0f));
		camera.setUp((Vector3f)Vector3f.createFrom(0.0f, 1.0f, 0.0f));
		camera.setFd((Vector3f)Vector3f.createFrom(0.0f,1.0f,-1.0f));
		SceneNode cameraN =
		rootNode.createChildSceneNode("MainCameraNode");
		cameraN.attachObject(camera);
		camera.setPo((Vector3f) Vector3f.createFrom(1.620f, 0.68f, -20.4710f));
		camera.setMode('c');
		camera.getFrustum().setFarClipDistance(1000.0f);
		cameraOne = new MyCamera(camera,cameraN);
		
		//Camera Two
		
		Camera camera2 = sm.createCamera("MainCamera2",
		Projection.PERSPECTIVE);
		rw.getViewport(1).setCamera(camera2);
		camera2.setRt((Vector3f)Vector3f.createFrom(1.0f, 0.0f, 0.0f));
		camera2.setUp((Vector3f)Vector3f.createFrom(0.0f, 1.0f, 0.0f));
		camera2.setFd((Vector3f)Vector3f.createFrom(0.0f, 0.0f, -1.0f));
		camera2.setPo((Vector3f) Vector3f.createFrom(1.620f, 0.0f, 20.4710f));
		SceneNode cameraN2 =
		rootNode.createChildSceneNode("MainCamera2Node");
		cameraN2.attachObject(camera2);
		camera2.setMode('c');
		camera2.getFrustum().setFarClipDistance(1000.0f);
		cameraTwo = new MyCamera(camera2,cameraN2);
		
		
	}

	@Override
	protected void setupScene(Engine eng, SceneManager sm) throws IOException {
		hub1 = new GameHub(eng);
		hub2 = new GameHub(eng);
		im = new GenericInputManager();
		dolphinOne = new GameObject(sm,"dolphinTwo","dolphinHighPoly.obj","player1");
		cameraControl = new CameraControl(cameraOne,im);
		//cameraControl.setUpKeyBoard();
		//objectControl = new ObjectControl(cameraOne,dolphinOne,im);
		//dolphinOne.create();
		
		//man = new ManualMadeGameObjects();
		//man.createTriangle(eng, sm,"TriangleOne");
		//man.getTriangle().rotate(Degreef.createFrom(90), Vector3f.createFrom(1.0f, 0.0f, 0.0f));
		
		man2 = new ManualMadeGameObjects();
		man2.createSquare(eng, sm, "Square");
		man2.getSquare().rotate(Degreef.createFrom(90), Vector3f.createFrom(1.0f, 0.0f, 0.0f));
		//man2 = new ManualMadeGameObjects();
		//man2.createTriangle(eng, sm,"TriangleTwo");
	
		
		//Create A prize
		prize = new GameObject(sm,"sphere","sphere.obj","prize");
		prize.create();
		prize.getNode().setLocalPosition(Vector3f.createFrom(2.0f, 0.0f, 2.0f));
		
		//objectControl.setUpKeyBoard();
		//dolphinOne.getNode().setLocalPosition(Vector3f.createFrom(0.0f, 0.5f, 8.0f));
		//Angle angle = Degreef.createFrom(180);
		//dolphinOne.getNode().rotate(angle,Vector3f.createFrom(0.0f, 1.0f, 0.0f));
		
		//cameraOne.getCamera().getParentNode().lookAt(dolphinOne.getNode());
		
		//cameraControl.setUpKeyBoard();
		//cameraControl.setUpGamePad();
		
		cameraControl2 = new CameraControl(cameraTwo,im);
		cameraControl2.setUpKeyBoard();
		
		// dolphin avatar for player in the top window
		Entity dolphinE = sm.createEntity("dolphin", "dolphinHighPoly.obj");
		dolphinE.setPrimitive(Primitive.TRIANGLES);
		SceneNode dolphinN =
		sm.getRootSceneNode().createChildSceneNode("dolphinNode");
		dolphinN.attachObject(dolphinE);
		dolphinN.yaw(Degreef.createFrom(180.0f));
		dolphinN.setLocalPosition(1.620f, 0.68f, 3.4710f);
		//System.out.println(dolphinN.getWorldScale());
		// earth avatar for player in the bottom window
		Entity earthE = sm.createEntity("earth", "earth.obj");
		earthE.setPrimitive(Primitive.TRIANGLES);
		SceneNode earthN =
		sm.getRootSceneNode().createChildSceneNode("earthNode");
		
		earthN.attachObject(earthE);
		earthN.setLocalPosition(0.0f, 0.0f, .0f);
		earthN.setLocalScale(0.2f, 0.2f, 0.2f);
	
		
		//dolphinN.yaw(Degreef.createFrom(45.0f));
		
		
		sm.getAmbientLight().setIntensity(new Color(.5f, .5f, .5f));

		Light plight = sm.createLight("testLamp1", Light.Type.POINT);
		plight.setAmbient(new Color(.3f, .3f, .3f));
		plight.setDiffuse(new Color(.7f, .7f, .7f));
		plight.setSpecular(new Color(1.0f, 1.0f, 1.0f));
		plight.setRange(5f);

		SceneNode plightNode = sm.getRootSceneNode().createChildSceneNode("plightNode");
		plightNode.attachObject(plight);
		
		setupOrbitCamera(eng,sm);
		setupInputs(sm);
	}
	
	protected void setupOrbitCamera(Engine eng, SceneManager sm) {
		SceneNode dolphinN = sm.getSceneNode("dolphinNode");

		SceneNode cameraN = sm.getSceneNode("MainCamera2Node");
		Camera camera = sm.getCamera("MainCamera2");
		String gpName = im.getFirstGamepadName();
		orbitController = new Camera3Pcontroller(camera, cameraN, dolphinN, gpName, im,1);
		
	}
	
	protected void setupInputs(SceneManager sm) {
		String kbName = im.getKeyboardName();
		String gpName = im.getFirstGamepadName();
		SceneNode dolphinN = getEngine().getSceneManager().getSceneNode("dolphinNode");
		moveFwdAct = new MoveForwardAction(dolphinN);
		im.associateAction(gpName, net.java.games.input.Component.Identifier.Button._3, moveFwdAct,InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		// avatar movement – move forward
		try{
		
		}catch(Exception e){
			
		}
		// other Action classes for avatar instantiated as needed
	}

	@Override
	protected void update(Engine engine) {
		// TODO Auto-generated method stub
		hub1.setHub(15, 15);
		hub2.setHub2(15, 345);
		try{
			cameraControl.getInputManager().update(elapsTime);
			cameraControl2.getInputManager().update(elapsTime);
			//objectControl.getInputManager().update(elapsTime);
			orbitController.updateCameraPosition();
			//im.update(elapsTime);
		}catch(Exception e){
			
		}
		
		
	}
	
	@Override
	protected void  setupWindowViewports(RenderWindow rw){//Passing an object will allow that object to have reference to that object
		rw.addKeyListener(this); //Updates the render window
		
		//Top Part Of the Screen
		Viewport topViewport = rw.getViewport(0);
		topViewport.setDimensions(0.51f, .01f, .99f, 0.49f);//bottom, left, width, height
		topViewport.setClearColor((new Color(1.0f,.7f,.7f)));
		
		Viewport botViewport = rw.createViewport(.01f, .01f, .99f, .49f);
		botViewport.setClearColor(new Color(.5f,1.0f,.5f));
	}
	
	
	  

}
