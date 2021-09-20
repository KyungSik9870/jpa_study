package com.github.kyungsik.jpa_study.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Members {

	@OneToMany(mappedBy = "team")
	private List<Member> members = new ArrayList<>();

	public Members() {
	}

	public boolean contains(Member member) {
		return this.members.contains(member);
	}

	public void add(Member member) {
		this.members.add(member);
	}
}
