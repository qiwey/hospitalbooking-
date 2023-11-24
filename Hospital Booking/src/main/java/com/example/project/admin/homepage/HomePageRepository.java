package com.example.project.admin.homepage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomePageRepository extends JpaRepository<HomePageInfo, Long> {
    HomePageInfo findTopBy();
}
