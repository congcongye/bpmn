package dataService.tableAnalyzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DatabaseGenerator2 {
	String sql = "";
	Map<String, DBTable> tables = new HashMap<String, DBTable>();
	
	public DatabaseGenerator2(List<AdvancedTableAnalyzer> anas, String bpmnName) {
		//sql = sql.concat("CREATE DATABASE ").concat(bpmnName).concat(";\n");
		//sql = sql.concat("USE ").concat(bpmnName).concat(";\n");
		for (AdvancedTableAnalyzer ana : anas) {
			createTable(ana, bpmnName, anas.indexOf(ana));
		}
		// 找更多的foreign key
		for (DBTable table : tables.values()) {
			for (String property : table.getSchema()) {
				if (!property.equals(table.getPk()) && property.contains("_id")
						&& !table.getFks().contains(property)) {
					for (String name : tables.keySet()) {
						if (property.equals(name + "_id")) {
							table.fks.add(property);
							break;
						}
					}
				}
			}
		}
		//output();
		//System.out.println(sql);
		createConnection(bpmnName);
		
	}
	
	public void createConnection(String bpmnName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
         
	        //一开始必须填一个已经存在的数据库
	        String url = "jdbc:mysql://localhost:3306/bpmn?useUnicode=true&characterEncoding=utf-8";
	        Connection conn = DriverManager.getConnection(url, "root", "123456");
	        Statement stat = conn.createStatement();
	         
	        //创建数据库hello
//	        stat.executeUpdate("DROP DATABASE ".concat(bpmnName).concat(";\n"));
	        stat.executeUpdate("CREATE DATABASE ".concat(bpmnName).concat(";\n"));
	         
	        //打开创建的数据库
	        stat.close();
	        conn.close();
	        url = "jdbc:mysql://localhost:3306/" + bpmnName + "?useUnicode=true&characterEncoding=utf-8";
	        conn = DriverManager.getConnection(url, "root", "123456");
	        stat = conn.createStatement();
	         
	        for (Entry<String, DBTable> table: tables.entrySet()) {
				//sql = sql.concat("DROP TABLE ").concat(table.getKey()).concat(";\n");
				sql = sql.concat("CREATE TABLE `").concat(table.getKey()).concat("`(\n");
				for (String property : table.getValue().getSchema()) {
					sql = sql.concat(property).concat(" ").concat("varchar(255),\n");
				}
				sql = sql.concat("primary key (").concat(table.getValue().getPk()).concat("));\n");
				stat.execute(sql);
				sql = "";
			}
			for (DBTable table : tables.values()) {
				if (!table.getFks().isEmpty()) {
				for (String fk : table.getFks()) {
					stat.execute("ALTER TABLE `".concat(table.getName())
							.concat("` add foreign key (").concat(fk).concat(") references `")
							.concat(fk.substring(0, fk.lastIndexOf("_"))).concat("` (").concat(fk).concat(");\n"));
				}
			}
			}
	        //创建表test
	        
	        //System.out.println(t);
	        stat.close();
	        conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createTable(AdvancedTableAnalyzer ana, String bpmnName, Integer i) {
		// related first
		for(Entry<String,List<String>> related : ana.getRelateds().entrySet()) {
			String relatedTitle = DBTable.escape(related.getKey());
			// 表已有，加新属性进去
			if (!tables.containsKey(relatedTitle)) {
				tables.put(relatedTitle, new DBTable(relatedTitle));
			}
			for (String property : related.getValue()) {
				String pro = DBTable.escape(property);
				if (!isPk(pro, relatedTitle) && !tables.get(relatedTitle).getSchema().contains(pro)){
					tables.get(relatedTitle).getSchema().add(pro);
				}
			}
		}
		
		// title
		String title = "";
		if (ana.getTitle().equals("Root")) {
			title = "root_".concat(i.toString());
		} else {
			title = DBTable.escape(ana.getTitle());
		}
		// plus
		if (!tables.containsKey(title)) {
			tables.put(title, new DBTable(title));
		}
		for (String p : ana.getPlus()) {
			String pro = DBTable.escape(p);
			if (!isPk(pro, title) && !tables.get(title).getSchema().contains(pro)){
				tables.get(title).getSchema().add(pro);
			}
		}
		// related
		for (String related : ana.getRelateds().keySet()) {
			String rel = DBTable.escape(related);
			String key = tables.get(rel).getPk();
			if (!tables.get(title).getSchema().contains(key)) {
				tables.get(title).getSchema().add(key);
				tables.get(title).getFks().add(key);
			} else if (!tables.get(title).getFks().contains(key)) {
				tables.get(title).getFks().add(key);
			}
		}
		
		// detailed
		String detailedTitle = title.concat("_detail");
		if (!tables.containsKey(detailedTitle)) {
			tables.put(detailedTitle, new DBTable(detailedTitle));
		}
		for (String p : ana.getDetailed()) {
			String pro = DBTable.escape(p);
			if (!isPk(pro, detailedTitle) && !tables.get(detailedTitle).getSchema().contains(pro)) {
				tables.get(detailedTitle).getSchema().add(pro);
			}
		}
		tables.get(detailedTitle).getFks().add(title + "_id");
		tables.get(detailedTitle).getSchema().add(title + "_id");
	}
	
	public void output() {
		for (Entry<String, DBTable> table: tables.entrySet()) {
			//sql = sql.concat("DROP TABLE ").concat(table.getKey()).concat(";\n");
			sql = sql.concat("CREATE TABLE `").concat(table.getKey()).concat("`(\n");
			for (String property : table.getValue().getSchema()) {
				sql = sql.concat(property).concat(" ").concat("varchar(255),\n");
			}
			sql = sql.concat("primary key (").concat(table.getValue().getPk()).concat("));\n");
		}
		for (DBTable table : tables.values()) {
			if (!table.getFks().isEmpty()) {
			for (String fk : table.getFks()) {
				sql = sql.concat("ALTER TABLE `").concat(table.getName())
						.concat("` add foreign key (").concat(fk).concat(") references `")
						.concat(fk.substring(0, fk.lastIndexOf("_"))).concat("` (").concat(fk).concat(");\n");
			}
		}
		}
	}
	
	public Boolean isPk(String property, String title) {
		if (property.equals(title + "_id") || property.equals(title + "_no") || property.equals(title + "#"))
			return true;
		return false;
	}
}
