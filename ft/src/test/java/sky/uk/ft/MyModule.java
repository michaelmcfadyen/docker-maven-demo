package sky.uk.ft;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.inject.Inject;

public class MyModule implements Module {
    @Override
    public void configure(Binder binder) {
        Names.bindProperties(binder, System.getProperties());
        binder.bind(Session.class).toProvider(SessionProvider.class).asEagerSingleton();
        binder.bind(HttpClient.class).toInstance(HttpClientBuilder.create().build());
        binder.bind(ObjectMapper.class).toInstance(new ObjectMapper());
    }

    private static class SessionProvider implements Provider<Session> {
        @Inject
        @Named("cassandra.host")
        private String host;

        @Inject
        @Named("cassandra.port")
        private int port;

        @Inject
        @Named("cassandra.keyspace")
        private String keyspace;

        @Override
        public Session get() {
            Cluster cluster = Cluster.builder().addContactPoint(host).withPort(port).build();
            return cluster.connect(keyspace);
        }
    }
}
