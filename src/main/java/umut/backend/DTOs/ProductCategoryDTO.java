package umut.backend.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryDTO extends BaseDTO {
    private UUID id;
    private String name;
    private String url;
    private String subPath;
    private WebsiteDTO website;
    private List<ProductDTO> products;
}
