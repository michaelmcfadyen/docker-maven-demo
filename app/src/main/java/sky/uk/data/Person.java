package sky.uk.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public class Person {

    @PrimaryKey
    private int id;
    private String firstname;
    private String surname;
    private int age;

    @JsonCreator
    public Person(@JsonProperty("id") int id,
                  @JsonProperty("firstname") String firstname,
                  @JsonProperty("surname") String surname,
                  @JsonProperty("age") int age) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }
}
