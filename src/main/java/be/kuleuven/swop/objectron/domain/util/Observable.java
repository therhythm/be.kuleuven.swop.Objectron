package be.kuleuven.swop.objectron.domain.util;

/**
 * Created with IntelliJ IDEA.
 * User: Nik
 * Date: 4/11/13
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Observable<T> {
    public void attach(T observer);
    public void detach(T observer);
}
