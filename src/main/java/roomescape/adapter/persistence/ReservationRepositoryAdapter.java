package roomescape.adapter.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import roomescape.adapter.persistence.entity.ReservationEntity;
import roomescape.domain.Reservation;
import roomescape.domain.repository.ReservationRepository;

@Repository
public class ReservationRepositoryAdapter implements ReservationRepository {

    private final ReservationJpaRepository jpaRepository;
    private final ReservationTimeJpaRepository timeJpaRepository;

    public ReservationRepositoryAdapter(ReservationJpaRepository jpaRepository,
                                        ReservationTimeJpaRepository timeJpaRepository) {
        this.jpaRepository = jpaRepository;
        this.timeJpaRepository = timeJpaRepository;
    }

    @Override
    public List<Reservation> findAll() {
        return jpaRepository.findAll().stream().map(ReservationEntity::toDomain).toList();
    }

    @Override
    public Reservation save(Reservation reservation) {
        return jpaRepository.save(ReservationEntity.from(reservation)).toDomain();
    }

    @Override
    public void deleteById(Long id) {
        // delete-before-insert 보장: 승급 흐름에서 역 예약 DELETE를 먼저 flush (UNIQUE 충돌 방지)
        jpaRepository.deleteById(id);
        jpaRepository.flush();
    }

    @Override
    public boolean existsByDateAndTimeAndTheme(LocalDate date, Long timeId, Long themeId) {
        return jpaRepository.existsByDateAndTime_IdAndTheme_Id(date, timeId, themeId);
    }

    @Override
    public boolean existsBySlotAndName(LocalDate date, Long timeId, Long themeId, String name) {
        return jpaRepository.existsByDateAndTime_IdAndTheme_IdAndName(date, timeId, themeId, name);
    }

    @Override
    public boolean existsByTimeId(Long timeId) {
        return jpaRepository.existsByTime_Id(timeId);
    }

    @Override
    public List<Reservation> findByNameOrderByDateAscTimeAsc(String name) {
        return jpaRepository.findByNameOrderByDateAscTime_StartAtAsc(name).stream()
                .map(ReservationEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return jpaRepository.findById(id).map(ReservationEntity::toDomain);
    }

    @Override
    public boolean existsByDateAndTimeAndThemeExcludingId(LocalDate date, Long timeId, Long themeId, Long excludeId) {
        return jpaRepository.existsByDateAndTime_IdAndTheme_IdAndIdNot(date, timeId, themeId, excludeId);
    }

    @Override
    public void updateDateAndTime(Long id, LocalDate date, Long timeId) {
        jpaRepository.updateDateAndTime(id, date, timeJpaRepository.getReferenceById(timeId));
    }

    @Override
    public boolean existsByThemeId(Long themeId) {
        return jpaRepository.existsByTheme_Id(themeId);
    }
}
