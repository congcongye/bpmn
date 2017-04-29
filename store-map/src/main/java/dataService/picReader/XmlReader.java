package dataService.picReader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import dataService.xmlObj.Block;
import dataService.xmlObj.TableBlock;
import dataService.xmlObj.TextBlock;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class XmlReader {
	public XmlReader(String path) {
		read(path);
	}
	private void read(String path) {
		try {
			// prepare input file
			File inputFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			Element root = doc.getDocumentElement();
			
			NodeList page = root.getElementsByTagName("page");
			Element p = (Element)page.item(0);
			width = Integer.valueOf(p.getAttribute("width"));
			height = Integer.valueOf(p.getAttribute("height"));
			NodeList blocknodes = root.getElementsByTagName("block");
			for (int b = 0; b < blocknodes.getLength(); b++) {
				Element eblock = (Element) blocknodes.item(b);
				if (eblock.getAttribute("blockType").equals("Text")) {
					blocks.add(new TextBlock(eblock));
				} else if (eblock.getAttribute("blockType").equals("Table")) {
					blocks.add(new TableBlock(eblock));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static String wrapChar(char ch){
		String wraped = new String();
		switch(ch) {
		case '\t': wraped = "9%"; break;
		case ' ': wraped = "20%"; break;
		case '\"': wraped = "22%"; break;
		case '%': wraped = "25%"; break;
		case '&': wraped = "26%"; break;
		case '\n': wraped = "%0D"; break;
		case '\r': wraped = "%0D"; break;
		default: wraped = "" + ch;
		}
		return wraped;
	}
	
	public static String unwrapString(String str) {
		String wraped = new String(str);
		wraped = wraped.replaceAll("9%", "\t");
		wraped = wraped.replaceAll("20%", " ");
		wraped = wraped.replaceAll("22%", "\"");
		wraped = wraped.replaceAll("26%", "&");
		wraped = wraped.replaceAll("%0D", "\n");
		wraped = wraped.replaceAll("25%", "%");
		return wraped;
	}
	
	public static String wrapString(String str) {
		String wraped = new String(str);
		wraped = wraped.replaceAll("%", "25%");
		wraped = wraped.replaceAll("\t", "9%");
		wraped = wraped.replaceAll(" ", "20%");
		wraped = wraped.replaceAll("\"", "22%");
		wraped = wraped.replaceAll("&", "26%");
		wraped = wraped.replaceAll("\n", "%0D");
		return wraped;
	}
	
	public static String unwrapProperty(String str) {
		String wraped = new String(str);
		wraped = wraped.replaceAll("9%", "");
		wraped = wraped.replaceAll("20%", "");
		wraped = wraped.replaceAll("22%", "");
		wraped = wraped.replaceAll("26%", "");
		wraped = wraped.replaceAll("%0D", "");
		wraped = wraped.replaceAll("25%", "");
		wraped = wraped.replaceAll("【", "");
		wraped = wraped.replaceAll("】", "");
		wraped = wraped.replaceAll("•", "");
		wraped = wraped.replaceAll("•", "");
		wraped = wraped.replaceAll("：", "");
		wraped = wraped.replaceAll(":", "");
		return wraped;
	}
	
	private List<Block> blocks = new LinkedList<Block>();
	private Integer width;
	private Integer height;

	public List<Block> getBlocks() {
		return blocks;
	}


	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	} 
}
