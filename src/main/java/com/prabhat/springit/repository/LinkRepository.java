package com.prabhat.springit.repository;

import com.prabhat.springit.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {

}
