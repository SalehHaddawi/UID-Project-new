package Threading;

public interface ThreadCompleteListener {
    void notifyOfThreadComplete(final Runnable runnable);
}
