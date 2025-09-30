package com.github.victormhb.fdaawards.repository;

import com.github.victormhb.fdaawards.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByPollId(Long pollId);
    long countByPollIdAndOptionId(Long pollId, Long optionId);
    boolean existsByPollIdAndOptionId(Long pollId, Long optionId);
}
