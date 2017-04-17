/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Stream.concat;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

import uk.me.eddies.lib.neuron.neuralnet.ActivationFunction;
import uk.me.eddies.lib.neuron.neuralnet.management.ConfigurationBuilder;

/**
 * {@link ConfigurationBuilder} for a feedforward type neural network.
 */
public class FeedforwardBuilder implements ConfigurationBuilder {
	
	private InputLayer input = null;
	private Collection<HiddenLayer> hidden = new LinkedHashSet<>();
	private OutputLayer output = null;
	private ActivationFunction activationFunction = null;
	
	public FeedforwardBuilder setInput(int size) {
		input = new InputLayer(size);
		return this;
	}
	
	public FeedforwardBuilder addHidden(int size) {
		if (activationFunction == null)
			throw new IllegalStateException("Adding a hidden layer requires an activation function to be set first.");
		hidden.add(new HiddenLayer(size, activationFunction));
		return this;
	}
	
	public FeedforwardBuilder setOutput(int size) {
		if (activationFunction == null)
			throw new IllegalStateException("Setting the output layer requires an activation function to be set first.");
		output = new OutputLayer(size, activationFunction);
		return this;
	}
	
	public FeedforwardBuilder usingActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
		return this;
	}

	@Override
	public FeedforwardConfiguration build() {
		if (input == null || output == null) {
			throw new IllegalStateException("Input and output layers must bot be set before a network can be built.");
		}
		Collection<LayerConfiguration<?>> layers
				= concat(Stream.of(requireNonNull(input)),
				concat(hidden.stream(),
				Stream.of(requireNonNull(output))))
				.collect(toCollection(LinkedHashSet::new));
		return new FeedforwardConfiguration(layers);
	}
}
