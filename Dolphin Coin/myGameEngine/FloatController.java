package myGameEngine;

import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;

import a1.GameObject.GameObject;
import ray.rage.scene.Node;
import ray.rage.scene.SceneNode;
import ray.rage.scene.controllers.AbstractController;
import ray.rml.Vector3f;

public class FloatController extends AbstractController {
	
	private float height;
	private float value = 0.0f;
	private Iterator itr;
	private float move = 0.0f;
	private Vector3f intialLoc;
	private int keep = 0;
	public FloatController(){
	}
	
	@Override
	protected void updateImpl(float arg0) {
		itr = this.controlledNodesList.iterator();
		
		while(itr.hasNext()){
		   Node node = (Node) itr.next();
		   floatAway(node);
		}
	}
	
	public void floatAway(Node node){
		node.moveUp(0.01f);
	}

}
