package umut.backend.Batch.Ps5Finder.Models;

import lombok.Getter;
import lombok.Setter;
import umut.backend.Batch.Ps5Finder.Models.AmazonRegion;

@Getter
@Setter
public class ProductAvailability {
    private boolean nonDigitalAvailable;
    private boolean digitalAvailable;
    private AmazonRegion region;
}
