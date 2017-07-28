package sonly.lines.statistics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *<ul>
 *<li>Description: 单元测试 
 *<li>Company: HUST
 *<li>@author Sonly
 *<li>Date: 2017年7月28日
 *</ul>
 */
public class Client {
	Statistics statistics;
	String folder;			//文件夹路径
	String file;			//文件路径
	List<String> fileTypes;	//文件类型（后缀）
	
	@Before
	public void setup() {
		statistics = new Statistics();
		folder = "files";
		file = "files\\test1.java";
		fileTypes = new ArrayList<>();
	}
	
	@Test
	public void test() throws IOException {
		fileTypes.add(".java");
		
		List<String> list = statistics.traverseFolder(folder, fileTypes);
		for(String str : list) {
			statistics.statCodeLineNumber(str);
		}
		
		statistics.show(statistics.getTotal_lines());
	}
	
	/**
	 * 测试int型数组求和
	 */
	@Test
	public void testGetTotal() {
		int[] temp = new int[]{1,2,3,4};
		Assert.assertEquals(10, statistics.getTotal(temp));
	}
	
	/**
	 * 测试给定文件夹和文件类型，求所有文件的绝对路径
	 */
	@Test
	public void testTraverseFolder() {
		fileTypes.add(".java");
		fileTypes.add(".txt");
		String string = "files\\folder1";
		List<String> list = new ArrayList<>();
		list.add("E:\\Project\\Eclipse\\StatisticsCodeLines\\files\\folder1\\demo.txt");
		list.add("E:\\Project\\Eclipse\\StatisticsCodeLines\\files\\folder1\\test2.java");
		list.add("E:\\Project\\Eclipse\\StatisticsCodeLines\\files\\folder1\\test3.java");
		
		Assert.assertEquals(list, statistics.traverseFolder(string, fileTypes));
	}
	
	/**
	 * 测试给定文件的路径求其空行数、注释行数、代码行数  
	 * @throws IOException
	 */
	@Test
	public void testStatCodeLineNumber() throws IOException {
		int[] lines = new int[]{1,6,6};
		int[] get = statistics.statCodeLineNumber(file);
		Assert.assertArrayEquals(lines, get);
	}
}
