/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.me.eddies.lib.neuron.neuralnet.Connections;
import uk.me.eddies.lib.neuron.neuralnet.MutableInterfaceNeurons;
import uk.me.eddies.lib.neuron.neuralnet.NetworkConfiguration;
import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;

public class NetworkClonerTest {

	@Mock private NeuralNetwork originalNetwork;
	@Mock private NeuralNetwork newNetwork;
	@Mock private NetworkConfiguration configuration;
	@Mock private MutableInterfaceNeurons originalInputs;
	@Mock private MutableInterfaceNeurons newInputs;
	@Mock private Connections originalConnections;
	@Mock private Connections newConnections;
	@Mock private List<Double> weights;
	@Mock private List<Double> inputs;

	private NetworkCloner systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		when(originalNetwork.getConfiguration()).thenReturn(configuration);
		when(configuration.create()).thenReturn(newNetwork);
		when(originalNetwork.getInputs()).thenReturn(originalInputs);
		when(originalNetwork.getAllConnections()).thenReturn(originalConnections);
		when(newNetwork.getInputs()).thenReturn(newInputs);
		when(newNetwork.getAllConnections()).thenReturn(newConnections);
		when(originalInputs.getValues()).thenReturn(inputs);
		when(originalConnections.getWeights()).thenReturn(weights);

		systemUnderTest = new NetworkCloner();
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToCloneNullNetwork() {
		systemUnderTest.clone(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToCloneWhenConfigurationDoesNotProvideClonedStructure() {
		when(configuration.create()).thenReturn(null);
		systemUnderTest.clone(originalNetwork);
	}

	@Test
	public void shouldReturnClonedStructure() {
		assertThat(systemUnderTest.clone(originalNetwork), sameInstance(newNetwork));
	}

	@Test
	public void shouldCloneWeights() {
		systemUnderTest.clone(originalNetwork);
		verify(newConnections).setWeights(weights);
	}

	@Test
	public void shouldCloneInputValues() {
		systemUnderTest.clone(originalNetwork);
		verify(newInputs).setValues(inputs);
	}
}
