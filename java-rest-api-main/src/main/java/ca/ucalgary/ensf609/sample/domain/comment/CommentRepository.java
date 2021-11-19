package ca.ucalgary.ensf609.sample.domain.comment;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;

import java.util.List;

public interface CommentRepository {

    String create(NewComment comment);
    List<Comment> getComments();
    void deleteComment(String id) throws UserNotFoundException;
    Comment updateComment(Comment comment) throws UserNotFoundException;
}
