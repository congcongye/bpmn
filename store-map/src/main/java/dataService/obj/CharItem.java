package dataService.obj;

import java.io.UnsupportedEncodingException;

public class CharItem {
	
	/**
	 * Note that page is not used now.
	 * @param value
	 * @param xfrom
	 * @param yfrom
	 * @param xto
	 * @param yto
	 * @param page
	 */
	public CharItem(String value, int xfrom, int yfrom, int xto, int yto, int page) {
		this.value = value;
		this.xfrom = xfrom;
		this.xto = xto;
		this.yfrom = yfrom;
		this.yto = yto;
	}
	
	/**
	 * NOTE: !aboveOf is not equal to bottomOf
	 * Used only for line.aboveOf(character)
	 * @param charB
	 * @return true if this charItem is above of charB
	 */
	public boolean aboveOf(CharItem charB) {
		if (this.getYfrom() >= charB.getYto()
				&& this.getXfrom() <= charB.getXfrom() && this.getXto() >= charB.getXto())
			return true;
		return false;
	}
	
	/**
	 * NOTE: !belowOf is not equal to bottomOf
	 * Used only for line. belowOf(character)
	 * @param charB
	 * @return true if this charItem is above of charB
	 */
	public boolean belowOf(CharItem charB) {
		if (this.getYto() <= charB.getYfrom()
				&& this.getXfrom() <= charB.getXfrom() && this.getXto() >= charB.getXto())
			return true;
		return false;
	}
	
	/**
	 * NOTE: !leftOf is not equal to rightOf
	 * Used only as line.leftOf(character)
	 * @param charB
	 * @return true if this charItem is in the left of charB
	 */
	public boolean leftOf(CharItem charB) {
		if (this.getXto() <= charB.getXfrom()
				&& this.getYfrom() <= charB.getYfrom() && this.getYto() >= charB.getYto())
			return true;
		return false;
	}
	
	/**
	 * NOTE: !rightOf is not equal to rightOf
	 * Used only as line.rightOf(character)
	 * @param charB
	 * @return true if this charItem is in the left of charB
	 */
	public boolean rightOf(CharItem charB) {
		if (this.getXfrom() >= charB.getXto()
				&& this.getYfrom() <= charB.getYfrom() && this.getYto() >= charB.getYto())
			return true;
		return false;
	}
		
	public int getXfrom() {
		return xfrom;
	}
	
	public void setXfrom(int xfrom) {
		this.xfrom = xfrom;
	}
	
	public int getXto() {
		return xto;
	}
	
	public void setXto(int xto) {
		this.xto = xto;
	}
	
	public int getYfrom() {
		return yfrom;
	}
	
	public void setYfrom(int yfrom) {
		this.yfrom = yfrom;
	}
	
	public int getYto() {
		return yto;
	}
	
	public void setYtom(int yto) {
		this.yto = yto;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getWidth() {
		return xto - xfrom;
	}
	
	public int getHeight() {
		return yto - yfrom;
	}
	
	public String getValue() {
		try {
			String temp = new String(value.getBytes("Unicode"), "Unicode");
			return temp;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return String.format("%s %d %d %d %d %d", value, xfrom, yfrom, xto, yto, page);
	}
	
	private int xfrom;
	private int yfrom;
	private int xto;
	private int yto;
	private int page;
	private String value;
}
