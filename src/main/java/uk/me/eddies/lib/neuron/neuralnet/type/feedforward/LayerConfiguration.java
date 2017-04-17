/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Stream.concat;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import uk.me.eddies.lib.neuron.neuralnet.ActivationFunction;
import uk.me.eddies.lib.neuron.neuralnet.BiasNeuron;
import uk.me.eddies.lib.neuron.neuralnet.Connection;
import uk.me.eddies.lib.neuron.neuralnet.InterfaceNeurons;
import uk.me.eddies.lib.neuron.neuralnet.MutableInterfaceNeurons;
import uk.me.eddies.lib.neuron.neuralnet.Neuron;

/**
 * Represents the configuration of a layer in a feedforward network.
 */
public abstract class LayerConfiguration<N extends Neuron> {

	private final int size;
	private final boolean bias;
	private final Function<Collection<Neuron>, N> neuronFactory;
	
	protected LayerConfiguration(int size, boolean bias, Function<Collection<Neuron>, N> neuronFactory) {
		this.size = size;
		this.bias = bias;
		this.neuronFactory = neuronFactory;
	}

	public Layer buildLayer(Optional<Layer> previousLayer) {
		
		Collection<Neuron> previousLayerNeurons = previousLayer
				.map(Layer::getNeurons)
				.orElse(Collections.emptySet());
		
		Collection<N> normalNeurons = createNormalNeurons(previousLayerNeurons);
		Collection<Neuron> neurons = concat(normalNeurons.stream(), createBiasNeuron())
				.collect(toCollection(LinkedHashSet::new));
		
		return new Layer(
				neurons,
				findConnections(normalNeurons),
				createOutputInterface(normalNeurons),
				createInputInterface(normalNeurons));
	}

	private Collection<N> createNormalNeurons(Collection<Neuron> previousLayerNeurons) {
		Collection<N> normalNeurons = new LinkedHashSet<>();
		for (int i = 0; i < size; i++) {
			normalNeurons.add(neuronFactory.apply(previousLayerNeurons));
		}
		return normalNeurons;
	}

	private Stream<Neuron> createBiasNeuron() {
		Stream<Neuron> biasNeuron = bias ? Stream.of(new BiasNeuron()) : Stream.empty();
		return biasNeuron;
	}
	
	private Collection<Connection> findConnections(Collection<N> neurons) {
		return neurons.stream()
				.flatMap(n -> n.getConnections().stream())
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	protected Optional<InterfaceNeurons> createOutputInterface(Collection<N> neurons) {
		return Optional.empty();
	}
	
	protected Optional<MutableInterfaceNeurons> createInputInterface(Collection<N> neurons) {
		return Optional.empty();
	}
	
	protected static <N extends Neuron>
			Function<Collection<Neuron>, N> givenActivationFunction(
					BiFunction<Collection<Neuron>, ActivationFunction, N> innerFactory,
					ActivationFunction activationFunction
	) {
		return neurons -> innerFactory.apply(neurons, activationFunction);
	}
}
