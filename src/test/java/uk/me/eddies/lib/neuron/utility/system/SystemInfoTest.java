/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.system;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class SystemInfoTest {

	private SystemInfo systemUnderTest;

	@Before
	public void setUp() {
		systemUnderTest = new SystemInfo();
	}

	@Test
	public void shouldProvideProcessorCount() {
		assertThat(systemUnderTest.getProcessorCount(), equalTo(Runtime.getRuntime().availableProcessors()));
	}
}
