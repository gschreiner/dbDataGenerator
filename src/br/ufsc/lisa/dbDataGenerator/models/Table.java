package br.ufsc.lisa.dbDataGenerator.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Table extends DataGenerator {
	
	private String name;
	private List<Column> columns;
	
	public Table() {
		this.columns = new LinkedList();
		this.setNumber(0);
		this.setProbability(1.0);
	}
	public Table(String n) {
		this.name = n;
		this.columns = new LinkedList();
		this.setNumber(0);
		this.setProbability(1.0);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> collumns) {
		this.columns = collumns;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	

}
