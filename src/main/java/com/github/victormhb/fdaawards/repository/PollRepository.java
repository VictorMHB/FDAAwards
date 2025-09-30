package com.github.victormhb.fdaawards.repository;

import com.github.victormhb.fdaawards.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
}
