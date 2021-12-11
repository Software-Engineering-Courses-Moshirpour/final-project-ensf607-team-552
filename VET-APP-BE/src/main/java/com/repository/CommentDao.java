package com.repository;

import com.model.Comment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentDao extends PagingAndSortingRepository<Comment, Integer> {

}
