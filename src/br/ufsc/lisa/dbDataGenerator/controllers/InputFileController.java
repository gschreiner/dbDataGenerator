package br.ufsc.lisa.dbDataGenerator.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.file.UploadedFile;

import com.sun.faces.el.ELContextImpl;

import br.ufsc.lisa.dbDataGenerator.models.Column;
import br.ufsc.lisa.dbDataGenerator.models.Database;
import br.ufsc.lisa.dbDataGenerator.models.InputFile;
import br.ufsc.lisa.dbDataGenerator.models.Table;
import br.ufsc.lisa.dbDataGenerator.util.SessionContext;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;

@ManagedBean(name = "fileUpload")
@RequestScoped
public class InputFileController {

	private String input;
	private UploadedFile upload;

	public void handleFileUpload() {

		if (this.upload != null && this.upload.getFileName() != null) {
			StringBuilder resultStringBuilder = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(this.upload.getInputStream()))) {
				String line;
				while ((line = br.readLine()) != null) {
					resultStringBuilder.append(line).append("\n");
				}
			} catch (IOException e) {
				FacesMessage msg = new FacesMessage("Error", this.upload.getFileName() + " suck's!.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				e.printStackTrace();
				return;
			}

			FacesMessage msg = new FacesMessage("Successful", this.upload.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, msg);

			this.input = resultStringBuilder.toString();

		}
	}

	public void parse() {
		try {
			Statements stmts = CCJSqlParserUtil.parseStatements(this.input);

			Database db = new Database();
			db.setName("Master");

			for (Statement stmt : stmts.getStatements()) {
				if (stmt instanceof CreateTable) {
					CreateTable ct = (CreateTable) stmt;
					Table tb = new Table();
					tb.setName(ct.getTable().getName());
					for (ColumnDefinition cd : ct.getColumnDefinitions()) {
						System.out.println(cd.toString());
						Column column = new Column(cd.getColumnName(), cd.getColDataType().getDataType(), 0, 0, cd.toString().contains("PRIMARY KEY"));
						tb.getColumns().add(column);
					}
					db.getTables().add(tb);
				} else {
					System.out.println("\t-> Ignoring: " + stmt.toString());
				}
			}

			SessionContext.getInstance().setParsedDB(db);

			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect("dbDetails.xhtml");
		} catch (JSQLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public UploadedFile getUpload() {
		return upload;
	}

	public void setUpload(UploadedFile uploaded) {
		this.upload = uploaded;
	}

}
