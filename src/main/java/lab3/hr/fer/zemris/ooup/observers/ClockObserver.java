package lab3.hr.fer.zemris.ooup.observers;

import java.time.LocalDateTime;

public interface ClockObserver {

    void updateTime(LocalDateTime now);
}
