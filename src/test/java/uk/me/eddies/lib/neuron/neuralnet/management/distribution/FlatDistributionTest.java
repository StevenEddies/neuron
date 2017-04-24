/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management.distribution;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class FlatDistributionTest {

	private static final double ALLOWED_ERROR = 1e-8;
	private static final double MINIMUM = 50d;
	private static final double MAXIMUM = 100d;

	@Mock private Random random;

	private FlatDistribution systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new FlatDistribution(random, MINIMUM, MAXIMUM);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutRandom() {
		new FlatDistribution(null, MINIMUM, MAXIMUM);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailToConstructSwappingMinimumAndMaximum() {
		new FlatDistribution(random, MAXIMUM, MINIMUM);
	}

	@Test
	public void shouldReturnMinimumOnLowestPossibleRandom() {
		when(random.nextDouble()).thenReturn(0d);
		assertThat(systemUnderTest.getAsDouble(), closeTo(MINIMUM, ALLOWED_ERROR));
	}

	@Test
	public void shouldReturnMaximumOnHighestPossibleRandom() {
		when(random.nextDouble()).thenReturn(1d);
		assertThat(systemUnderTest.getAsDouble(), closeTo(MAXIMUM, ALLOWED_ERROR));
	}

	@Test
	public void shouldCallToRandomIndependentlyOnEachCall() {
		when(random.nextDouble()).thenReturn(0d).thenReturn(1d);
		assertThat(systemUnderTest.getAsDouble(), closeTo(MINIMUM, ALLOWED_ERROR));
		assertThat(systemUnderTest.getAsDouble(), closeTo(MAXIMUM, ALLOWED_ERROR));
	}

	@Test
	public void shouldReturnCorrespondinglyScaledRandomAtMidpoint() {
		final double range = MAXIMUM - MINIMUM;
		for (double proportion = 0.1; proportion <= 0.9; proportion += 0.1) {
			when(random.nextDouble()).thenReturn(proportion);
			assertThat(systemUnderTest.getAsDouble(), closeTo(MINIMUM + proportion * range, ALLOWED_ERROR));
		}
	}

	@Test
	public void shouldStayWithinRequiredRangeUsingRealRandom() {
		systemUnderTest = new FlatDistribution(new Random(), MINIMUM, MAXIMUM);
		for (int i = 0; i < 10000; i++) {
			double result = systemUnderTest.getAsDouble();
			assertThat(result, greaterThanOrEqualTo(MINIMUM));
			assertThat(result, lessThan(MAXIMUM));
		}
	}
}
