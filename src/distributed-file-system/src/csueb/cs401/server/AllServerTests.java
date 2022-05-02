package csueb.cs401.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({
	ServiceGetLogsTest.class, 
	ServiceLoginTest.class, 
	ServicePostFileRequestTest.class, 
	ServicePostFileResponseTest.class,
	ServiceReadFileRequestTest.class,
	ServiceReadFileResponseTest.class,
	ServiceSearchTest.class
})
public class AllServerTests {
}