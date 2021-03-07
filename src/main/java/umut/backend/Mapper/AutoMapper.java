package umut.backend.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import umut.backend.DTOs.*;
import umut.backend.Entities.*;
import umut.backend.Requests.RequestSignUp;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AutoMapper {

    QuizDTO toQuizDTO(Quiz quiz);

    @Mapping(target = "quizId", source = "sourceQuizId")
    QuizSong toQuizSong(QuizSongDTO quizSongDTOList, UUID sourceQuizId);

    AppUser toAppUser(RequestSignUp request);

    UserDTO toUserDTO(AppUser user);

    ProductDTO toProductDTO(Product product);

    @Mapping(target = "latestPrice", source = "latestPrice")
    @Mapping(target = "productPrices", ignore = true)
    ProductDTO toProductDTO(Product product, BigDecimal latestPrice);

    ProductCategoryDTO toProductCategoryDTO(ProductCategory productCategory);

    ProductCategory toProductCategory(ProductCategoryDTO dto);

    ProductPriceDTO toProductPriceDTO(ProductPrice productPrice);

    ProductPrice toProductPrice(ProductPriceDTO dto);

    Website toWebsite(WebsiteDTO dto);

    WebsiteDTO toWebsiteDTO(Website website);

}
