package com.repository;

import com.model.Prescription;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PrescribeDao extends PagingAndSortingRepository<Prescription, Integer> {

}
