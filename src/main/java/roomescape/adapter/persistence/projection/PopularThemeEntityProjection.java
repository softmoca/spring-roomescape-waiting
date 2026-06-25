package roomescape.adapter.persistence.projection;

import roomescape.adapter.persistence.entity.ThemeEntity;
import roomescape.domain.repository.projection.PopularThemeProjection;

public class PopularThemeEntityProjection {

    private final ThemeEntity theme;
    private final long reservationCount;

    public PopularThemeEntityProjection(ThemeEntity theme, long reservationCount) {
        this.theme = theme;
        this.reservationCount = reservationCount;
    }

    public PopularThemeProjection toDomain() {
        return new PopularThemeProjection(theme.toDomain(), reservationCount);
    }
}
