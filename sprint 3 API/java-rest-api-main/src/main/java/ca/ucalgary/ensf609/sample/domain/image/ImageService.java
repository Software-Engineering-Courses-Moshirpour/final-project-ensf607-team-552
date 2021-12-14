package ca.ucalgary.ensf609.sample.domain.image;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public String create(NewImage image) {
        return imageRepository.create(image);
    }

    public List<Image> getImages(){return  imageRepository.getImages();}

    public void deleteImage(String id) throws UserNotFoundException{
        Objects.requireNonNull(id,"Image id is required");
        imageRepository.deleteImage(id);
    }

    public Image updateImage(Image image) throws UserNotFoundException{
        Objects.requireNonNull(image.getId(),"Image id is required for update");
        return  imageRepository.updateImage(image);

    }



}
