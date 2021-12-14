package ca.ucalgary.ensf609.sample.app.api.comment;

import ca.ucalgary.ensf609.sample.domain.comment.Comment;
import lombok.Value;

import java.util.List;

@Value
public class CommentListResponse {
    List<Comment> comments;
}
