package com.rdt.redditcopy.repository;

import com.rdt.redditcopy.model.Sub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SubRepository extends JpaRepository<Sub, Integer> {

}
