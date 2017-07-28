package sonly.lines.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *<ul>
 *<li>Description: 统计代码中的行数：<br>
 *空行<br>
 *“//和“/*”注释行<br>
 *代码行 <br>
 *<li>Company: HUST
 *<li>@author Sonly
 *<li>Date: 2017年7月28日
 *</ul>
 */
public class Statistics {
	
	/**
	 * 文件编号
	 */
	static int line_no = 1;
	/**
	 * 存放文件绝对路径的链表
	 */
	static List<String> list_files = new LinkedList<>();
	
	/**
	 * 存放所有文件总行数的数组
	 * 0-空行总行数
	 * 1-注释总行数
	 * 2-代码总行数
	 */
	static int[] total_lines = new int[3];
	
	/**
	 * 返回int型数组的和
	 * @param temp待求和的数组
	 * @return int型数组的和
	 */
	private static int getTotal(int[] temp){
		int result = 0;
		for(int num : temp)
			result += num;
		
		return result;
	}
	
	/**
	 * 搜索给定文件夹下所有的文件 ,并筛选所需文件
	 * @param path 文件所在文件夹
	 * @param regExp 正则表达式，过滤文件
	 * @return 所有文件夹中的文件
	 */
	private static List<String> traverseFolder(String path, List<String> fileTypes) {
		File file = new File(path);

		if (file.exists()) {
	        File[] files = file.listFiles();
	        if (files.length != 0)
	            for (File file2 : files) {
	                if (file2.isDirectory()){//文件夹
	                    traverseFolder(file2.getAbsolutePath(), fileTypes);
	                }else {//文件
	                	String absolutePath = file2.getAbsolutePath();
	                	String fileType = absolutePath.substring(absolutePath.indexOf("."), absolutePath.length());
	                	if(fileTypes.contains(fileType)){//所需文件类型的文件
	                		list_files.add(file2.getAbsolutePath());
	                	}
	                }//end if-else
	            }//end for
		}//end out if
		return list_files;
	}
	
	/**
	 * 统计文件中的行数，并返回代码净行数
	 * @param javaFile 待统计的文件
	 * @return 文件中的净行数
	 * @throws IOException
	 */
	private static void statCodeLineNumber(String filePath) throws IOException {  	       
		FileReader fileR = new FileReader(new File(filePath));  
		BufferedReader br = new BufferedReader(fileR);  
	    int[] lines = new int[3];
		String str;  
		Stack<String> codeStack = new Stack<String>();  
	    
		while((str = br.readLine())!=null) {
			if (!str.trim().startsWith("//")) {//非“//”注释行  
				if (!str.trim().startsWith("/*")) {//非“/*”注释行
					if (str.trim().length()!=0){//非空行
						lines[2]++;
					}else//空行				
						lines[0]++;
				}else {//“/*”注释行
					codeStack.push(str);
					lines[1]++;
					while(!codeStack.isEmpty()) {
						while(((str=br.readLine())!=null)&& (!(str.trim()).endsWith("*/"))) {                             
							codeStack.push(str);
							lines[1]++;
						}
						lines[1]++;
						@SuppressWarnings("unused")
						String topStringOfStack;  
						while((!codeStack.isEmpty())&& (!(topStringOfStack=codeStack.pop()).startsWith("/*"))) {  
							
						}  
					}                     
				}        
			}else {//"//"注释行
				lines[1]++;
			}  
		}//读取结束
		br.close();
		
		System.out.println(line_no+++":\t"+filePath+":"+getTotal(lines));
		
		for(int index = 0; index < 3; index++) {
			total_lines[index] += lines[index];
		}
	}  
	
	private static void show() {
		System.out.println(list_files.size()+"个文件:\n\t总行数："+getTotal(total_lines)+
						   "\n\t空行数："+total_lines[0] +
						   "\n\t注释行数："+total_lines[1]+
						   "\n\t代码行数："+total_lines[2]);
	}
	
	public static void main(String[] args) throws IOException {
		String path = "E:\\Project\\Eclipse\\Ngram\\src";
		List<String> fileTypes = new ArrayList<>();
		fileTypes.add(".java");
		
		List<String> list = traverseFolder(path, fileTypes);
		for(String file : list) 
			statCodeLineNumber(file);
		
		show();
	}
}
