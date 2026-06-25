package roomescape.adapter.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roomescape.adapter.persistence.entity.WaitingEntity;

public interface WaitingJpaRepository extends JpaRepository<WaitingEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"time", "theme"})
    Optional<WaitingEntity> findById(Long id);

    @EntityGraph(attributePaths = {"time", "theme"})
    List<WaitingEntity> findByDateAndTime_IdAndTheme_IdOrderByOrderIndexAsc(LocalDate date, Long timeId, Long themeId);

    @EntityGraph(attributePaths = {"time", "theme"})
    List<WaitingEntity> findByNameOrderByDateAscTime_StartAtAsc(String name);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update WaitingEntity w set w.orderIndex = :orderIndex where w.id = :id")
    void updateOrderIndex(@Param("id") Long id, @Param("orderIndex") int orderIndex);
}
