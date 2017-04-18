/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.info;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.me.eddies.lib.neuron.utility.RegexMatcher;

public class NeuronInfoTest {

	private static final Logger LOG = LoggerFactory.getLogger(NeuronInfoTest.class);

	private NeuronInfo systemUnderTest;

	@Before
	public void setUp() throws IOException {
		systemUnderTest = new NeuronInfo();
	}

	@Test
	public void shouldReadVersionNumber() {
		String result = systemUnderTest.getVersionNumber();
		assertThat(result, notNullValue());
		assertThat(result, anyOf(new RegexMatcher("^\\d+\\.\\d+\\.\\d+$"),
				new RegexMatcher("^0-iss\\d+\\.\\d+$"),
				new RegexMatcher("^\\d+\\.\\d+-dev\\.\\d+$")));
		LOG.info("Version number is {}.", result);
	}
}
