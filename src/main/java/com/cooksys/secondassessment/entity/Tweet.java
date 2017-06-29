package com.cooksys.secondassessment.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tweet")
public class Tweet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="userId", referencedColumnName = "id", nullable=false)
	private User user;
	private Timestamp posted;
	
	@Column(nullable=true)
	private String content;
	
	@OneToOne()
	@JoinColumn(name="inReplyToId", referencedColumnName = "id", nullable=true)
	private Tweet inReplyTo;
	
	@OneToOne()
	@JoinColumn(name="repostOfId", referencedColumnName = "id", nullable=true)
	private Tweet repostOf;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Timestamp getPosted() {
		return posted;
	}
	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}
	public String getContent() {
		return content;
	}
	
	
	public void setContent(String content) {
		this.content = content;
	}
	public Tweet getInReplyTo() {
		return inReplyTo;
	}
	public void setInReplyTo(Tweet inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	public Tweet getRepostOf() {
		return repostOf;
	}
	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tweet other = (Tweet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
