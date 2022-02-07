package ca.ucalgary.ensf609.sample.domain.image;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;

import java.util.List;

public interface ImageRepository {

    String create(NewImage image);
    List<Image> getImages();
    void deleteImage(String id) throws UserNotFoundException;
    Image updateImage(Image image) throws UserNotFoundException;
}
