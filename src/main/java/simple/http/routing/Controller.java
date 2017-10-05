package simple.http.routing;

/**
 * Created by Attila on 17/10/05.
 */
public interface Controller {
    void registerHandlers(MappingRegistry mappingRegistry);
}
