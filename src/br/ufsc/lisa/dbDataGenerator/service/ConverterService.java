package br.ufsc.lisa.dbDataGenerator.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.objenesis.strategy.StdInstantiatorStrategy;

import br.ufsc.lisa.dbDataGenerator.models.Column;
import br.ufsc.lisa.dbDataGenerator.models.Database;
import br.ufsc.lisa.dbDataGenerator.models.Table;

public class ConverterService {

	private Database db;

	public ConverterService(Database db) {
		this.db = db;
	}

	public String convertTables() {
		StringBuilder sb = new StringBuilder();
		boolean generatedHead = false;

		for (Table tb : db.getTables()) {
			System.out.println("Generating values: " + tb.getName());

			for (int i = 0; i < tb.getNumber(); i++) {

				StringBuilder sthead = new StringBuilder();
				sthead.append("INSERT INTO " + tb.getName() + " (");
				tb.getColumns().forEach(c -> {
					sthead.append(c.getName() + ", ");
				});

				// Table tb = db.getTables().get(0);
				StringBuilder sbt = new StringBuilder();
				sbt.append(sthead.substring(0, sthead.toString().length() - 2) + ") VALUES (");
				boolean fpk = true;
				for (Column c : tb.getColumns()) {  
					
					sbt.append(this.generateData(c, i, !fpk) + ", ");
					if (c.getPrimary())
						fpk = false;
				}

				sb.append(sbt.toString().substring(0, sbt.toString().length() - 2) + ");\n");
			}
			// sb = new StringBuilder();
			//break;
		}

		return sb.toString();
	}

	private String generateData(Column col, int pk, boolean secPK) {
		StringBuilder str = new StringBuilder();

		String type = (String) col.getType();
		int willGenerate = (int) col.getProbability() * 100;
		if (RandomUtils.nextInt(0, 100) <= willGenerate) {

			if (type.toUpperCase().equals("INTEGER") || type.toUpperCase().equals("INT")) {
				Integer i = 0;
				if (col.getPrimary() && !secPK) {
					i = pk + 1;
				} else {
					if (col.getNumber() > 0) {
						i = RandomUtils.nextInt(1, col.getNumber());
					} else {
						i = ((int) Math.random() % 100) + 1;
					}
				}
				str.append(i);
			} else if (type.toUpperCase().equals("STRING") || type.toUpperCase().equals("VARCHAR")) {
				str.append("'" + RandomStringUtils.random(col.getSize(), true, false) + "'");
			} else if (type.toUpperCase().equals("BOOLEAN")) {
				str.append(RandomUtils.nextBoolean());
			} else if (type.toUpperCase().equals("NUMERIC") || type.toUpperCase().equals("DOUBLE")) {
				DecimalFormat df = new DecimalFormat("##.######");
				str.append(df.format(RandomUtils.nextDouble(0, +35)));
			} else if (type.toUpperCase().equals("FLOAT")) {
				DecimalFormat df = new DecimalFormat("##.######");
				str.append(df.format(RandomUtils.nextFloat(0, +35)).replace(',', '.'));
			} else if (type.toUpperCase().equals("TIMESTAMP")) {
				LocalDateTime data = LocalDateTime.now();
				data = data.minusDays(RandomUtils.nextInt(2, 3000));
				data = data.minusSeconds(RandomUtils.nextInt(2, 1000));
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				str.append("'" + data.format(formatter) + "'");
			} else {
				System.err.println("Tipo não definido " + col.getType());
			}
		} else {
			str.append(" NULL ");
		}

		return str.toString();
	}

}
