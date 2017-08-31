package sonly.lines.statistics;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class StatNGram {
	public static void main(String[] args) throws IOException {
		String files = "E:\\Project\\Eclipse\\Ngram\\src";
		Statistics statistics = new Statistics();
		List<String> fileTypes = new LinkedList<>();
		fileTypes.add(".java");
		List<String> fileList = statistics.traverseFolder(files, fileTypes);
		for(String file : fileList)
			statistics.statCodeLineNumber(file);
		
		statistics.show(statistics.getTotal_lines());
	}

}
