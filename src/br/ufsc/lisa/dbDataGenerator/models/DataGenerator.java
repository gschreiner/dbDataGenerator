package br.ufsc.lisa.dbDataGenerator.models;

public abstract class DataGenerator {
	private int number;
	private double probability;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getProbability() {
		return probability;
	}
	public void setProbability(double probability) {
		this.probability = probability;
	}
	
	

}
