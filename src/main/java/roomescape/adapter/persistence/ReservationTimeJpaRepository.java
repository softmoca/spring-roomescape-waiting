package roomescape.adapter.persistence;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roomescape.adapter.persistence.entity.ReservationTimeEntity;

public interface ReservationTimeJpaRepository extends JpaRepository<ReservationTimeEntity, Long> {

    @Query("""
            select rt from ReservationTimeEntity rt
            where rt.id not in (
                select r.time.id from ReservationEntity r
                where r.date = :date and r.theme.id = :themeId
            )
            """)
    List<ReservationTimeEntity> findAvailable(@Param("date") LocalDate date, @Param("themeId") Long themeId);
}
