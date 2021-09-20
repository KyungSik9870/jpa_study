package com.github.kyungsik.jpa_study.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberTest {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Test
	void save() {
		Team team = new Team("HyeYong");
		teamRepository.save(team);

		Member member = new Member("기메링", 27);
		Member member2 = new Member("류경식", 29);

		memberRepository.save(member);
		memberRepository.save(member2);

		team.addMember(member);
		team.addMember(member2);

		Member newMember = memberRepository.findByName("기메링").get();
		assertThat(newMember).isNotNull();

		System.out.println(member);
		System.out.println(member2);

	}
}
