package sky.uk.data;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cassandra.core.ConsistencyLevel;
import org.springframework.cassandra.core.RetryPolicy;
import org.springframework.cassandra.core.RowMapper;
import org.springframework.cassandra.core.WriteOptions;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

@Repository
public class PersonDao {

    private static final String COLUMN_FAMILY = "person";
    private static final WriteOptions WRITE_OPTIONS = new WriteOptions(ConsistencyLevel.LOCAL_QUOROM, RetryPolicy.DEFAULT);

    private final CassandraOperations cassandraOperations;
    private final RowMapper<Person> rowMapper;

    @Inject
    public PersonDao(CassandraOperations cassandraOperations,
                     @Qualifier("personRowMapper") PersonRowMapper rowMapper) {
        this.cassandraOperations = cassandraOperations;
        this.rowMapper = rowMapper;
    }

    public void insert(Person person){
        cassandraOperations.insert(person, WRITE_OPTIONS);
    }

    public Person get(int id){
        Select query = QueryBuilder
                .select()
                .from(COLUMN_FAMILY);
        query.where(QueryBuilder.eq("id", id));

        try {
            return cassandraOperations.queryForObject(query, rowMapper);
        } catch (IllegalArgumentException e){
            throw new NotFoundException();
        }
    }
}
