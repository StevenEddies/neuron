/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.activation;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class LinearActivationTest {

	private LinearActivation systemUnderTest;
	
	@Before
	public void setUp() {
		systemUnderTest = new LinearActivation();
	}
	
	public Object[] parametersForShouldReturnInput() {
		return new Object[] { -100d, -1d, -0.01, 0d, 0.01, 1d, 100d };
	}
	
	@Test
	@Parameters
	public void shouldReturnInput(double input) {
		assertThat(systemUnderTest.applyAsDouble(input), equalTo(input));
	}
}
