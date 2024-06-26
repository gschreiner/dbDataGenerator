package br.ufsc.lisa.dbDataGenerator.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.ufsc.lisa.dbDataGenerator.models.Database;

public class SessionContext {
	 private static SessionContext instance;
     
	    public static SessionContext getInstance(){
	         if (instance == null){
	             instance = new SessionContext();
	         }
	          
	         return instance;
	    }
	     
	    private SessionContext(){
	          
	    }
	     
	    private ExternalContext currentExternalContext(){
	         if (FacesContext.getCurrentInstance() == null){
	             throw new RuntimeException("O FacesContext não pode ser chamado fora de uma requisição HTTP");
	         }else{
	             return FacesContext.getCurrentInstance().getExternalContext();
	         }
	    }
	     
	    public Database getParsedDB(){
	         return (Database) getAttribute("getParsedDB");
	    }
	     
	    public void setParsedDB(Database parsedDB){
	         setAttribute("getParsedDB", parsedDB);
	    }
	     
	    public void encerrarSessao(){   
	         currentExternalContext().invalidateSession();
	    }
	     
	    public Object getAttribute(String nome){
	         return currentExternalContext().getSessionMap().get(nome);
	    }
	     
	    public void setAttribute(String nome, Object valor){
	         currentExternalContext().getSessionMap().put(nome, valor);
	    }

}
