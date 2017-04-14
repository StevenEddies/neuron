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
	@Parameters(source=Polarisation.class)
	public void shouldUseMinimumActivatonBelowZeroInput(Polarisation eachPolarisation) {
		systemUnderTest = new ThresholdActivation(eachPolarisation);
		assertThat(systemUnderTest.applyAsDouble(-0.01), equalTo(eachPolarisation.getMinimum()));
		assertThat(systemUnderTest.applyAsDouble(-1d), equalTo(eachPolarisation.getMinimum()));
		assertThat(systemUnderTest.applyAsDouble(-100d), equalTo(eachPolarisation.getMinimum()));
	}
	
	@Test
	@Parameters(source=Polarisation.class)
	public void shouldUseMinimumActivatonAtZeroInput(Polarisation eachPolarisation) {
		systemUnderTest = new ThresholdActivation(eachPolarisation);
		assertThat(systemUnderTest.applyAsDouble(0d), equalTo(eachPolarisation.getMinimum()));
	}
	
	@Test
	@Parameters(source=Polarisation.class)
	public void shouldUseMaximumActivatonAboveZeroInput(Polarisation eachPolarisation) {
		systemUnderTest = new ThresholdActivation(eachPolarisation);
		assertThat(systemUnderTest.applyAsDouble(0.01), equalTo(eachPolarisation.getMaximum()));
		assertThat(systemUnderTest.applyAsDouble(1d), equalTo(eachPolarisation.getMaximum()));
		assertThat(systemUnderTest.applyAsDouble(100d), equalTo(eachPolarisation.getMaximum()));
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithoutPolarisation() {
		new ThresholdActivation(null);
	}
}
