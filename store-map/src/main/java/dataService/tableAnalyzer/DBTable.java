package dataService.tableAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class DBTable {
	String name;
	List<String> schema = new ArrayList<String>();
	String pk;
	List<String> fks = new ArrayList<String>();
	
	public DBTable(String name) {
		this.name = escape(name);
		pk = this.name + "_id";
		schema.add(pk);
	}

	public static String escape(String str) {
		String res = "";
		//res = str.replaceAll(":", "").trim().replaceAll("\\.", "").replaceAll(" ", "_").replace("\n", "_").toLowerCase();
		res = str.trim().replaceAll("\\s", "_").toLowerCase().replaceAll("[^\\w]", "");
		return res;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = escape(name);
	}

	public List<String> getSchema() {
		return schema;
	}

	public void setSchema(List<String> schema) {
		this.schema = schema;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public List<String> getFks() {
		return fks;
	}

	public void setFks(List<String> fks) {
		this.fks = fks;
	}
}
