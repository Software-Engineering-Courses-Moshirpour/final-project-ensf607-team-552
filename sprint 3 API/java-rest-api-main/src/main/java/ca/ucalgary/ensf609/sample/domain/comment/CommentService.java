package ca.ucalgary.ensf609.sample.domain.comment;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public String create(NewComment comment) {
        return commentRepository.create(comment);
    }

    public List<Comment> getComments(){return  commentRepository.getComments();}

    public void deleteComment(String id) throws UserNotFoundException{
        Objects.requireNonNull(id,"Comment id is required");
        commentRepository.deleteComment(id);
    }

    public Comment updateComment(Comment comment) throws UserNotFoundException{
        Objects.requireNonNull(comment.getId(),"Comment id is required for update");
        return  commentRepository.updateComment(comment);

    }



}
