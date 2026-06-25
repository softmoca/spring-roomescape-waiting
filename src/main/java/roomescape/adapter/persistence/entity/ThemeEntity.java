package roomescape.adapter.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import roomescape.domain.Theme;

@Entity
@Table(name = "theme")
public class ThemeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String thumbnailUrl;

    protected ThemeEntity() {
    }

    private ThemeEntity(Long id, String name, String description, String thumbnailUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static ThemeEntity from(Theme domain) {
        return new ThemeEntity(domain.getId(), domain.getName(), domain.getDescription(), domain.getThumbnailUrl());
    }

    public Theme toDomain() {
        return Theme.withId(id, name, description, thumbnailUrl);
    }
}
