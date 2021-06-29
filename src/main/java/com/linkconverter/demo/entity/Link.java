package com.linkconverter.demo.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Link {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String request;

	private String response;

	public Link() {
	}

	public Link(String request, String response) {
		this.request = request;
		this.response = response;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String hash) {
		this.request = hash;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String value) {
		this.response = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Link link = (Link) o;
		return Objects.equals(request, link.request) && Objects.equals(response, link.response);
	}

	@Override
	public int hashCode() {
		return Objects.hash(request, response);
	}
}
