package sky.uk.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import sky.uk.resource.DemoResource;

@Component
public class JerseyConfig extends ResourceConfig{
    public JerseyConfig() {
        register(DemoResource.class);
    }
}
