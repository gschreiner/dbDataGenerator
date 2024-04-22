package br.ufsc.lisa.dbDataGenerator.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

import br.ufsc.lisa.dbDataGenerator.models.Column;
import br.ufsc.lisa.dbDataGenerator.models.DataGenerator;
import br.ufsc.lisa.dbDataGenerator.models.Database;
import br.ufsc.lisa.dbDataGenerator.models.Table;
import br.ufsc.lisa.dbDataGenerator.service.ConverterService;
import br.ufsc.lisa.dbDataGenerator.service.TableInformationService;
import br.ufsc.lisa.dbDataGenerator.util.Document;
import br.ufsc.lisa.dbDataGenerator.util.SessionContext;

@ManagedBean(name = "tablesController")
@RequestScoped
public class TablesController {
	
	private TreeNode rootDatabase;
	
	private int maxValue = 1000;
	private Database db = new Database();
	
	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
	
	
	private StreamedContent file;

	
	
	public void resetMaxNumber() {
		System.out.println(maxValue);
		for (Table tb : db.getTables()) {
			tb.setNumber((int) Math.round(this.getMaxValue()*tb.getProbability()));
			for (Column col: tb.getColumns()) {
				col.setNumber(tb.getNumber());
				col.setNumber((int) Math.round(this.getMaxValue()*col.getProbability()));
			}
		}
		this.loadDatabaseThree();
	}


	
	@PostConstruct
	public void loadDatabaseThree() {
		
		this.db = SessionContext.getInstance().getParsedDB();
//		this.rootDatabase = this.tableInfoService.getTableNodes(this.db);
		TreeNode root = new DefaultTreeNode("Teste", null, null);
		
		
		for (Table tb : db.getTables()) {
			TreeNode root_table = new DefaultTreeNode(tb.getName(), new Document(tb.getName(), "Table", "-", 0, tb.getNumber(),tb.getProbability(), tb), root);
			for (Column col: tb.getColumns()) {
				TreeNode col_node = new DefaultTreeNode(col.getName(), new Document(col.getName(), "Col", col.getType().toString(), col.getSize(),col.getNumber(),col.getProbability(), col), root_table);
			}
		}
		this.rootDatabase = root;
	}

	public void rowEdit(RowEditEvent<TreeNode> event) {
		Document doc = (Document) event.getObject().getData();
		
		DataGenerator dg = (DataGenerator) doc.getObj();
		dg.setNumber(doc.getNumber());
		if (dg.getProbability() != doc.getProbability()) {
			dg.setProbability(doc.getProbability());
			dg.setNumber((int) Math.round(this.getMaxValue()*doc.getProbability()));
		}
		
		
		FacesMessage msg = new FacesMessage("Document Edited", doc.toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void convertDB() {
		ConverterService cs = new ConverterService(db);
		
		 FileWriter myWriter;
		try {
			myWriter = new FileWriter("temp.sql");
			 myWriter.write(cs.convertTables());
		      myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
		this.file = DefaultStreamedContent.builder()
	            .name("file_"+this.db.getName()+"("+this.maxValue+").sql")
	            .contentType("document/sql")
	            .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("temp.sql"))
	            .build();
	}
	

	public TreeNode getRootDatabase() {
		return rootDatabase;
	}


	public void setRootDatabase(TreeNode rootDatabase) {
		this.rootDatabase = rootDatabase;
	}


	public Database getDb() {
		return db;
	}


	public void setDb(Database db) {
		this.db = db;
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}
	
	

}
