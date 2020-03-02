package DataStructure;

import java.util.ArrayList;

public class TreeNode{
	String name;
	TreeNode parent;
	ArrayList<TreeNode> childs;
	
	
	public TreeNode(){
		
	}
	public TreeNode(String name, TreeNode parent){
		this.name = name;
		this.parent=parent;
		this.childs = null;
	}
	public void setChilds(ArrayList<String> name){
		this.childs = new ArrayList<TreeNode>();
		for(String temp:name){
		childs.add(new TreeNode((temp),this));
		}
	}
	public void addChilds(String name){
		if(this.childs!=null){
			this.childs.add(new TreeNode(name,this));
		}else{
			this.childs = new ArrayList<TreeNode>();
			this.childs.add(new TreeNode(name,this));
		}
	}
	public TreeNode getParent(){
		return this.parent;
	}
	public ArrayList<TreeNode> getChilds(){
		return this.childs;
	}
	public String getName(){
		return this.name;
	}
}