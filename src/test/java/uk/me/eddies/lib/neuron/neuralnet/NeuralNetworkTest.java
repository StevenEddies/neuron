/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class NeuralNetworkTest {

	@Mock private NetworkConfiguration configuration;
	@Mock private MutableInterfaceNeurons inputs;
	@Mock private InterfaceNeurons outputs;
	@Mock private Connections allConnections;

	private NeuralNetwork systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new NeuralNetwork(configuration, inputs, outputs, allConnections);
	}

	@Test
	public void shouldMakeConfigurationAccessible() {
		assertThat(systemUnderTest.getConfiguration(), equalTo(configuration));
	}

	@Test
	public void shouldMakeInputsAccessible() {
		assertThat(systemUnderTest.getInputs(), equalTo(inputs));
	}

	@Test
	public void shouldMakeOutputsAccessible() {
		assertThat(systemUnderTest.getOutputs(), equalTo(outputs));
	}

	@Test
	public void shouldMakeConnectionsAccessible() {
		assertThat(systemUnderTest.getAllConnections(), equalTo(allConnections));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullConfiguration() {
		new NeuralNetwork(null, inputs, outputs, allConnections);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullInputs() {
		new NeuralNetwork(configuration, null, outputs, allConnections);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullOutputs() {
		new NeuralNetwork(configuration, inputs, null, allConnections);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullConnections() {
		new NeuralNetwork(configuration, inputs, outputs, null);
	}
}
