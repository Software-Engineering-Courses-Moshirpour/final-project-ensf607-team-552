package com.repository;

import com.model.Prescription;
import com.model.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PrescribeDao extends PagingAndSortingRepository<Prescription, Integer> {
    @Query(value = "SELECT * FROM request r WHERE r.user=?1", nativeQuery = true)
    List<Prescription> findPreByTechId(int userId);



}
