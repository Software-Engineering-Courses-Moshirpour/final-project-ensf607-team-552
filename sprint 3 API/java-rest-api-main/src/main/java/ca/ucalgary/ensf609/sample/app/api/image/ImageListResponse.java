package ca.ucalgary.ensf609.sample.app.api.image;

//import ca.ucalgary.ensf609.sample.domain.animal.Animal;
import ca.ucalgary.ensf609.sample.domain.image.Image;
import lombok.Value;

import java.util.List;

@Value
public class ImageListResponse {
    List<Image> images;
}
