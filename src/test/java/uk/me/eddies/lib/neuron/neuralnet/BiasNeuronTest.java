/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class BiasNeuronTest {

	private BiasNeuron systemUnderTest;
	
	@Before
	public void setUp() {
		systemUnderTest = new BiasNeuron();
	}
	
	@Test
	public void shouldHaveUnityValue() {
		assertThat(systemUnderTest.getValue(), equalTo(1d));
	}
	
	@Test
	public void shouldHaveNoConnections() {
		assertThat(systemUnderTest.getConnections(), emptyIterable());
	}
}
