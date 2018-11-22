package dataTree.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class Matrix2D {
	private String[] colnames;
	private LinkedNode2D[][] dMatrix;
	private int xsize = 0;
	private int ysize = 0;
	
	public String[] getColnames() {
		return colnames;
	}

	public LinkedNode2D[][] getDMatrix() {
		return dMatrix;
	}

	public int getXsize() {
		return xsize;
	}

	public int getYsize() {
		return ysize;
	}

	public void setColnames(String[] colnames) {
		this.colnames = colnames;
	}

	public void setDMatrix(LinkedNode2D[][] dMatrix) {
		this.dMatrix = dMatrix;
	}

	public void setXsize(int xsize) {
		this.xsize = xsize;
	}

	public void setYsize(int ysize) {
		this.ysize = ysize;
	}
	
	private void computeProp(List<Map<String, Object>> rows, String[] fieldsName) {
		this.xsize = fieldsName.length;
		this.ysize = rows.size();
		this.colnames = fieldsName;
		this.dMatrix = new LinkedNode2D[xsize][ysize];
	}

	public void initSortedAndDistinct(List<Map<String, Object>> rows, String[] fieldsName) {
//		if (rows.size() == 0 || fieldsName.length == 0) return;
		computeProp(rows, fieldsName);
		for (int y = 0; y < ysize; y++) {
			Map<String, Object> row = rows.get(y);
			for (int x = 0; x < xsize; x++) {
				String key = colnames[x];
				Object obj = row.get(key);
				if (obj == null) continue;
				LinkedNode2D node2d = null;
				if (y != 0) {
					LinkedNode2D last = dMatrix[x][y-1];
					for (int i = 2; last == null; i++)
						last = dMatrix[x][y-i];
					if (obj.equals(last.getContent())) {
						continue;
					}
				}
				if (x == 0) {
					node2d = new LinkedNode2D(null, new int[]{x, y}, obj);
				} else {
					LinkedNode2D prev = dMatrix[x-1][y];
					for (int i = 1; prev == null; i++) {
						prev = dMatrix[x-1][y-i];
					}
					node2d = new LinkedNode2D(prev.getIndex(), new int[]{x, y}, obj);
				}
				
				dMatrix[x][y] = node2d;
			}
		}
	}
	
	
	private void list2Matrix(List<Map<String, Object>> rows, String[] fieldsName) {
		computeProp(rows, fieldsName);
		// TODO
	}
	
	private void sort() {
		int[] rowIndexMapper = new int[this.ysize];
		// TODO
	}
	
	public void print() {
		for (int y = 0; y < getYsize(); y++) {
			for (int x = 0; x < getXsize(); x++) {
				String ln2d = dMatrix[x][y] == null? "null" : (String) dMatrix[x][y].getContent();
				if (x == getXsize()-1)
					System.out.println(ln2d);
				else
					System.out.print(ln2d + " | ");
			}
		}
	}
	
	public static void main(String[] args) {
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(new HashMap<String, Object>(){{put("year", "2018"); put("campus", "主校区"); put("college", "文学院"); put("major_code", "1001"); put("major_name", "汉语言"); put("name", "坂田银时");}});
		list.add(new HashMap<String, Object>(){{put("year", "2018"); put("campus", "主校区"); put("college", "文学院"); put("major_code", "1001"); put("major_name", "汉语言"); put("name", "志村新八");}});
		list.add(new HashMap<String, Object>(){{put("year", "2018"); put("campus", "主校区"); put("college", "理工院"); put("major_code", "1011"); put("major_name", "概率论"); put("name", "神乐");}});
		list.add(new HashMap<String, Object>(){{put("year", "2018"); put("campus", "主校区"); put("college", "理工院"); put("major_code", "1011"); put("major_name", "概率论"); put("name", "志村妙");}});
		Matrix2D m = new Matrix2D();
		m.initSortedAndDistinct(list, new String[]{"year","campus","college","major_code","major_name","name"});
		m.print();
		// use gson
		/*Gson gson = new Gson();
		String json = gson.toJson(m);
		System.out.println(json);*/
	}

	
}
