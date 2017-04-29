package dataService.obj;

public class Cell {
	public Cell() {}
	
	public Cell(int id, int row, int column, int height, int width, String value) {
		this.row = row;
		this.column = column;
		this.width = width;
		this.height = height;
		this.value = value;
		this.id = id;
		this.isProperty = false;
	}
	
	public Cell(int id, int row, int column, int height,
			int width, String value, boolean isProperty) {
		this.row = row;
		this.column = column;
		this.width = width;
		this.height = height;
		this.value = value;
		this.id = id;
		this.isProperty = isProperty;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void appendValue(String ch) {
		value = value.concat(ch);
	}
	
	public int getId() {
		return id;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public String getValue() {
		return value;
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
	
	public boolean samePosition(Cell cellB) {
		if (this.row == cellB.row && this.column == cellB.column)
			return true;
		return false;
	}
	
	public void setIsProperty(boolean isProperty) {
		this.isProperty = isProperty;
	}
	
	public boolean isProperty() {
		return isProperty;
	}
	
	
	private String value;
	private int row;
	private int column;
	private int width;
	private int height;
	private int id;
	private boolean isProperty;
}
