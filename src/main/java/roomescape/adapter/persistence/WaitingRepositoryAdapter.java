package roomescape.adapter.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import roomescape.adapter.persistence.entity.WaitingEntity;
import roomescape.domain.Waiting;
import roomescape.domain.repository.WaitingRepository;

@Repository
public class WaitingRepositoryAdapter implements WaitingRepository {

    private final WaitingJpaRepository jpaRepository;

    public WaitingRepositoryAdapter(WaitingJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Waiting save(Waiting waiting) {
        return jpaRepository.save(WaitingEntity.from(waiting)).toDomain();
    }

    @Override
    public List<Waiting> findBySlot(LocalDate date, Long timeId, Long themeId) {
        return jpaRepository.findByDateAndTime_IdAndTheme_IdOrderByOrderIndexAsc(date, timeId, themeId).stream()
                .map(WaitingEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Waiting> findById(Long id) {
        return jpaRepository.findById(id).map(WaitingEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void updateOrderIndex(Long id, int newOrderIndex) {
        jpaRepository.updateOrderIndex(id, newOrderIndex);
    }

    @Override
    public List<Waiting> findByName(String name) {
        return jpaRepository.findByNameOrderByDateAscTime_StartAtAsc(name).stream()
                .map(WaitingEntity::toDomain)
                .toList();
    }
}
