/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class NeuralNetworkTest {

	@Mock private MutableInterfaceNeurons inputs;
	@Mock private InterfaceNeurons outputs;
	
	private NeuralNetwork systemUnderTest;
	
	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new NeuralNetwork(inputs, outputs);
	}
	
	@Test
	public void shouldMakeInputsAccessible() {
		assertThat(systemUnderTest.getInputs(), equalTo(inputs));
	}
	
	@Test
	public void shouldMakeOutputsAccessible() {
		assertThat(systemUnderTest.getOutputs(), equalTo(outputs));
	}

	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullInputs() {
		new NeuralNetwork(null, outputs);
	}
	
	@Test(expected=NullPointerException.class)
	public void shouldFailToConstructWithNullOutputs() {
		new NeuralNetwork(inputs, null);
	}
}
