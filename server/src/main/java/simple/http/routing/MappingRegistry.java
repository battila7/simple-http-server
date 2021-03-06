package simple.http.routing;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.unmodifiableList;

public class MappingRegistry {
    private final List<Mapping> mappings;

    public MappingRegistry() {
        this.mappings = new LinkedList<>();
    }

    public MappingRegistry register(Mapping mapping) {
        this.mappings.add(Objects.requireNonNull(mapping));

        return this;
    }

    List<Mapping> getMappings() {
        return unmodifiableList(mappings);
    }
}
