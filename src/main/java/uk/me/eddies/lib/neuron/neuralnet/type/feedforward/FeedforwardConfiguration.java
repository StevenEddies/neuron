/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import static java.util.Collections.unmodifiableCollection;
import static java.util.stream.Collectors.toCollection;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

import uk.me.eddies.lib.neuron.neuralnet.Connections;
import uk.me.eddies.lib.neuron.neuralnet.NetworkConfiguration;
import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;

/**
 * {@link NetworkConfiguration} for a feedforward type neural network.
 */
public class FeedforwardConfiguration implements NetworkConfiguration {

	private final Collection<LayerConfiguration<?>> layerConfigs;
	
	public FeedforwardConfiguration(Collection<LayerConfiguration<?>> layerConfigs) {
		this.layerConfigs = unmodifiableCollection(new LinkedHashSet<>(layerConfigs));
		this.layerConfigs.forEach(Objects::requireNonNull);
	}

	@Override
	public NeuralNetwork create() {
		Deque<Layer> layers = buildLayersFromConfig();
		return new NeuralNetwork(this,
				layers.getFirst().getInputInterface().get(),
				layers.getLast().getOutputInterface().get(),
				combineConnections(layers));
	}

	private Connections combineConnections(Deque<Layer> layers) {
		return new Connections(layers.stream()
				.flatMap(l -> l.getConnections().stream())
				.collect(toCollection(LinkedHashSet::new)));
	}

	private Deque<Layer> buildLayersFromConfig() {
		Deque<Layer> layers = new LinkedList<>();
		Optional<Layer> previous = Optional.empty();
		for (LayerConfiguration<?> eachLayerConfig : layerConfigs) {
			Layer current = eachLayerConfig.buildLayer(previous);
			layers.addLast(current);
			previous = Optional.of(current);
		}
		return layers;
	}
}
