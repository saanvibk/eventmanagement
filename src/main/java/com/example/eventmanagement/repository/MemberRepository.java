package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 🔥 ADD THIS LINE
    List<Member> findByClubId(Long clubId);
}