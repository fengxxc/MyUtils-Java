package dataTree.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TreeNode {
	private String id = null;
	private String[] levelNames;
	private Object content;
	private Map<String, Object> data = new HashMap<String, Object>();
	private int level = 0; // 第几层
	private int branchCount = 0; // 末端分支数
	private Set<TreeNode> children = new HashSet<TreeNode>();
	
	public TreeNode() {}
	
	public TreeNode(String id, Object content, int level, String[] levelNames, Map<String, Object> data) {
		super();
		this.id = id;
		this.content = content;
		this.level = level;
		this.levelNames = levelNames;
		this.data = data;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public String[] getLevelNames() {
		return levelNames;
	}
	public void setLevelNames(String[] oColNames) {
		this.levelNames = oColNames;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getId() {
		return id;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public int getBranchCount() {
		return branchCount;
	}
	public Set<TreeNode> getChildren() {
		return children;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public void setBranchCount(int branchCount) {
		this.branchCount = branchCount;
	}
	public void setChildren(Set<TreeNode> children) {
		this.children = children;
	}
	public void addChildren(TreeNode children) {
		this.children.add(children);
	}

	private void depthFirstTraversal(TreeNode root, int totalLevel, int level, Function fn) {
		// do something
		fn.opera(root, totalLevel, level);
		for (TreeNode child : root.getChildren()) {
			depthFirstTraversal(child, totalLevel, level+1, fn);
		}
	}
	
	public void print(int maxLevel) {
		Function print = (node, totalLevel, level) -> {
			if (level == totalLevel-1) {
				System.out.println(level + " " + node.getContent()); 
			} else {
				System.out.print(level + " " + node.getContent() + "	");
			}
		};
		depthFirstTraversal(this, maxLevel, 0, print);
	}
	
	public interface Function {
		void opera(TreeNode node, int totalLevel, int level);
	}
	
}
