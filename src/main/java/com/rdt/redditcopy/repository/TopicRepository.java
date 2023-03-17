package com.rdt.redditcopy.repository;

import com.rdt.redditcopy.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
}
