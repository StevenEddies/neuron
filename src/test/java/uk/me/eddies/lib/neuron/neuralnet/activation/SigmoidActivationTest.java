/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.activation;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class SigmoidActivationTest {

	private double ALLOWABLE_ERROR = 1e-10;

	private SigmoidActivation systemUnderTest;

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldHaveActivatonBelowMedianWhenInputBelowZero(Polarisation eachPolarisation) {
		systemUnderTest = new SigmoidActivation(eachPolarisation);
		assertThat(systemUnderTest.applyAsDouble(-0.01), lessThan(getMedian(eachPolarisation)));
		assertThat(systemUnderTest.applyAsDouble(-1d), lessThan(systemUnderTest.applyAsDouble(-0.01)));
		assertThat(systemUnderTest.applyAsDouble(-100d), lessThan(systemUnderTest.applyAsDouble(-1d)));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldHaveMedianActivatonAtZeroInput(Polarisation eachPolarisation) {
		systemUnderTest = new SigmoidActivation(eachPolarisation);
		assertThat(systemUnderTest.applyAsDouble(0d),
				closeTo(getMedian(eachPolarisation), ALLOWABLE_ERROR));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldHaveActivatonAboveMedianWhenInputAboveZero(Polarisation eachPolarisation) {
		systemUnderTest = new SigmoidActivation(eachPolarisation);
		assertThat(systemUnderTest.applyAsDouble(0.01), greaterThan(getMedian(eachPolarisation)));
		assertThat(systemUnderTest.applyAsDouble(1d), greaterThan(systemUnderTest.applyAsDouble(0.01)));
		assertThat(systemUnderTest.applyAsDouble(100d), greaterThan(systemUnderTest.applyAsDouble(1d)));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldApproachMaximumForHighInputs(Polarisation eachPolarisation) {
		systemUnderTest = new SigmoidActivation(eachPolarisation);
		assertThat(systemUnderTest.applyAsDouble(1d),
				closeTo(eachPolarisation.getMaximum(), 0.27 * eachPolarisation.getRange()));
		assertThat(systemUnderTest.applyAsDouble(10d),
				closeTo(eachPolarisation.getMaximum(), 4.6e-5 * eachPolarisation.getRange()));
		assertThat(systemUnderTest.applyAsDouble(100d),
				closeTo(eachPolarisation.getMaximum(), 3.8e-44 * eachPolarisation.getRange()));
		assertThat(systemUnderTest.applyAsDouble(1000d), closeTo(eachPolarisation.getMaximum(), Double.MIN_VALUE));
		assertThat(systemUnderTest.applyAsDouble(10000d), closeTo(eachPolarisation.getMaximum(), Double.MIN_VALUE));
		assertThat(systemUnderTest.applyAsDouble(100000d), closeTo(eachPolarisation.getMaximum(), Double.MIN_VALUE));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldApproachMinimumForLowInputs(Polarisation eachPolarisation) {
		systemUnderTest = new SigmoidActivation(eachPolarisation);
		assertThat(systemUnderTest.applyAsDouble(-1d),
				closeTo(eachPolarisation.getMinimum(), 0.27 * eachPolarisation.getRange()));
		assertThat(systemUnderTest.applyAsDouble(-10d),
				closeTo(eachPolarisation.getMinimum(), 4.6e-5 * eachPolarisation.getRange()));
		assertThat(systemUnderTest.applyAsDouble(-100d),
				closeTo(eachPolarisation.getMinimum(), 3.8e-44 * eachPolarisation.getRange()));
		assertThat(systemUnderTest.applyAsDouble(-1000d), closeTo(eachPolarisation.getMinimum(), Double.MIN_VALUE));
		assertThat(systemUnderTest.applyAsDouble(-10000d), closeTo(eachPolarisation.getMinimum(), Double.MIN_VALUE));
		assertThat(systemUnderTest.applyAsDouble(-100000d), closeTo(eachPolarisation.getMinimum(), Double.MIN_VALUE));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldPublishPolarisation(Polarisation eachPolarisation) {
		systemUnderTest = new SigmoidActivation(eachPolarisation);
		assertThat(systemUnderTest.getPolarisation(), equalTo(eachPolarisation));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutPolarisation() {
		new ThresholdActivation(null);
	}

	private double getMedian(Polarisation polarisation) {
		return polarisation.getMinimum() + (polarisation.getRange() / 2);
	}
}
