package dataService.xmlObj;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Cell {
	private String value;
	private int row;
	private int column;
	private int width;
	private int height;
	private int l;
	private int r;
	private int b;
	private int t;
	private int lineSpace;
	
	public Cell(Element ecell, int row, int column) {
		l = Integer.MAX_VALUE;
		r = 0;
		b = 0;
		t = Integer.MAX_VALUE;
		NodeList lineList = ecell.getElementsByTagName("line");
		lineSpace = Integer.valueOf(((Element)ecell.getElementsByTagName("par").item(0)).getAttribute("lineSpacing"));
		String content = "";
		for (int k = 0; k < lineList.getLength(); k++) {
			Element eline = (Element) lineList.item(k);
			Line line = new Line(eline);
			content = content.concat("\n").concat(line.getContent());
			l = Math.min(l, line.getL());
			r = Math.max(r, line.getR());
			t = Math.min(t, line.getT());
			b = Math.max(b, line.getB());
		}
		width = Integer.valueOf(ecell.getAttribute("width"));
		height = Integer.valueOf(ecell.getAttribute("height"));
		value = content;
		this.row = row;
		this.column = column;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public int getLineSpace() {
		return lineSpace;
	}

	public void setLineSpace(int lineSpace) {
		this.lineSpace = lineSpace;
	}
}
