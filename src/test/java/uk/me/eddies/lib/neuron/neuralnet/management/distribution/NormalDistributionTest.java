/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management.distribution;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.Collection;
import java.util.Random;
import java.util.stream.DoubleStream;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class NormalDistributionTest {

	private static final double ALLOWED_ERROR = 1e-8;
	private static final double MEAN = 100d;
	private static final double VARIANCE = 10d;
	private static final double STD_DEV = Math.sqrt(VARIANCE);

	@Mock private Random random;

	private NormalDistribution systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new NormalDistribution(random, MEAN, VARIANCE);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutRandom() {
		new NormalDistribution(null, MEAN, VARIANCE);
	}

	@Test
	public void shouldReturnLowValueOnEquivalentGaussianRandom() {
		when(random.nextGaussian()).thenReturn(-1d);
		assertThat(systemUnderTest.getAsDouble(), closeTo(MEAN - STD_DEV, ALLOWED_ERROR));
	}

	@Test
	public void shouldReturnHighValueOnEquivalentGaussianRandom() {
		when(random.nextGaussian()).thenReturn(1d);
		assertThat(systemUnderTest.getAsDouble(), closeTo(MEAN + STD_DEV, ALLOWED_ERROR));
	}

	@Test
	public void shouldCallToRandomIndependentlyOnEachCall() {
		when(random.nextGaussian()).thenReturn(-1d).thenReturn(1d);
		assertThat(systemUnderTest.getAsDouble(), closeTo(MEAN - STD_DEV, ALLOWED_ERROR));
		assertThat(systemUnderTest.getAsDouble(), closeTo(MEAN + STD_DEV, ALLOWED_ERROR));
	}

	@Test
	public void shouldReturnMidValueOnEquivalentGaussianRandom() {
		when(random.nextGaussian()).thenReturn(0d);
		assertThat(systemUnderTest.getAsDouble(), closeTo(MEAN, ALLOWED_ERROR));
	}

	@Test
	public void shouldHaveCorrectMeanUsingRealRandom() {
		systemUnderTest = new NormalDistribution(MEAN, VARIANCE);
		final int count = 100000;
		double actualMean = DoubleStream.generate(systemUnderTest)
				.limit(count)
				.average()
				.getAsDouble();
		assertThat(actualMean, closeTo(MEAN, 0.2));
	}

	@Test
	public void shouldHaveCorrectVarianceUsingRealRandom() {
		systemUnderTest = new NormalDistribution(MEAN, VARIANCE);
		final int count = 100000;
		Collection<Double> sample = DoubleStream.generate(systemUnderTest)
				.limit(count)
				.boxed()
				.collect(toSet());
		double actualMean = sample.stream()
				.mapToDouble(Double::valueOf)
				.average()
				.getAsDouble();
		double actualVariance = sample.stream()
				.mapToDouble(Double::valueOf)
				.map(x -> x - actualMean)
				.map(x -> x * x)
				.sum()
				/ (count - 1);
		assertThat(actualVariance, closeTo(VARIANCE, 0.2));
	}
}
