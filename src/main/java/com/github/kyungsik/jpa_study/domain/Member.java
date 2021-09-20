package com.github.kyungsik.jpa_study.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Member {

	@Id
	@Column(name = "MEMBER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 3000)
	private String name;

	private Integer age;

	@ManyToOne
	@JoinColumn(name = "TEAM_ID", foreignKey = @ForeignKey(name = "fk_member_team"))
	private Team team;

	public void setTeam(Team team){
		this.team = team;
	}

	public Member() {
	}

	public Member(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Member{" +
			"id=" + id +
			", name='" + name + '\'' +
			", age=" + age +
			", team=" + team +
			'}';
	}
}
