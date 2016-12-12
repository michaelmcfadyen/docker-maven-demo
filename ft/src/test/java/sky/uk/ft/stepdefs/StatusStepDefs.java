package sky.uk.ft.stepdefs;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.java.guice.ScenarioScoped;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import sky.uk.ft.Person;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

@ScenarioScoped
public class StatusStepDefs {

    private final String url;
    private final Session session;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private HttpResponse response;
    private Person expectedPerson = new Person();

    @Inject
    public StatusStepDefs(@Named("app.hostname") String hostname,
                          @Named("app.port") String port,
                          Session session,
                          HttpClient httpClient,
                          ObjectMapper objectMapper) {
        this.session = session;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.url = format("%s:%s", hostname, port);
    }

    @Given("^there is an existing person$")
    public void there_is_an_existing_person() throws Throwable {
        expectedPerson = createDefaultPerson();
        session.execute(format("insert into person(id, firstname, surname, age) values(%s, '%s', '%s', %s)",
                expectedPerson.getId(),
                expectedPerson.getFirstname(),
                expectedPerson.getSurname(),
                expectedPerson.getAge()));
    }

    @Given("^I have a person$")
    public void i_have_a_person() throws Throwable {
        expectedPerson = createDefaultPerson();
    }

    @When("^I call status endpoint$")
    public void i_call_status_endpoint() throws Throwable {
        HttpGet httpGet = new HttpGet(url);

        response = httpClient.execute(httpGet);
    }

    @When("^I insert the person$")
    public void i_insert_the_person() throws Throwable {
        HttpPost httpPost = new HttpPost(format("%s/person", url));
        httpPost.setEntity(new ByteArrayEntity(objectMapper.writeValueAsBytes(expectedPerson), ContentType.APPLICATION_JSON));
        response = httpClient.execute(httpPost);
    }

    @When("^I get the person$")
    public void i_get_the_person() throws Throwable {
        HttpGet httpGet = new HttpGet(format("%s/person/%s", url, expectedPerson.getId()));

        response = httpClient.execute(httpGet);
    }

    @Then("^response code is (\\d+)$")
    public void response_code_is(int responseCode) throws Throwable {
        assertEquals(responseCode, response.getStatusLine().getStatusCode());
    }

    @Then("^content is \"([^\"]*)\"$")
    public void content_is(String arg1) throws Throwable {
        assertEquals(arg1, EntityUtils.toString(response.getEntity()));
    }

    @Then("^the person exists in the database$")
    public void the_person_exists_in_the_database() throws Throwable {
        ResultSet resultSet = session.execute(format("select count(*) as count from person where id = %s", expectedPerson.getId()));
        long count = resultSet.one().getLong("count");

        assertEquals(1, count);
    }

    @Then("^the person details are correct$")
    public void the_person_details_are_correct() throws Throwable {
        Person actualPerson = objectMapper.readValue(response.getEntity().getContent(), Person.class);

        assertEquals(expectedPerson, actualPerson);
    }

    private Person createDefaultPerson(){
        return new Person(1, "michael", "mcfadyen", 24);
    }
}
