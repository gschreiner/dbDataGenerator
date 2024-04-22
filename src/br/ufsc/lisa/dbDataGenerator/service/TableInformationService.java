package br.ufsc.lisa.dbDataGenerator.service;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.ufsc.lisa.dbDataGenerator.models.Column;
import br.ufsc.lisa.dbDataGenerator.models.Database;
import br.ufsc.lisa.dbDataGenerator.models.Table;
import br.ufsc.lisa.dbDataGenerator.util.Document;

@Named
@ApplicationScoped
public class TableInformationService {

	public TreeNode getTableNodes (Database db) {
		
		TreeNode root = new DefaultTreeNode("Teste", null, null);
		
		
		for (Table tb : db.getTables()) {
			TreeNode root_table = new DefaultTreeNode(tb.getName(), new Document(tb.getName(), "Table", "-", tb.getNumber(), 0,1.0, tb), root);
			for (Column col: tb.getColumns()) {
				TreeNode col_node = new DefaultTreeNode(col.getName(), new Document(col.getName(), "Col", col.getType().toString(), 0, 0,1.0, col), root_table);
			}
		}
		
		return root;
	}
	
}
