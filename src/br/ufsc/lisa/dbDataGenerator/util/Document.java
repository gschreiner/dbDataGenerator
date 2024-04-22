package br.ufsc.lisa.dbDataGenerator.util;

public class Document {
	private String label;
	private String type;
	private String dataType;
	private int size;
	private int number;
	private double probability;
	private Object obj;
	
	public Document(String label, String type, String dataType, int size, int number, double probability, Object obj) {
		super();
		this.label = label;
		this.type = type;
		this.dataType = dataType;
		this.size = size;
		this.number = number;
		this.probability = probability;
		this.obj = obj;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	
}
