package roomescape.adapter.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

@Entity
@Table(name = "reservation_time")
public class ReservationTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalTime startAt;

    protected ReservationTimeEntity() {
    }

    private ReservationTimeEntity(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTimeEntity from(ReservationTime domain) {
        return new ReservationTimeEntity(domain.getId(), domain.getStartAt());
    }

    public ReservationTime toDomain() {
        return ReservationTime.withId(id, startAt);
    }
}
