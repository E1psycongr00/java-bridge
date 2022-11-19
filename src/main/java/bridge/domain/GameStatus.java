package bridge.domain;

public enum GameStatus {
    SUCCESS(false),
    IN_PROGRESS(false),
    FAIL(true);

    private final boolean retry;

    GameStatus(boolean retry) {
        this.retry = retry;
    }

    public boolean needCallRetry() {
        return this.retry;
    }
}
