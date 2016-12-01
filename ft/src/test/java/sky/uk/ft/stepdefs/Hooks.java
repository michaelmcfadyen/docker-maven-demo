package sky.uk.ft.stepdefs;

import com.datastax.driver.core.Session;
import cucumber.api.java.Before;

import javax.inject.Inject;

public class Hooks {

    private final Session session;

    @Inject
    public Hooks(Session session) {
        this.session = session;
    }

    @Before
    public void before(){
        session.execute("truncate person");
    }
}
