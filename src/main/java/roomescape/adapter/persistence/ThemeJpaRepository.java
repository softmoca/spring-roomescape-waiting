package roomescape.adapter.persistence;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roomescape.adapter.persistence.entity.ThemeEntity;
import roomescape.adapter.persistence.projection.PopularThemeEntityProjection;

public interface ThemeJpaRepository extends JpaRepository<ThemeEntity, Long> {

    @Query("""
            select new roomescape.adapter.persistence.projection.PopularThemeEntityProjection(t, count(r))
            from ThemeEntity t, ReservationEntity r
            where r.theme = t and r.date >= :from and r.date < :to
            group by t
            order by count(r) desc
            """)
    List<PopularThemeEntityProjection> findPopularBetween(@Param("from") LocalDate from,
                                                          @Param("to") LocalDate to,
                                                          Pageable pageable);
}
