package DataStructure;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Tree {
	private TreeNode root;
	private TreeNode target;

	public TreeNode buildTree(String name) {
		this.root = new TreeNode(name, null);
		return this.root;
	};

	public void setRoot(TreeNode _root) {
		this.root = _root;
	}

	public TreeNode getRoot() {
		return this.root;
	}

	public TreeNode searchTree(TreeNode subTree, String name) {
		if (subTree.getName().equals(name))
			return subTree;
		ArrayList<TreeNode> childs = subTree.getChilds();
		TreeNode temp;
		if (childs != null) {
			for (int i = 0; i < childs.size(); i++) {
				temp = searchTree(childs.get(i), name);
				if (temp != null)
					return temp;
			}
		}
		return null;
	}

	public void printNode(TreeNode subTree) {
		System.out.println(subTree.getName());
		if (subTree.getChilds() != null) {
			for (int i = 0; i < subTree.getChilds().size(); i++) {
				printNode(subTree.getChilds().get(i));
			}
		}
	}

	public TreeNode searchTreeByBFS(TreeNode subTree, String name) {
		Queue<TreeNode> BFS = new ArrayDeque<TreeNode>();
		BFS.offer(subTree);
		while (!BFS.isEmpty()) {
			TreeNode temp = BFS.poll();
			if (temp.getName().equals(name))
				return temp;

			if (temp.getChilds() != null) {
				for (int i = 0; i < temp.getChilds().size(); i++) {
					BFS.offer(temp.getChilds().get(i));
				}
			}
		}
		return null;

	}
}
