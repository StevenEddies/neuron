/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management.distribution;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class FixedDistributionTest {

	private static final double VALUE = 589d;

	private FixedDistribution systemUnderTest;

	@Before
	public void setUp() {
		systemUnderTest = new FixedDistribution(VALUE);
	}

	@Test
	public void shouldReturnFixedValue() {
		assertThat(systemUnderTest.getAsDouble(), equalTo(VALUE));
	}

	@Test
	public void shouldReturnFixedValueRepeatedly() {
		assertThat(systemUnderTest.getAsDouble(), equalTo(VALUE));
		assertThat(systemUnderTest.getAsDouble(), equalTo(VALUE));
		assertThat(systemUnderTest.getAsDouble(), equalTo(VALUE));
	}
}
