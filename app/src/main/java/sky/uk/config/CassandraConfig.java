package sky.uk.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;

@Configuration
public class CassandraConfig {

    @Bean
    public Session session(CassandraProperties cassandraProperties){
        Cluster cluster = Cluster.builder()
                .addContactPoint(cassandraProperties.getContactPoints())
                .withPort(cassandraProperties.getPort())
                .build();
        return cluster.connect(cassandraProperties.getKeyspace());
    }

    @Bean
    public CassandraOperations cassandraOperations(Session session){
        return new CassandraTemplate(session);
    }
}
