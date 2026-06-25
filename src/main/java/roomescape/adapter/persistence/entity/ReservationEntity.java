package roomescape.adapter.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import roomescape.domain.Reservation;

@Entity
@Table(name = "reservation", uniqueConstraints =
        @UniqueConstraint(columnNames = {"date", "time_id", "theme_id"}))
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "time_id")
    private ReservationTimeEntity time;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "theme_id")
    private ThemeEntity theme;

    protected ReservationEntity() {
    }

    private ReservationEntity(Long id, String name, LocalDate date,
                             ReservationTimeEntity time, ThemeEntity theme) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
    }

    public static ReservationEntity from(Reservation domain) {
        return new ReservationEntity(
                domain.getId(),
                domain.getName(),
                domain.getDate(),
                ReservationTimeEntity.from(domain.getTime()),
                ThemeEntity.from(domain.getTheme())
        );
    }

    public Reservation toDomain() {
        return Reservation.withId(id, name, date, time.toDomain(), theme.toDomain());
    }
}
