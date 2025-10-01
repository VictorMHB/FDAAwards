package com.github.victormhb.fdaawards.repository;

import com.github.victormhb.fdaawards.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    List<Poll> findByStatusAndOpeningDateBefore(Poll.Status status, LocalDateTime now);
    List<Poll> findByStatusAndClosingDateIsNotNullAndClosingDateBefore(Poll.Status status, LocalDateTime now);
}
