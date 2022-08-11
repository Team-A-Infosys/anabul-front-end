package team.kucing.anabulshopcare.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import team.kucing.anabulshopcare.dto.response.CategoryResponse;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE category SET is_deleted = true WHERE category_id=?")
@Where(clause = "is_deleted = false")

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;

    private Boolean isDeleted = Boolean.FALSE;

    public CategoryResponse convertResponseCategory(){
        return CategoryResponse.builder().categoryName(this.categoryName).build();
    }

}