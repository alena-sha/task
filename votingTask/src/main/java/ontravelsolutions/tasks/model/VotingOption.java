package ontravelsolutions.tasks.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
public class VotingOption implements Serializable{
	@Id @GeneratedValue
	private int id;
	private String title;
	private int numberOfVotes;
	@Transient
	private AtomicInteger _count= new AtomicInteger(0);;
	@JsonBackReference("voting-votingOption")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "voting")
	private Voting voting;
	
	public VotingOption() {
		super();
	}
	public VotingOption(String title,int numberOfVotes,Voting voting) {
		super();
		this.title=title;
		this.numberOfVotes=numberOfVotes;
		this.voting=voting;
	}
	
	public VotingOption(int id,String title,int numberOfVotes,Voting voting) {
		super();
		this.id=id;
		this.title=title;
		this.numberOfVotes=numberOfVotes;
		this.voting=voting;
	}
	
	public Voting getVoting() {
		return voting;
	}
	public void setVoting(Voting voting) {
		this.voting = voting;
	}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getNumberOfVotes() {
		return numberOfVotes;
	}
	public void vote() {
		this.numberOfVotes=_count.incrementAndGet();
	}
	
	
}
