package io.easy.cucumber;

import io.cucumber.core.backend.Backend;
import io.cucumber.core.backend.BackendProviderService;
import io.cucumber.core.backend.Container;
import io.cucumber.core.backend.Lookup;

import java.util.function.Supplier;

public class EasyJsonBackendProviderService implements BackendProviderService {

    @Override
    public Backend create(Lookup lookup, Container container, Supplier<ClassLoader> classLoader) {
        return new EasyCucumberBackend(container, classLoader);
    }
}
