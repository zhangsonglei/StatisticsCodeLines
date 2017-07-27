package sonly.lines.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Statistics {
	static List<String> list_files = new LinkedList<>();

	private static List<String> traverseFolder(String path) {
		File file = new File(path);
	   
		if (file.exists()) {
	        File[] files = file.listFiles();
	        if (files.length != 0) 
	            for (File file2 : files) 
	                if (file2.isDirectory()) 
	                    traverseFolder(file2.getAbsolutePath());
	                else 
	                    list_files.add(file2.getAbsolutePath());
	    }
		
		return list_files;
	}
	
	public static int statCodeLineNumber(File javaFile) throws IOException {  
		int result = 0;       
	          
		FileReader fileR = new FileReader(javaFile);  
		BufferedReader br = new BufferedReader(fileR);  
	              
		String str;  
		Stack<String> codeStack = new Stack<String>();  
	          
		while((str = br.readLine())!=null) {       
			if (!str.trim().startsWith("//")) {  
				if (!str.trim().startsWith("/*")) {  
					if (str.trim().length()!=0)
						result++;  
					else  
						System.out.println("one empty line appeared...");    
	                }  
				else {  
					codeStack.push(str);  
					System.out.println("after/*   "+str);  
					while(!codeStack.isEmpty()) {     
						while(((str=br.readLine())!=null)&& (!(str.trim()).endsWith("*/"))) {                             
							codeStack.push(str);  
							System.out.println("after/*   "+str);                             
						}  
						String topStringOfStack;  
						while((!codeStack.isEmpty())&& (!(topStringOfStack=codeStack.pop()).startsWith("/*"))) {  
	                                
						}  
					}                     
				}  
	                  
			}  
			else {  
				System.out.println("after//     "+str);  
			}  
		}  
		return result;  
	}  
	
	public static void main(String[] args) throws IOException {
		int lines = 0;
		String path = "E:\\Project\\Eclipse\\Ngram\\src";
		List<String> list = traverseFolder(path);
		for(String file : list) 
			lines += statCodeLineNumber(new File(file));
		
		System.out.println(list.size()+" 个文件，净行数："+lines);
			
	}
}
