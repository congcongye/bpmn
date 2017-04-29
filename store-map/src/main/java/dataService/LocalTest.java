package dataService;


import dataService.picReader.XmlReader;
import dataService.tableAnalyzer.AdvancedTableAnalyzer;
import dataService.tableAnalyzer.DatabaseGenerator2;

import java.util.ArrayList;
import java.util.List;

public class LocalTest {
	public static void main(String[] args) {
		String[] no = {"1", "11", "12"};
		List<AdvancedTableAnalyzer> anas = new ArrayList<AdvancedTableAnalyzer>();
		for (String num : no) {
		String path = "/Users/ycc/Desktop/sample/sample" + num + ".jpg"; //1.4.5.8.9.10
		String type = "xml";
		String chs = "English";
//		TestApp.main(path, type, chs);
		XmlReader reader = new XmlReader(path.substring(0, path.lastIndexOf(".")).concat(".xml"));
		String outputPath = path.substring(0, path.lastIndexOf(".")).concat(".output");
		AdvancedTableAnalyzer ana = new AdvancedTableAnalyzer();
		ana.analyze(reader, outputPath);
		anas.add(ana);
		}
		DatabaseGenerator2 dbg = new DatabaseGenerator2(anas, "purchase");
	}
}
