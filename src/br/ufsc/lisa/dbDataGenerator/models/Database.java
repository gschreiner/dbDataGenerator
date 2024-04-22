package br.ufsc.lisa.dbDataGenerator.models;

import java.util.LinkedList;
import java.util.List;

public class Database {
	
	private String uri;
	private String name;
	private String user;
	private List<Table> tables;
	
	public Database() {
		this.tables = new LinkedList<Table>();
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	
	
}
