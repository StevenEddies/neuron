/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.activation;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ThresholdActivationTest {

	private ThresholdActivation systemUnderTest;

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldUseMinimumActivatonBelowZeroInput(Polarisation eachPolarisation) {
		systemUnderTest = new ThresholdActivation(eachPolarisation);
		assertThat(systemUnderTest.applyFunction(-0.01), equalTo(eachPolarisation.getMinimum()));
		assertThat(systemUnderTest.applyFunction(-1d), equalTo(eachPolarisation.getMinimum()));
		assertThat(systemUnderTest.applyFunction(-100d), equalTo(eachPolarisation.getMinimum()));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldUseMinimumActivatonAtZeroInput(Polarisation eachPolarisation) {
		systemUnderTest = new ThresholdActivation(eachPolarisation);
		assertThat(systemUnderTest.applyFunction(0d), equalTo(eachPolarisation.getMinimum()));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldUseMaximumActivatonAboveZeroInput(Polarisation eachPolarisation) {
		systemUnderTest = new ThresholdActivation(eachPolarisation);
		assertThat(systemUnderTest.applyFunction(0.01), equalTo(eachPolarisation.getMaximum()));
		assertThat(systemUnderTest.applyFunction(1d), equalTo(eachPolarisation.getMaximum()));
		assertThat(systemUnderTest.applyFunction(100d), equalTo(eachPolarisation.getMaximum()));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldPublishPolarisation(Polarisation eachPolarisation) {
		systemUnderTest = new ThresholdActivation(eachPolarisation);
		assertThat(systemUnderTest.getPolarisation(), equalTo(eachPolarisation));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutPolarisation() {
		new ThresholdActivation(null);
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldNotHaveGradient(Polarisation eachPolarisation) {
		systemUnderTest = new ThresholdActivation(eachPolarisation);
		assertThat(systemUnderTest.hasGradient(), equalTo(false));
	}

	@Test(expected = UnsupportedOperationException.class)
	@Parameters(source = Polarisation.class)
	public void shouldFailToApplyGradient(Polarisation eachPolarisation) {
		systemUnderTest = new ThresholdActivation(eachPolarisation);
		systemUnderTest.applyGradient(1d);
	}
}
