import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

class CollisionMap<T> {
    private Map<T, Long> timerMap = new HashMap<>();
    static final long TIMER_START = 16;

    public void add(T elem) {
        timerMap.put(elem, TIMER_START);
    }

    public boolean contains(T elem) {
        // If one of you figures out a way to change and set the value
        // with 1 single lookup, then consider yourself better than AleMazzo.
        Long timerValue = timerMap.get(elem);
        if (timerValue != null) {
            timerMap.put(elem, TIMER_START);
        }
        return timerValue != null;
    }

    public void update() {
        timerMap = timerMap.entrySet().stream()
            .filter(x -> x.getValue() != 0)
            .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> v.getValue() - 1));
    }

    public long getTimer(T elem) {
        Long value = timerMap.get(elem);
        return value == null ? -1 : value;
    }
}
