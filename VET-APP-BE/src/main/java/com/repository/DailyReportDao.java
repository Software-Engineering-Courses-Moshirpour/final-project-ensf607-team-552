package com.repository;


import com.model.AnimalStatusHistory;
import com.model.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyReportDao extends PagingAndSortingRepository<AnimalStatusHistory, Integer> {
    @Query(value = "SELECT * FROM animalstatushist r WHERE r.user_id=?1", nativeQuery = true)
    Iterable<AnimalStatusHistory> findReportsByUserId(int userId);
}