package be.kuleuven.swop.objectron.domain.util;

/**
 * An interface of Observables.
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/11/13
 * Time: 12:30 PM
 */
public interface Observable<T> {
    /**
     * Attach an observer to this Observable.
     * @param observer
     *        The observer to attach.
     */
    public void attach(T observer);

    /**
     * Detach an observer from this Observable.
     * @param observer
     *        The observer to detach.
     */
    public void detach(T observer);
}
