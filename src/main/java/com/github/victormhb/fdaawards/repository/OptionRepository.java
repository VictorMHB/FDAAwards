package com.github.victormhb.fdaawards.repository;

import com.github.victormhb.fdaawards.repository.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
}
