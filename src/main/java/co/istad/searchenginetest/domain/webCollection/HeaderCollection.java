package co.istad.searchenginetest.domain.webCollection;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HeaderCollection {
    Integer id;
    String title;
    String description;
    String author;
    String keyword;
    String thumbnail;
    String domain;
}
