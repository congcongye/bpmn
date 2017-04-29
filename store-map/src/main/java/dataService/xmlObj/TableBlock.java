package dataService.xmlObj;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TableBlock implements Block{
	Integer l;
	Integer t;
	Integer r;
	Integer b;
	List<Cell> cells;
	List<Boolean> rowEmpty = new ArrayList<Boolean>();
	
	public TableBlock(Element block) {
		NodeList rowList = block.getElementsByTagName("row");
		l = Integer.valueOf(block.getAttribute("l"));
		t = Integer.valueOf(block.getAttribute("t"));
		r = Integer.valueOf(block.getAttribute("r"));
		b = Integer.valueOf(block.getAttribute("b"));
		cells = new ArrayList<Cell>();
		for (int i = 0; i < rowList.getLength(); i++) {
			Element erow = (Element) rowList.item(i);
			NodeList cellList = erow.getElementsByTagName("cell");
			Boolean empty = true;
			for (int j = 0; j < cellList.getLength(); j++) {
				Element ecell = (Element) cellList.item(j);
				Cell cell = new Cell(ecell, i, j);
				cells.add(cell);
				if (!cell.getValue().isEmpty()) {
					empty = false;
				}
			}
			rowEmpty.add(empty);
		}
	}

	public Integer getL() {
		return l;
	}

	public void setL(Integer l) {
		this.l = l;
	}

	public Integer getT() {
		return t;
	}

	public void setT(Integer t) {
		this.t = t;
	}

	public Integer getR() {
		return r;
	}

	public void setR(Integer r) {
		this.r = r;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

	public List<Boolean> getRowEmpty() {
		return rowEmpty;
	}

	public void setRowEmpty(List<Boolean> rowEmpty) {
		this.rowEmpty = rowEmpty;
	}
}
