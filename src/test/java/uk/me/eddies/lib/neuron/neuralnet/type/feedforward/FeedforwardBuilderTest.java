/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import uk.me.eddies.lib.neuron.neuralnet.ActivationFunction;

public class FeedforwardBuilderTest {

	@Mock private ActivationFunction activation;
	@Mock private ActivationFunction otherActivation;

	private FeedforwardBuilder systemUnderTest;
	private FeedforwardConfiguration result;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new FeedforwardBuilder();
	}

	@Test
	public void shouldReturnSameBuilderFromEachCall() {
		assertThat(systemUnderTest.setInput(3), sameInstance(systemUnderTest));
		assertThat(systemUnderTest.usingActivationFunction(activation), sameInstance(systemUnderTest));
		assertThat(systemUnderTest.addHidden(3), sameInstance(systemUnderTest));
		assertThat(systemUnderTest.setOutput(3), sameInstance(systemUnderTest));
	}

	@Test
	public void shouldSuccessfullyCreateNetworkWithNoHiddenLayers() {
		result = systemUnderTest
				.setInput(2)
				.usingActivationFunction(activation).setOutput(4)
				.build();

		assertThat(result.getLayers(), contains(Arrays.asList(
				instanceOf(InputLayer.class), instanceOf(OutputLayer.class))));

		List<LayerConfiguration<?>> layers = new ArrayList<>(result.getLayers());
		Layer inputs = makeTestLayer(layers.get(0), 3, null); // Remember bias neuron
		Layer outputs = makeTestLayer(layers.get(1), 4, inputs);

		verifyActivationFunction(outputs, activation);
	}

	@Test
	public void shouldSuccessfullyCreateNetworkWithSingleHiddenLayer() {
		result = systemUnderTest
				.setInput(2)
				.usingActivationFunction(activation)
				.addHidden(5)
				.setOutput(4)
				.build();

		assertThat(result.getLayers(), contains(Arrays.asList(
				instanceOf(InputLayer.class),
				instanceOf(HiddenLayer.class),
				instanceOf(OutputLayer.class))));

		List<LayerConfiguration<?>> layers = new ArrayList<>(result.getLayers());
		Layer inputs = makeTestLayer(layers.get(0), 3, null); // Remember bias neuron
		Layer hidden = makeTestLayer(layers.get(1), 6, inputs);
		Layer outputs = makeTestLayer(layers.get(2), 4, inputs);

		verifyActivationFunction(hidden, activation);
		verifyActivationFunction(outputs, activation);
	}

	@Test
	public void shouldSuccessfullyCreateNetworkWithMultipleHiddenLayers() {
		result = systemUnderTest
				.setInput(2)
				.usingActivationFunction(activation)
				.addHidden(5)
				.addHidden(7)
				.usingActivationFunction(otherActivation)
				.addHidden(9)
				.setOutput(4)
				.build();

		assertThat(result.getLayers(), contains(Arrays.asList(
				instanceOf(InputLayer.class),
				instanceOf(HiddenLayer.class),
				instanceOf(HiddenLayer.class),
				instanceOf(HiddenLayer.class),
				instanceOf(OutputLayer.class))));

		List<LayerConfiguration<?>> layers = new ArrayList<>(result.getLayers());
		Layer inputs = makeTestLayer(layers.get(0), 3, null); // Remember bias neuron
		Layer hidden1 = makeTestLayer(layers.get(1), 6, inputs);
		Layer hidden2 = makeTestLayer(layers.get(2), 8, inputs);
		Layer hidden3 = makeTestLayer(layers.get(3), 10, inputs);
		Layer outputs = makeTestLayer(layers.get(4), 4, inputs);

		verifyActivationFunction(hidden1, activation);
		verifyActivationFunction(hidden2, activation);
		verifyActivationFunction(hidden3, otherActivation);
		verifyActivationFunction(outputs, otherActivation);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldFailToCreateNetworkWithoutInputLayer() {
		systemUnderTest
				.usingActivationFunction(activation).setOutput(4)
				.build();
	}

	@Test(expected = IllegalStateException.class)
	public void shouldFailToCreateNetworkWithoutOutputLayer() {
		systemUnderTest
				.setInput(4)
				.build();
	}

	@Test(expected = IllegalStateException.class)
	public void shouldFailToAddHiddenLayerWithoutActivationFunction() {
		systemUnderTest.addHidden(3);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldFailToSetOutputLayerWithoutActivationFunction() {
		systemUnderTest.setOutput(3);
	}

	private Layer makeTestLayer(LayerConfiguration<?> config, int expectedSize, Layer inputLayer) {
		Layer layer = config.buildLayer(Optional.ofNullable(inputLayer));
		assertThat(layer.getNeurons(), hasSize(expectedSize));
		return layer;
	}

	private void verifyActivationFunction(Layer layer, ActivationFunction mockActivationFunction) {
		reset(mockActivationFunction);
		layer.getNeurons().iterator().next().getValue();
		verify(mockActivationFunction).applyAsDouble(Mockito.anyDouble());
	}
}
