package dataService.xmlObj;

import dataService.obj.CharItem;
import org.w3c.dom.NodeList;



import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

public class Line {
	Integer baseline;
	Integer l;
	Integer t;
	Integer r;
	Integer b;
	String content;
	List<CharItem> chars;

	public Line(Element eline) {
		NodeList charParams = eline.getElementsByTagName("charParams");
		baseline = Integer.valueOf(eline.getAttribute("baseline"));
		l = Integer.valueOf(eline.getAttribute("l"));
		r = Integer.valueOf(eline.getAttribute("r"));
		t = Integer.valueOf(eline.getAttribute("t"));
		b = Integer.valueOf(eline.getAttribute("b"));
		content = "";
		chars = new ArrayList<CharItem>();
		for (int i = 0; i < charParams.getLength(); i++) {
			Element chr = (Element) charParams.item(i);
			CharItem item = new CharItem(
					chr.getTextContent(),
					Integer.valueOf(chr.getAttribute("l")),
					Integer.valueOf(chr.getAttribute("b")),
            		Integer.valueOf(chr.getAttribute("r")),
            		Integer.valueOf(chr.getAttribute("t")), 1);
			chars.add(item);
			content = content.concat(chr.getTextContent());
		}
	}

	public Integer getBaseline() {
		return baseline;
	}
	public void setBaseline(Integer baseline) {
		this.baseline = baseline;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public List<CharItem> getChars() {
		return chars;
	}

	public void setChars(List<CharItem> chars) {
		this.chars = chars;
	}
}
