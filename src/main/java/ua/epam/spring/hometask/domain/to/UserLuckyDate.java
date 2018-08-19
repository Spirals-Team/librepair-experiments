package ua.epam.spring.hometask.domain.to;

import java.time.LocalDateTime;

/**
 * Created by Vladimir on 29.10.2017.
 */
public class UserLuckyDate {
    private Long userId;
    private LocalDateTime luckyDate;

    public UserLuckyDate(Long userId, LocalDateTime luckyDate) {
        this.userId = userId;
        this.luckyDate = luckyDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getLuckyDate() {
        return luckyDate;
    }

    public void setLuckyDate(LocalDateTime luckyDate) {
        this.luckyDate = luckyDate;
    }
}
