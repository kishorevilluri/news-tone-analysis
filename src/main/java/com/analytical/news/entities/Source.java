package com.analytical.news.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sources", schema="news")
public class Source {
	
	@Id
	@Column(name="source_id")
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private Long sourceId;
	
	@Column(name="NAME")
	private String name;

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long source_id) {
		this.sourceId = source_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (sourceId ^ (sourceId >>> 32));
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
		Source other = (Source) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sourceId != other.sourceId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Source [sourceId=" + sourceId + ", name=" + name + "]";
	}
}
