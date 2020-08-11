package bean;

import java.io.Serializable;

public class Evaluator implements Serializable {

	private static final long serialVersionUID = -1649719759212378207L;
	
	private int id;
	private String name;
	private String email;
	private int evaluatedEntities;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEvaluatedEntities() {
		return evaluatedEntities;
	}
	public void setEvaluatedEntities(int evaluatedEntities) {
		this.evaluatedEntities = evaluatedEntities;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
