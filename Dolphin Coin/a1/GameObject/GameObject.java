package a1.GameObject;

import java.io.IOException;

import ray.rage.asset.material.Material;
import ray.rage.asset.texture.Texture;
import ray.rage.rendersystem.Renderable.Primitive;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneManager;
import ray.rage.scene.SceneNode;

public class GameObject {
	private String name;
	private String path;
	private SceneManager sm;
	private SceneNode objectNode;
	private String type;
	private Entity gameEntity;
	private int points = 1;
	/***
	 * @param SceneManager
	 * @param name
	 * @param path
	 */
	public GameObject(SceneManager sm,String name, String path,String type){
		this.name = name;
		this.path = path;
		this.sm = sm;
		this.type = type;
	}
	
	/**
	 * Create a game object
	 * @throws IOException
	 */
	public void create() throws IOException{
		gameEntity = sm.createEntity(name, path);
		gameEntity.setPrimitive(Primitive.TRIANGLES);
		objectNode = sm.getRootSceneNode().createChildSceneNode(name+"Node");
		objectNode.attachObject(gameEntity);
	}
	
	/**
	 * Add texture to the object
	 * @throws IOException 
	 */
	public void addTexture(String texture) throws IOException{
		Texture tex = sm.getTextureManager().getAssetByPath(texture);
		TextureState texState = (TextureState) sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
		texState.setTexture(tex);
		gameEntity.setRenderState(texState);
	}
	
	public void addMaterial(String material) throws IOException{
		Material mat = sm.getMaterialManager().getAssetByPath(material);
		gameEntity.setMaterial(mat);
	}
	
	/**
	 * 
	 */
	
	public SceneNode getNode(){
		return objectNode;
	}
	
	/**
	 * Get the type 
	 */
	public String getType(){
		return type;
	}
	
	public void setPoints(int points){
		this.points = points;
	}
	
	public int getPoints(){
		return points;
	}
	
	public void minusPoint(){
		points--;
	}
}
