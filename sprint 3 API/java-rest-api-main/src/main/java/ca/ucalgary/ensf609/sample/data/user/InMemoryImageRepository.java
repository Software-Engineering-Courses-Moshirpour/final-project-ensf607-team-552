package ca.ucalgary.ensf609.sample.data.user;

import ca.ucalgary.ensf609.sample.app.errors.UserNotFoundException;
import ca.ucalgary.ensf609.sample.domain.image.Image;
import ca.ucalgary.ensf609.sample.domain.image.ImageRepository;
import ca.ucalgary.ensf609.sample.domain.image.NewImage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryImageRepository implements ImageRepository {

    private static final Map<String, Image> IMAGES_STORE = new ConcurrentHashMap();

    @Override
    public String create(NewImage newImage) {
        String id = UUID.randomUUID().toString();
        Image image = Image.builder()
                .id(id)
                .userId(newImage.getUserId())
                .createDate(newImage.getCreateDate())
                .address(newImage.getAddress())
                .animalID(newImage.getAnimalID())
                .type(newImage.getType())
                .build();
        IMAGES_STORE.put(id, image);

        return id;
    }
    @Override
    public List<Image> getImages(){
        return new ArrayList<>(IMAGES_STORE.values());
    }


    @Override
    public  void deleteImage(String id) throws UserNotFoundException {
        Image image= Optional.of(IMAGES_STORE.get(id)).orElseThrow(()->  new UserNotFoundException(404, "image not found."));
        IMAGES_STORE.remove(image.getId(),image);
    }
    @Override
    public  Image updateImage(Image image){
        Optional.of(IMAGES_STORE.get(image.getId())).orElseThrow(()->  new UserNotFoundException(404, "image not found."));
        IMAGES_STORE.replace(image.getId(), image);
        return  image;

    }


}
