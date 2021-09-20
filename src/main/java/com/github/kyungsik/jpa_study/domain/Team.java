package com.github.kyungsik.jpa_study.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Team {

	@Id
	@Column(name = "TEAM_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Embedded
	private Members members = new Members();

	public void addMember(Member member){
		member.setTeam(this);
		if (members.contains(member)){
			throw new RuntimeException();
		}
		members.add(member);
	}

	public Team() {
	}

	public Team(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Team{" +
			"id=" + id +
			", name='" + name + '\'' +
			'}';
	}
}
