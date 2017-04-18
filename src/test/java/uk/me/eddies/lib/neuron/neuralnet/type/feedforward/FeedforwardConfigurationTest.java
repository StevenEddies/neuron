/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.me.eddies.lib.neuron.neuralnet.Connection;
import uk.me.eddies.lib.neuron.neuralnet.InterfaceNeurons;
import uk.me.eddies.lib.neuron.neuralnet.MutableInterfaceNeurons;
import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;

public class FeedforwardConfigurationTest {

	@Mock private LayerConfiguration<?> layer1Config;
	@Mock private LayerConfiguration<?> layer2Config;
	@Mock private LayerConfiguration<?> layer3Config;
	@Mock private Layer layer1;
	@Mock private Layer layer2;
	@Mock private Layer layer3;
	@Mock private MutableInterfaceNeurons inputInterface;
	@Mock private InterfaceNeurons outputInterface;
	@Mock private Connection connection1a;
	@Mock private Connection connection1b;
	@Mock private Connection connection3a;

	private FeedforwardConfiguration systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		when(layer1Config.buildLayer(Optional.empty())).thenReturn(layer1);
		when(layer2Config.buildLayer(Optional.of(layer1))).thenReturn(layer2);
		when(layer3Config.buildLayer(Optional.of(layer2))).thenReturn(layer3);
		when(layer1.getInputInterface()).thenReturn(Optional.of(inputInterface));
		when(layer3.getOutputInterface()).thenReturn(Optional.of(outputInterface));
		when(layer1.getConnections()).thenReturn(Arrays.asList(connection1a, connection1b));
		when(layer2.getConnections()).thenReturn(emptyList());
		when(layer3.getConnections()).thenReturn(Arrays.asList(connection3a));

		systemUnderTest = new FeedforwardConfiguration(Arrays.asList(
				layer1Config, layer2Config, layer3Config));
	}

	@Test
	public void shouldKeepThisConfigurationAsNetworksConfiguration() {
		NeuralNetwork result = systemUnderTest.create();
		assertThat(result.getConfiguration(), equalTo(systemUnderTest));
	}

	@Test
	public void shouldKeepInputInterfaceFromFirstLayer() {
		NeuralNetwork result = systemUnderTest.create();
		assertThat(result.getInputs(), sameInstance(inputInterface));
	}

	@Test
	public void shouldKeepOutputInterfaceFromLastLayer() {
		NeuralNetwork result = systemUnderTest.create();
		assertThat(result.getOutputs(), sameInstance(outputInterface));
	}

	@Test
	public void shouldCombineConnectionsFromAllLayers() {
		NeuralNetwork result = systemUnderTest.create();
		result.getAllConnections().setWeights(Arrays.asList(1d, 2d, 3d));
		verify(connection1a).setWeight(1d);
		verify(connection1b).setWeight(2d);
		verify(connection3a).setWeight(3d);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutLayerConfiguration() {
		new FeedforwardConfiguration(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullLayerConfiguration() {
		new FeedforwardConfiguration(Arrays.asList(
				layer1Config, layer2Config, null));
	}

	@Test
	public void shouldMakeLayerConfigurationsAccessible() {
		assertThat(systemUnderTest.getLayers(), contains(Arrays.asList(
				equalTo(layer1Config), equalTo(layer2Config), equalTo(layer3Config))));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldMakeLayerConfigurationsUnmodifiable() {
		systemUnderTest.getLayers().clear();
	}

	@Test
	public void shouldInsulateLayerConfigurationsFromInputCollectionChanges() {
		List<LayerConfiguration<?>> original = new ArrayList<>(Arrays.asList(
				layer1Config, layer2Config, layer3Config));
		systemUnderTest = new FeedforwardConfiguration(original);

		original.clear();

		assertThat(systemUnderTest.getLayers(), contains(Arrays.asList(
				equalTo(layer1Config), equalTo(layer2Config), equalTo(layer3Config))));
	}
}
