package br.ufsc.lisa.dbDataGenerator.models;

public class Column extends DataGenerator{
	private String name;
	private Object type;
	private int size;
	private Boolean primary;
	
	
	public Boolean getPrimary() {
		return primary;
	}
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getType() {
		return type;
	}
	public void setType(Object type) {
		this.type = type;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public Column(String name, Object type, int size, int number, boolean primary) {
		super();
		this.name = name;
		this.type = type;
		this.size = size;
		this.setNumber(number);
		this.setProbability(1.0);
		this.defineSize();
		this.primary = primary;
	}
	
	private void defineSize() {
		String type = (String) this.type;
		if (this.size ==0 && (type.equals("VARCHAR") || type.equals("STRING"))) {
			this.size = 40;
		}
	}
}
