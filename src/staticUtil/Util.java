package staticUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Util {
	/**
	 * 格式化二进制字符串
	 * 如："111111011000000" 格式化成 "第1-6周,第8-9周"
	 * @param binStr 二进制字符串，如："111111011000000"
	 * @param conn	  连接符，如："-"
	 * @param spli	  分隔符，如：","
	 * @param pre	  前缀，如："第"
	 * @param suf	  后缀，如："周"
	 * @return 		"第1-6周,第8-9周"
	 */
	public static String formatBinaryCode(String binStr, String conn, String spli, String pre, String suf) {
		if (binStr == null || "".equals(binStr)) return "";
		String[] binArr = splitToSingleWord(binStr);
		ArrayList<Integer> parseArr = new ArrayList<Integer>();
		for (int i = 0; i < binArr.length; i++) 
			if ("1".equals(binArr[i])) parseArr.add(i+1);
		StringBuffer r = new StringBuffer();
		boolean isSeries = false; // 默认不连
		int len = parseArr.size();
		if (len == 0) return "";
		if (len == 1) return pre + parseArr.get(0) + suf;
		r.append(pre + parseArr.get(0));
		for (int j = 1, l = len-1; j < l; j++) {
			if (parseArr.get(j) - parseArr.get(j-1) == 1) { // 连
				if (!isSeries) r.append(conn);
				isSeries = true;
			} else { // 不连
				if (isSeries) 
					r.append(parseArr.get(j-1) + suf + spli + pre + parseArr.get(j));
				else
					r.append(suf + spli + pre + parseArr.get(j));
				isSeries = false;
			}
		}
		if (isSeries) 
			if (parseArr.get(len-1) - parseArr.get(len-2) == 1) 
				r.append(parseArr.get(len-1)+"");
			else 
				r.append(parseArr.get(len-2) + suf + spli + pre + parseArr.get(len-1));
		else 
			if (parseArr.get(len-1) - parseArr.get(len-2) == 1) 
				r.append(conn + parseArr.get(len-1));
			else 
				r.append(suf + spli + pre + parseArr.get(len-1));
		r.append(suf);
		return r.toString();
	}
	
	/**
	 * 分割字符串成数组，每个字符为一个元素
	 * 为了解决JDK1.7-的 String.split("") bug
	 * 用String.charArray()可代替该方法!!!
	 * @param str
	 * @return
	 */
	public static String[] splitToSingleWord(String str) {
		String[] split = str.split("");
		int oLength = split.length;
		if (oLength > 1 && "".equals(split[0])) {
			System.arraycopy(split, 1, split, 0, oLength-1);
			split = Arrays.copyOf(split, oLength-1);
		}
		return split;
	}
	
	/**
	 * 根据列表中元素指定属性值 排序
	 * @param list 待排序列表
	 * @param prop 列表中元素的属性名
	 */
	public static void sortListByProperty(List<? extends Object> list, String prop) {
		if (list == null || list.size() == 0) return;
		try {
			Class<? extends Object> clazz = list.get(0).getClass();
			Field field = clazz.getDeclaredField(prop);
			field.setAccessible(true);
			Collections.sort(list, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					int res = 0;
					try {
						res = (Integer)field.get(o2) - (Integer)field.get(o1);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						System.err.println(clazz.getSimpleName() + "类中" + field.getName() + "属性值不为数值型，无法比较");
						e.printStackTrace();
					}
					return res;
				}
			});
		} catch (NoSuchFieldException e) {
			System.err.println("未在列表中找到包含" + prop + "属性的对象");
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据两个二进制字符串，判断二者是否有同位数都为1的情况
	 * @param binstr1
	 * @param binstr2
	 * @return
	 */
	public static boolean isClashByBinaryStr(String binstr1, String binstr2) {
		int bs1 = Integer.parseInt(binstr1, 2);
		int bs2 = Integer.parseInt(binstr2, 2);
		int res = bs1&bs2;
		if (res> 0)	return true;
		return false;
	}
	
	/**
	 * 将字符串中的转义字符转换成java正则表达式字符串
	 * "{{}}" => "\\{\\{\\}\\}"
	 * @param keyword
	 * @return
	 */
	public static String ESC2Regex(String keyword) {
		String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };  
        for (String key : fbsArr)
            if (keyword.contains(key))
                keyword = keyword.replace(key, "\\" + key);
        return keyword;
	}
	
	/**
	 * 将数组按指定字符串 拼接成字符串
	 * 类似JavaScript中ArrayObject.join(keyStr)
	 * @param keyStr
	 * @param strAry
	 * @return
	 */
	public static String join(String keyStr, String[] strAry){
        StringBuffer sb = new StringBuffer();
        for(int i=0, len =strAry.length; i<len; i++){
            if(i == (len-1)){
                sb.append(strAry[i]);
            } else {
                sb.append(strAry[i]).append(keyStr);
            }
        }
        return sb.toString();
    }
	
	/**
	 * 深拷贝
	 * https://blog.csdn.net/lpayit/article/details/46408929
	 * @param src
	 * @return
	 * @throws RuntimeException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cloneTo(T src) throws RuntimeException {
		ByteArrayOutputStream memoryBuffer = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		T dist = null;
		try {
			out = new ObjectOutputStream(memoryBuffer);
			out.writeObject(src);
			out.flush();
			in = new ObjectInputStream(new ByteArrayInputStream(memoryBuffer.toByteArray()));
			dist = (T) in.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (out != null)
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			if (in != null)
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
		return dist;
	}

	/**
	 * 将map按指定分隔符 拼接成字符串
	 * @param map	如{aa: 11, bb: 22, cc: 33}
	 * @param entrySplit 每个键值对之间的分隔符，如：" & "
	 * @param kvSplit 键值之间的分隔符，如：" = "
	 * @return 字符串，如："aa = 11 & bb = 22 & cc = 33"
	 */
	public static String mapToString(Map<String, Object> map, String entrySplit, String kvSplit) {
		if (map == null || map.size() == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		for (String key : map.keySet())
			sb.append(key).append(kvSplit).append((String) map.get(key)).append(entrySplit);
		sb.delete(sb.length() - entrySplit.length(), sb.length());
		return sb.toString();
	}
	
	/**
	 * 数组中插入元素
	 * @param original
	 * @param element
	 * @param index
	 * @return
	 */
	public static Object[] insertElement(Object original[], Object element, int index) {
		int length = original.length;
		Object destination[] = new Object[length + 1];
		System.arraycopy(original, 0, destination, 0, index);
		destination[index] = element;
		System.arraycopy(original, index, destination, index + 1, length - index);
		return destination;
	}
	
	/**
	 * 数组后追加元素
	 * @param original
	 * @param element
	 * @return
	 */
	public static Object[] appendElement(Object original[], Object element) {
		return insertElement(original, element, original.length);
	}
	
	/**
	 * 获取数组中元素的索引
	 * @param arr
	 * @param key
	 * @return
	 */
	public int getArrayIndex(Object[] arr, Object key) {
		if (key instanceof Integer) 
			return Arrays.binarySearch(arr, key);
		for (int i = 0; i < arr.length; i++) 
			if (arr[i].equals(key)) 
				return i;
		return -1;
	}
	
	/**
	 * 根据文件路径，获取文件名
	 * @param path
	 * @param removeExtName 移除扩展名吗？
	 * @return
	 */
	public static String getFileNameByPath(String path, boolean removeExtName) {
		path = path.trim();
		String fileName = path.substring(path.lastIndexOf(File.separator)+1);
		if (removeExtName) {
			int dot = fileName.lastIndexOf('.'); 
			if ((dot >-1) && (dot < (fileName.length())))
				fileName = fileName.substring(0, dot);
		}
		return fileName;
	}
	
	public static void main(String[] args) {
//		String str = formatBinaryCode("11111101100000000000000000", "-", ",", "第", "周");
//		String str2 = formatBinaryCode("0000000000000000", "-", ",", "第", "周");
//		String str3 = formatBinaryCode("11111101100011100000000000", "-", ",", "第", "周");
//		System.out.println(str);
//		System.out.println(str2);
//		System.out.println(str3);
		
//		String string = "促内需哦，我一把我删";
//		char[] charArray = string.toCharArray();
//		System.out.println(Arrays.toString(charArray));
		
		Object[] arr = appendElement(new String[]{"A"}, "a");
		System.out.println(Arrays.toString(arr));
		
		System.out.println(System.getProperty("java.version"));
		
	}
}
