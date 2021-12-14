package ca.ucalgary.ensf609.sample.app.api.prescriptionitem;

//import ca.ucalgary.ensf609.sample.domain.animal.Animal;
import ca.ucalgary.ensf609.sample.domain.prescriptionitem.PrescriptionItem;
import lombok.Value;

import java.util.List;

@Value
public class PrescriptionItemListResponse {
    List<PrescriptionItem> prescriptionItems;
}
