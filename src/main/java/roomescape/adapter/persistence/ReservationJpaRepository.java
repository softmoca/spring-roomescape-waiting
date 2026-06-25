package roomescape.adapter.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roomescape.adapter.persistence.entity.ReservationEntity;
import roomescape.adapter.persistence.entity.ReservationTimeEntity;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"time", "theme"})
    List<ReservationEntity> findAll();

    @Override
    @EntityGraph(attributePaths = {"time", "theme"})
    Optional<ReservationEntity> findById(Long id);

    boolean existsByDateAndTime_IdAndTheme_Id(LocalDate date, Long timeId, Long themeId);

    boolean existsByDateAndTime_IdAndTheme_IdAndName(LocalDate date, Long timeId, Long themeId, String name);

    boolean existsByDateAndTime_IdAndTheme_IdAndIdNot(LocalDate date, Long timeId, Long themeId, Long excludeId);

    boolean existsByTime_Id(Long timeId);

    boolean existsByTheme_Id(Long themeId);

    @EntityGraph(attributePaths = {"time", "theme"})
    List<ReservationEntity> findByNameOrderByDateAscTime_StartAtAsc(String name);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update ReservationEntity r set r.date = :date, r.time = :time where r.id = :id")
    void updateDateAndTime(@Param("id") Long id, @Param("date") LocalDate date, @Param("time") ReservationTimeEntity time);
}
