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
import roomescape.domain.Waiting;

@Entity
@Table(name = "waiting", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "time_id", "theme_id", "order_index"}),
        @UniqueConstraint(columnNames = {"date", "time_id", "theme_id", "name"})
})
public class WaitingEntity {

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

    @Column(name = "order_index", nullable = false)
    private int orderIndex;

    protected WaitingEntity() {
    }

    private WaitingEntity(Long id, String name, LocalDate date,
                         ReservationTimeEntity time, ThemeEntity theme, int orderIndex) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.theme = theme;
        this.orderIndex = orderIndex;
    }

    public static WaitingEntity from(Waiting domain) {
        return new WaitingEntity(
                domain.getId(),
                domain.getName(),
                domain.getDate(),
                ReservationTimeEntity.from(domain.getTime()),
                ThemeEntity.from(domain.getTheme()),
                domain.getOrderIndex()
        );
    }

    public Waiting toDomain() {
        return Waiting.withId(id, name, date, time.toDomain(), theme.toDomain(), orderIndex);
    }
}
