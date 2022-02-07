package ca.ucalgary.ensf609.sample.data.user;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import ca.ucalgary.ensf609.sample.domain.comment.Comment;
import ca.ucalgary.ensf609.sample.domain.comment.CommentRepository;
import ca.ucalgary.ensf609.sample.domain.comment.NewComment;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCommentRepository implements CommentRepository {

    private static final Map<String, Comment> COMMENTS_STORE = new ConcurrentHashMap();

    @Override
    public String create(NewComment newComment) {
        String id = UUID.randomUUID().toString();
        Comment comment = Comment.builder()
                .id(id)
                .userId(newComment.getUserId())
                .animalId(newComment.getAnimalId())
                .description(newComment.getDescription())
                .build();
        COMMENTS_STORE.put(id, comment);

        return id;
    }
    @Override
    public List<Comment> getComments(){
        return new ArrayList<>(COMMENTS_STORE.values());
    }


    @Override
    public  void deleteComment(String id) throws UserNotFoundException {
        Comment comment= Optional.of(COMMENTS_STORE.get(id)).orElseThrow(()->  new UserNotFoundException(404, "comment not found."));
        COMMENTS_STORE.remove(comment.getId(),comment);
    }
    @Override
    public  Comment updateComment(Comment comment){
        Optional.of(COMMENTS_STORE.get(comment.getId())).orElseThrow(()->  new UserNotFoundException(404, "comment not found."));
        COMMENTS_STORE.replace(comment.getId(), comment);
        return  comment;

    }


}
