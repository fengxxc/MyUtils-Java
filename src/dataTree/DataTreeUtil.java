package dataTree;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.gson.Gson;

import dataTree.model.TreeNode;

public class DataTreeUtil {
		
	public static TreeNode list2Tree(List<Map<String, Object>> rows, String[] fieldsName, String fieldSplSyx) {
		TreeNode tree = new TreeNode();
		tree.setId("1");
		tree.setLevelNames(new String[]{"root"});
		tree.setLevel(0);
		tree.setContent("root");
		HashMap<String, Object> m = new HashMap<>();
		m.put("fieldsName", fieldsName);
		tree.setData(m);
		Set<Integer> rowsindex = new HashSet<Integer>();
		for (int i = 0; i < rows.size(); i++)
			rowsindex.add(i);
		
		int branchCount = list2Tree(rows, fieldsName, tree, rowsindex, 0, fieldSplSyx); // branchCount：总分支数
		return tree;
	}
	public static TreeNode list2Tree(List<Map<String, Object>> rows, String[] fieldsName) {
		return list2Tree(rows, fieldsName, null);
	}

	/**
	 * 列表转成树
	 * @param rows
	 * @param fieldsName
	 * @param root
	 * @param rowsIndex
	 * @param fieldindex
	 * @param fieldSplSyx
	 * @return
	 */
	private static int list2Tree(List<Map<String, Object>> rows, String[] fieldsName, TreeNode root, Set<Integer> rowsIndex, int fieldindex, String fieldSplSyx) {
		// 末端
		if (fieldindex == fieldsName.length) {
			root.setBranchCount(1);
			return 1;
		}
		// key: contents, value: rowsIndexRange
		Map<List<Object>, Set<Integer>> contentMap = new HashMap<List<Object>, Set<Integer>>();
		String fName = fieldsName[fieldindex];
		String[] fNames;
		if (fieldSplSyx == null || "".equals(fieldSplSyx))
			fNames = new String[]{fName};
		else
			fNames = fName.split(fieldSplSyx);
		for (Integer index : rowsIndex) {
			Map<String, Object> row = rows.get(index);
			List<Object> contents = new ArrayList<>();
			for (int j = 0; j < fNames.length; j++)
				contents.add(row.get(fNames[j]));
			Set<Integer> indexs;
			if (contentMap.containsKey(contents))
				indexs = contentMap.get(contents);
			else
				indexs = new HashSet<Integer>(index);
			indexs.add(index);
			contentMap.put(contents, indexs);
		}
		for (List<Object> contents : contentMap.keySet()) {
			Map<String, Object> d = new HashMap<String, Object>();
			for (int i = 0; i < fNames.length; i++)
				d.put( fNames[i], contents.get(i) );
			TreeNode child = new TreeNode((String) contents.get(0), contents.get(0), fieldindex, fNames, d);
			Set<Integer> rowsIndexRange = contentMap.get(contents);
			int endCount = list2Tree(rows, fieldsName, child, rowsIndexRange, fieldindex+1, fieldSplSyx);
			root.setBranchCount(root.getBranchCount() + endCount);
			root.addChildren(child);
		}
		// 倒数第二层，向根回溯
		return root.getBranchCount();
	}
	
	@Test
	public void test() {
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(new HashMap<String, Object>(){{put("year", "2018"); put("campus", "主校区"); put("college", "文学院"); put("major_code", "1001"); put("major_name", "汉语言"); put("name", "坂田银时");}});
		list.add(new HashMap<String, Object>(){{put("year", "2018"); put("campus", "主校区"); put("college", "文学院"); put("major_code", "1001"); put("major_name", "汉语言"); put("name", "志村新八");}});
		list.add(new HashMap<String, Object>(){{put("year", "2018"); put("campus", "主校区"); put("college", "理工院"); put("major_code", "1011"); put("major_name", "概率论"); put("name", "神乐");}});
		list.add(new HashMap<String, Object>(){{put("year", "2018"); put("campus", "主校区"); put("college", "理工院"); put("major_code", "1011"); put("major_name", "概率论"); put("name", "志村妙");}});
		list.add(new HashMap<String, Object>(){{put("year", "2018"); put("campus", "分校区"); put("college", "理工院"); put("major_code", "1011"); put("major_name", "概率论"); put("name", "凯瑟琳");}});
		TreeNode lt = list2Tree(list, new String[]{"year","campus","college","major_code,major_name","name"}, ",");
		lt.print(6);
		// use gson
		Gson gson = new Gson();
		String json = gson.toJson(lt);
		try (FileWriter writer = new FileWriter("E:\\test\\testGson.json")) {
		     writer.write(json);
		} catch (IOException e) {
			System.err.println("IO异常");
		}
	}
	
}
