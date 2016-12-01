package sky.uk.data;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.exceptions.DriverException;
import org.springframework.cassandra.core.RowMapper;
import org.springframework.stereotype.Component;

@Component("personRowMapper")
public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(Row row, int i) throws DriverException {
        int id = row.getInt("id");
        int age = row.getInt("age");
        String firstname = row.getString("firstname");
        String surname = row.getString("surname");

        return new Person(id, firstname, surname, age);
    }
}
