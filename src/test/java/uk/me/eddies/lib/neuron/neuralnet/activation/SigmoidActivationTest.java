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
		assertThat(systemUnderTest.applyFunction(-0.01), lessThan(getMedian(eachPolarisation)));
		assertThat(systemUnderTest.applyFunction(-1d), lessThan(systemUnderTest.applyFunction(-0.01)));
		assertThat(systemUnderTest.applyFunction(-100d), lessThan(systemUnderTest.applyFunction(-1d)));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldHaveMedianActivatonAtZeroInput(Polarisation eachPolarisation) {
		systemUnderTest = new SigmoidActivation(eachPolarisation);
		assertThat(systemUnderTest.applyFunction(0d),
				closeTo(getMedian(eachPolarisation), ALLOWABLE_ERROR));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldHaveActivatonAboveMedianWhenInputAboveZero(Polarisation eachPolarisation) {
		systemUnderTest = new SigmoidActivation(eachPolarisation);
		assertThat(systemUnderTest.applyFunction(0.01), greaterThan(getMedian(eachPolarisation)));
		assertThat(systemUnderTest.applyFunction(1d), greaterThan(systemUnderTest.applyFunction(0.01)));
		assertThat(systemUnderTest.applyFunction(100d), greaterThan(systemUnderTest.applyFunction(1d)));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldApproachMaximumForHighInputs(Polarisation eachPolarisation) {
		systemUnderTest = new SigmoidActivation(eachPolarisation);
		assertThat(systemUnderTest.applyFunction(1d),
				closeTo(eachPolarisation.getMaximum(), 0.27 * eachPolarisation.getRange()));
		assertThat(systemUnderTest.applyFunction(10d),
				closeTo(eachPolarisation.getMaximum(), 4.6e-5 * eachPolarisation.getRange()));
		assertThat(systemUnderTest.applyFunction(100d),
				closeTo(eachPolarisation.getMaximum(), 3.8e-44 * eachPolarisation.getRange()));
		assertThat(systemUnderTest.applyFunction(1000d), closeTo(eachPolarisation.getMaximum(), Double.MIN_VALUE));
		assertThat(systemUnderTest.applyFunction(10000d), closeTo(eachPolarisation.getMaximum(), Double.MIN_VALUE));
		assertThat(systemUnderTest.applyFunction(100000d), closeTo(eachPolarisation.getMaximum(), Double.MIN_VALUE));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldApproachMinimumForLowInputs(Polarisation eachPolarisation) {
		systemUnderTest = new SigmoidActivation(eachPolarisation);
		assertThat(systemUnderTest.applyFunction(-1d),
				closeTo(eachPolarisation.getMinimum(), 0.27 * eachPolarisation.getRange()));
		assertThat(systemUnderTest.applyFunction(-10d),
				closeTo(eachPolarisation.getMinimum(), 4.6e-5 * eachPolarisation.getRange()));
		assertThat(systemUnderTest.applyFunction(-100d),
				closeTo(eachPolarisation.getMinimum(), 3.8e-44 * eachPolarisation.getRange()));
		assertThat(systemUnderTest.applyFunction(-1000d), closeTo(eachPolarisation.getMinimum(), Double.MIN_VALUE));
		assertThat(systemUnderTest.applyFunction(-10000d), closeTo(eachPolarisation.getMinimum(), Double.MIN_VALUE));
		assertThat(systemUnderTest.applyFunction(-100000d), closeTo(eachPolarisation.getMinimum(), Double.MIN_VALUE));
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

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldHaveGradient(Polarisation eachPolarisation) {
		systemUnderTest = new SigmoidActivation(eachPolarisation);
		assertThat(systemUnderTest.hasGradient(), equalTo(true));
	}

	public Object[][] parametersForShouldHaveCorrectGradient() {
		return new Object[][] {
				{ Polarisation.UNIPOLAR, 2d, 2.000001d },
				{ Polarisation.UNIPOLAR, 1d, 1.000001d },
				{ Polarisation.UNIPOLAR, 0.5d, 0.500001d },
				{ Polarisation.UNIPOLAR, 0d, 0.000001d },
				{ Polarisation.UNIPOLAR, -1d, -0.999999d },
				{ Polarisation.BIPOLAR, 2d, 2.000001d },
				{ Polarisation.BIPOLAR, 1d, 1.000001d },
				{ Polarisation.BIPOLAR, 0.5d, 0.500001d },
				{ Polarisation.BIPOLAR, 0d, 0.000001d },
				{ Polarisation.BIPOLAR, -1d, -0.999999d }
		};
	}

	@Test
	@Parameters
	public void shouldHaveCorrectGradient(Polarisation eachPolarisation, double x1, double x2) {
		systemUnderTest = new SigmoidActivation(eachPolarisation);
		double y1 = systemUnderTest.applyFunction(x1);
		double y2 = systemUnderTest.applyFunction(x2);
		double expectedM = (y2 - y1) / (x2 - x1);
		double medianX = (x1 + x2) / 2;

		assertThat(systemUnderTest.applyGradient(medianX), closeTo(expectedM, 2e-10));
	}

	private double getMedian(Polarisation polarisation) {
		return polarisation.getMinimum() + (polarisation.getRange() / 2);
	}
}
