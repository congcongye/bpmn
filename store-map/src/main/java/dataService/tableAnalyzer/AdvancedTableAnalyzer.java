package dataService.tableAnalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dataService.picReader.XmlReader;
import dataService.xmlObj.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class AdvancedTableAnalyzer {
	private Document output = DocumentHelper.createDocument();
	private Element globalRoot = output.addElement("rdf:RDF");
	private String title = "";
	private Map<String, List<String>> relateds = new HashMap<String, List<String>>();
	private List<String> detailed = new ArrayList<String>();
	private List<String> plus = new ArrayList<String>();
	private XmlReader reader;
	
	public void analyze(XmlReader reader, String outputPath) {
		this.reader = reader;
		relateds.put("Root", new ArrayList<String>());
		for (int i = 0; i < reader.getBlocks().size(); i++) {
			if (reader.getBlocks().get(i).getClass().equals(TextBlock.class)) {
				if (i == 0 && ((TextBlock)reader.getBlocks().get(0)).getPars().get(0).getLineNumber() == 1) {
					title = ((TextBlock)reader.getBlocks().get(0)).getPars().get(0).getLines().get(0).getContent();
				} else {
					copeWithTextBlock((TextBlock)reader.getBlocks().get(i), i);
				}
			} else {
				copeWithTableBlock((TableBlock)reader.getBlocks().get(i), i);
			}
		}
		if (!relateds.get("Root").isEmpty()) {
			plus.addAll(relateds.get("Root"));
			relateds.remove("Root");
		}
		output(outputPath);
	}
	
	public String output(String outputPath) {
		String rdf = "";
		if (title.isEmpty()) {
			title = "Root";
		}
		rdf = rdf.concat(title).concat("\n");
		for(Entry<String,List<String>> related : relateds.entrySet()) {
			String key = related.getKey().trim().replace(":", "");
			rdf = rdf+"--"+key+"\n";
			for (String l : related.getValue()) {
				String trim = l.trim().replace(":", "");
				rdf = rdf.concat("----").concat(trim).concat("\n");
			}
		}
		rdf = rdf.concat("--").concat("detailed").concat("\n");
		for (String d : detailed) {
			String trim = d.trim().replace(":", "");
			rdf = rdf.concat("----").concat(trim).concat("\n");
		}
		for (String p : plus) {
			String trim = p.trim().replace(":", "");
			rdf = rdf.concat("--").concat(trim).concat("\n");
		}
		System.out.print(rdf);
		return rdf;
	}
	
	// Root为key的是plus，否则是related
	public void copeWithTextBlock(TextBlock block, int bi) {
		Boolean byCol = false;
		Boolean byRow = false;
		int breakcol = Integer.MAX_VALUE;
		int breakrow = Integer.MAX_VALUE;
		List<String> related1 = new ArrayList<String>();
		List<String> related2 = new ArrayList<String>();
		Integer firstspace1 = 0;
		Integer firstspace2 = 0;
		Boolean firstIsTitle1 = false;
		Boolean firstIsTitle2 = false;
		List<Line> lines = new ArrayList<Line>();
		//如果相同名字发现，则两个实体，否则一个
		for (Par par : block.getPars()) {
			lines.addAll(par.getLines());
		}
		for (int i = 0; i < lines.size() - 1; i++) {
			Line li = lines.get(i);
			for(int j = i + 1; j < lines.size(); j++) {
				Line lj = lines.get(j);
				if (li.getContent().equals(lj.getContent())) {
					if (Math.abs(li.getBaseline() - lj.getBaseline()) < 10) {
						byCol = true;
						breakcol = Math.min(breakcol, Math.max(li.getL(), lj.getL()));
					} else {
						byRow = true;
						breakrow = Math.min(breakrow, Math.max(li.getT(), lj.getT()));
					}
				}
			}
		}
		// do add operation

		if (byCol) {
			//TODO: Title by linespace
			
			for (Line line : lines) {
				if (!line.getContent().isEmpty()) {
					if (line.getR() < breakcol) {
						if (!firstIsTitle1) {
							if (related1.isEmpty()) {
								firstspace1 = line.getB() - line.getT();
							} else {
								if (Math.abs(line.getB() - line.getT() - firstspace1) > 10) {
									firstIsTitle1 = true;
								}
							}
						}
						String content = line.getContent();
						String[] strs = content.split(":");
						for (String sub : strs) {
							if (sub.toLowerCase().equals(sub)) continue;
							related1.add(sub);
						}
					} else {
						if (!firstIsTitle2) {
							if (related2.isEmpty()) {
								firstspace2 = line.getB() - line.getT();
							} else {
								if (Math.abs(line.getB() - line.getT() - firstspace2) > 10) {
									firstIsTitle2 = true;
								}
							}
						}
						String content = line.getContent();
						String[] strs = content.split(":");
						for (String sub : strs) {
							if (sub.toLowerCase().equals(sub)) continue;
							related1.add(sub);
						}
					}
				}
			}
			if (firstIsTitle1) {
				relateds.put(related1.remove(0),related1);
			} else {
				relateds.get("Root").addAll(related1);
			}
			if (firstIsTitle2) {
				relateds.put(related2.remove(0),related2);
			} else {
				relateds.get("Root").addAll(related2);
			}
		} else if (byRow) {
			for (Line line : lines) {
				if (!line.getContent().isEmpty()) {
					if (line.getB() < breakrow) {
						if (!firstIsTitle1) {
							if (related1.isEmpty()) {
								firstspace1 = line.getB() - line.getT();
							} else {
								if (Math.abs(line.getB() - line.getT() - firstspace1) > 10) {
									firstIsTitle1 = true;
								}
							}
						}
						String content = line.getContent();
						String[] strs = content.split(":");
						for (String sub : strs) {
							if (sub.toLowerCase().equals(sub)) continue;
							related1.add(sub);
						}
					} else {
						if (!firstIsTitle2) {
							if (related2.isEmpty()) {
								firstspace2 = line.getB() - line.getT();
							} else {
								if (Math.abs(line.getB() - line.getT() - firstspace2) > 10) {
									firstIsTitle2 = true;
								}
							}
						}
						String content = line.getContent();
						String[] strs = content.split(":");
						for (String sub : strs) {
							if (sub.toLowerCase().equals(sub)) continue;
							related1.add(sub);
						}
					}
				}
			}
			if (firstIsTitle1) {
				relateds.put(related1.remove(0),related1);
			} else {
				relateds.get("Root").addAll(related1);
			}
			if (firstIsTitle2) {
				relateds.put(related2.remove(0),related2);
			} else {
				relateds.get("Root").addAll(related2);
			}
		} else {
			for (Line line : lines) {
				if (!line.getContent().isEmpty()) {
					if (!firstIsTitle1) {
						if (related1.isEmpty()) {
							firstspace1 = line.getB() - line.getT();
						} else {
							if (Math.abs(line.getB() - line.getT()- firstspace1) > 10) {
								firstIsTitle1 = true;
							}
						}
					}
					String content = line.getContent();
					String[] strs = content.split(":");
					for (String sub : strs) {
						if (sub.toLowerCase().equals(sub)) continue;
						related1.add(sub);
					}
				}
			}
			if (firstIsTitle1) {
				relateds.put(related1.remove(0),related1);
			} else {
				relateds.get("Root").addAll(related1);
			}
		}
	}
	public Map<String, List<String>> copeWithTextBlock1(TextBlock block, int bi) {
		Map<String, List<String>> titlePar = new HashMap<String, List<String>>();
		String tmpTitle = "";
		for (Par par : block.getPars()) {
			// par一行则为title
			if (par.getLineNumber() == 1) {
				String content = par.getLines().get(0).getContent();
				String[] strs = content.split(" ");
				if (strs.length < 5) {
					relateds.put(content, new ArrayList<String>());
					tmpTitle = content;
				}
			} else {
				//第一个就是多行，则直接加进去Root
				/*if (block.getPars().indexOf(par) == 0) {
					// add directly
					List<String> property = new ArrayList<String>();
					for (Line line : par.getLines()) {
						String content = line.getContent();
						String[] strs = content.split(":");
						for (String sub : strs) {
							if (sub.contains("-")) continue;
							property.add(sub);
						}
					}
					relateds.get("Root").addAll(property);
				}
				else*/ if (!tmpTitle.isEmpty()) {
					// 前一个是标题 则add to the key
					List<String> property = new ArrayList<String>();
					for (Line line : par.getLines()) {
						String content = line.getContent();
						String[] strs = content.split(":");
						for (String sub : strs) {
							if (sub.contains("-")) continue;
							property.add(sub);
						}
					}
					relateds.get(tmpTitle).addAll(property);
					tmpTitle = ""; //tmpTitle用过，置回空
				} else {
					// 没有tmpTitle
					List<String> property = new ArrayList<String>();
					for (Line line : par.getLines()) {
						String content = line.getContent();
						String[] strs = content.split(":");
						for (String sub : strs) {
							if (sub.contains("-")) continue;
							property.add(sub);
						}
					}
					plus.addAll(property);
				}
			}
		}
		//relateds.putAll(titlePar);
		return titlePar;
	}
	
	public void copeWithTextBlock2(TextBlock block, int bi) {
		Map<Integer, List<Par>> groupPar = new HashMap<Integer, List<Par>>();
		Map<Integer, List<Integer>> spaceGroup = new HashMap<Integer, List<Integer>>();
		//根据space和块建立map找出space相同的作为一个整体
		for(Par par : block.getPars()) {
			Integer index = block.getPars().indexOf(par);
			if (spaceGroup.containsKey(par.getLineSpace())) {
				Boolean found = false;
				for(Integer group : spaceGroup.get(par.getLineSpace())) {
					if (groupPar.get(group).size() + group == index) {
						groupPar.get(group).add(par);
						found = true;
						break;
					}
				}
				if (!found) {
					List<Par> parList = new ArrayList<Par>();
					parList.add(par);
					groupPar.put(index, parList);
					spaceGroup.get(par.getLineSpace()).add(index);
				}
			} else {
				spaceGroup.put(par.getLineSpace(), new ArrayList<Integer>());
				spaceGroup.get(par.getLineSpace()).add(index);
				groupPar.put(index, new ArrayList<Par>());
				groupPar.get(index).add(par);
			}
		}
		for (List<Par> pars : groupPar.values()) {
			
		}
		// find title title在最前
		/*Integer maxSpace = 0;
		for (Entry<Integer, List<Par>> sp: space.entrySet()) {
			if (sp.getValue().size() == 1 && sp.getKey() > maxSpace) {
				maxSpace = sp.getKey();
			}
		}
		List<Par> copy = new ArrayList<Par>(block.getPars());
		if (maxSpace > 0) {
			title = space.get(maxSpace).get(0).getLines().get(0).getContent();
			copy.remove(space.get(maxSpace));
		}*/
		// find subtitle
		/*Map<Integer, List<Par>> parBlocks = new HashMap<Integer, List<Par>>();
		for (int i = 0; i < copy.size(); i++) {
			
		}*/
	}
	
	// detail和related处理
	public void copeWithTableBlock(TableBlock block, int bi) {
		// 大于一半宽度可能为detail
		List<String> property = new ArrayList<String>();
		if (block.getR() - block.getL() > reader.getWidth() * 0.5) {
			int cnt = 0;
			for (Boolean b : block.getRowEmpty()) {
				if (b) {
					cnt++;
				}
			}
			// 是detail
			if (cnt >= 3) {
				for (Cell cell: block.getCells()) {
					if (!cell.getValue().isEmpty()) {
						if (cell.getRow() == 0) {
							detailed.add(cell.getValue());
						} else {
							plus.add(cell.getValue());
						}
					}
				}
				return;
			}
		}
		// 是其他的
			Boolean byCol = false;
			Boolean byRow = false;
			int breakcol = Integer.MAX_VALUE;
			int breakrow = Integer.MAX_VALUE;
			//如果相同名字发现，则两个实体，否则一个
			for (int i = 0; i < block.getCells().size() - 1; i++) {
				Cell ci = block.getCells().get(i);
				if (ci.getValue().isEmpty()) continue;
				for(int j = i + 1; j < block.getCells().size(); j++) {
					Cell cj = block.getCells().get(j);
					if (ci.getValue().equals(cj.getValue())) {
						if (ci.getRow() == cj.getRow()) {
							byCol = true;
							breakcol = Math.min(breakcol, Math.max(ci.getL(), cj.getL()));
						} else if (ci.getColumn() == block.getCells().get(j).getColumn()) {
							byRow = true;
							breakrow = Math.min(breakrow, Math.max(ci.getT(), cj.getT()));
						}
					}
				}
			}
			// do add operation
			List<String> related1 = new ArrayList<String>();
			List<String> related2 = new ArrayList<String>();
			Integer firstspace1 = 0;
			Integer firstspace2 = 0;
			Boolean firstIsTitle1 = false;
			Boolean firstIsTitle2 = false;
			if (byCol) {
				//TODO: Title by linespace
				
				for (Cell cell : block.getCells()) {
					if (!cell.getValue().isEmpty()) {
						if (cell.getR() < breakcol) {
							if (!firstIsTitle1) {
								if (related1.isEmpty()) {
									firstspace1 = cell.getLineSpace();
								} else {
									if (Math.abs(cell.getLineSpace() - firstspace1) > 10) {
										firstIsTitle1 = true;
									}
								}
							}
							related1.add(cell.getValue());
						} else {
							if (!firstIsTitle2) {
								if (related2.isEmpty()) {
									firstspace2 = cell.getLineSpace();
								} else {
									if (Math.abs(cell.getLineSpace() - firstspace2) > 10) {
										firstIsTitle2 = true;
									}
								}
							}
							related2.add(cell.getValue());
						}
					}
				}
				if (firstIsTitle1) {
					relateds.put(related1.remove(0),related1);
				} else {
					relateds.get("Root").addAll(related1);
				}
				if (firstIsTitle2) {
					relateds.put(related2.remove(0),related2);
				} else {
					relateds.get("Root").addAll(related2);
				}
			} else if (byRow) {
				for (Cell cell : block.getCells()) {
					if (!cell.getValue().isEmpty()) {
						if (cell.getB() < breakrow) {
							if (!firstIsTitle1) {
								if (related1.isEmpty()) {
									firstspace1 = cell.getLineSpace();
								} else {
									if (Math.abs(cell.getLineSpace() - firstspace1) > 10) {
										firstIsTitle1 = true;
									}
								}
							}
							related1.add(cell.getValue());
						} else {
							if (!firstIsTitle2) {
								if (related2.isEmpty()) {
									firstspace2 = cell.getLineSpace();
								} else {
									if (Math.abs(cell.getLineSpace() - firstspace2) > 10) {
										firstIsTitle2 = true;
									}
								}
							}
							related2.add(cell.getValue());
						}
					}
				}
				if (firstIsTitle1) {
					relateds.put(related1.remove(0),related1);
				} else {
					relateds.get("Root").addAll(related1);
				}
				if (firstIsTitle2) {
					relateds.put(related2.remove(0),related2);
				} else {
					relateds.get("Root").addAll(related2);
				}
			} else {
				for (Cell cell: block.getCells()) {
					if (!cell.getValue().isEmpty()) {
						if (!firstIsTitle1) {
							if (related1.isEmpty()) {
								firstspace1 = cell.getLineSpace();
							} else {
								if (Math.abs(cell.getLineSpace() - firstspace1) > 10) {
									firstIsTitle1 = true;
								}
							}
						}
						related1.add(cell.getValue());
					}
				}
				if (firstIsTitle1) {
					relateds.put(related1.remove(0),related1);
				} else {
					relateds.get("Root").addAll(related1);
				}
			}
		}

	public Document getOutput() {
		return output;
	}

	public void setOutput(Document output) {
		this.output = output;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, List<String>> getRelateds() {
		return relateds;
	}

	public void setRelateds(Map<String, List<String>> relateds) {
		this.relateds = relateds;
	}

	public List<String> getDetailed() {
		return detailed;
	}

	public void setDetailed(List<String> detailed) {
		this.detailed = detailed;
	}

	public List<String> getPlus() {
		return plus;
	}

	public void setPlus(List<String> plus) {
		this.plus = plus;
	}

	public XmlReader getReader() {
		return reader;
	}

	public void setReader(XmlReader reader) {
		this.reader = reader;
	}

}
