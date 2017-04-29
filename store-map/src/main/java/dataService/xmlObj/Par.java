package dataService.xmlObj;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Par {
	Integer lineSpace;
	Integer lineNumber;
	List<Line> lines;
	
	public Par(Element epar) {
		NodeList lineList = epar.getElementsByTagName("line");
		lineNumber = lineList.getLength();
		lineSpace = Integer.valueOf(epar.getAttribute("lineSpacing"));
		lines = new ArrayList<Line>();
		for (int i = 0; i < lineList.getLength(); i++) {
			Element eline = (Element) lineList.item(i);
			Line line = new Line(eline);
			lines.add(line);
		}
	}

	public Integer getLineSpace() {
		return lineSpace;
	}

	public void setLineSpace(Integer lineSpace) {
		this.lineSpace = lineSpace;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public List<Line> getLines() {
		return lines;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}
}
