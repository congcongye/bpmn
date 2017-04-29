package dataService.xmlObj;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TextBlock implements Block{
	List<Par> pars;
	Integer l;
	Integer t;
	Integer r;
	Integer b;
	
	public TextBlock(Element block) {
		NodeList parList = block.getElementsByTagName("par");
		l = Integer.valueOf(block.getAttribute("l"));
		t = Integer.valueOf(block.getAttribute("t"));
		r = Integer.valueOf(block.getAttribute("r"));
		b = Integer.valueOf(block.getAttribute("b"));
		pars = new ArrayList<Par>();
		for (int i = 0; i < parList.getLength(); i++) {
			Element epar = (Element) parList.item(i);
			Par par = new Par(epar);
			pars.add(par);
		}
	}

	public List<Par> getPars() {
		return pars;
	}

	public void setPars(List<Par> pars) {
		this.pars = pars;
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
}
