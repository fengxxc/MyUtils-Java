package dataTree.model;

import java.util.Map;

public class LinkedNode2D {
	private int[] prevIndex;
	private int[] index;
	Object content;
	Map<String, Object> attributs;
	
	public LinkedNode2D() {
		super();
	}
	public LinkedNode2D(int[] prevIndex, int[] index, Object content) {
		super();
		this.prevIndex = prevIndex;
		this.index = index;
		this.content = content;
	}
	
	public LinkedNode2D(int[] prevIndex, int[] index, Object content, Map<String, Object> attributs) {
		super();
		this.prevIndex = prevIndex;
		this.index = index;
		this.content = content;
		this.attributs = attributs;
	}
	public int[] getPrevIndex() {
		return prevIndex;
	}
	public int[] getIndex() {
		return index;
	}
	public Object getContent() {
		return content;
	}
	public Map<String, Object> getAttributs() {
		return attributs;
	}
	public void setPrevIndex(int[] prevIndex) {
		this.prevIndex = prevIndex;
	}
	public void setIndex(int[] index) {
		this.index = index;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public void setAttributs(Map<String, Object> attributs) {
		this.attributs = attributs;
	}
	
}
