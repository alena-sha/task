package ontravelsolutions.tasks.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity
public class Voting implements Serializable{
	@Id
	private String id;
	private String title;
	private Boolean isClosed;
	@JsonManagedReference("voting-votingOption")
	@OneToMany(mappedBy="voting", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<VotingOption> list=new ArrayList<VotingOption>();
		
	public Voting() {
		super();
		this.id=UUID.randomUUID().toString();
	}
	
	public Voting(String title, Boolean isClosed) {
		super();
		this.id=UUID.randomUUID().toString();
		this.title = title;
		this.isClosed = isClosed;
	}
	
	public List<VotingOption> getList() {
		return list;
	}
	public void setList(List<VotingOption> list) {
		this.list = list;
	}
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getIsClosed() {
		return isClosed;
	}
	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}

	
}
