/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class InputNeuronTest {
	
	private static final double SET_VALUE = 42d;

	private InputNeuron systemUnderTest;
	
	@Before
	public void setUp() {
		systemUnderTest = new InputNeuron();
	}
	
	@Test
	public void shouldInitiallyHaveZeroValue() {
		assertThat(systemUnderTest.getValue(), equalTo(0d));
	}
	
	@Test
	public void shouldRetainSetValue() {
		systemUnderTest.setValue(SET_VALUE);
		assertThat(systemUnderTest.getValue(), equalTo(SET_VALUE));
	}
	
	@Test
	public void shouldHaveNoConnections() {
		assertThat(systemUnderTest.getConnections(), emptyIterable());
	}
}
