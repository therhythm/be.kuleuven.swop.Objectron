package be.kuleuven.swop.objectron.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Nik Torfs
 *         Date: 14/03/13
 *         Time: 23:23
 */
public class HandlerCatalog {
    private Map<Class<?>, Handler> catalog = new HashMap<>();

    public void addHandler(Handler handler){
        catalog.put(handler.getClass(), handler);
    }

    public Handler getHandler(Class<?> handler){
        return catalog.get(handler);
    }
}
