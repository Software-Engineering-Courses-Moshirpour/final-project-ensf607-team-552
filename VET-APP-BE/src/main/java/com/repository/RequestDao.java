package com.repository;

import com.model.Animal;
import com.model.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestDao extends PagingAndSortingRepository<Request, Integer> {

    @Query(value = "SELECT * FROM request r WHERE r.instruct_id=?1", nativeQuery = true)
    Iterable<Request> findRequestByInstructId(int instrucId);

}

