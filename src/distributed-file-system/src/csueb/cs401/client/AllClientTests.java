package csueb.cs401.client;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({
	ClientLoginBodyTest.class, ClientMessageTypeTest.class
})
public class AllClientTests {
}
