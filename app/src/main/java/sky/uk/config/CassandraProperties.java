package sky.uk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class CassandraProperties {

    @Value("${cassandra.contactpoints}")
    private String contactPoints;
    @Value("${cassandra.port}")
    private int port;
    @Value("${cassandra.keyspace}")
    private String keyspace;

    public String getContactPoints() {
        return contactPoints;
    }

    public int getPort() {
        return port;
    }

    public String getKeyspace() {
        return keyspace;
    }
}
