/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Stream.concat;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Stream;
import uk.me.eddies.lib.neuron.neuralnet.ActivationFunction;
import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;
import uk.me.eddies.lib.neuron.neuralnet.management.ConfigurationBuilder;

/**
 * {@link ConfigurationBuilder} for a feedforward type neural network.
 */
public class FeedforwardBuilder implements ConfigurationBuilder {

	private InputLayer input = null;
	private Collection<HiddenLayer> hidden = new LinkedHashSet<>();
	private OutputLayer output = null;

	public FeedforwardBuilder setInput(int size) {
		input = new InputLayer(size);
		return this;
	}

	public FeedforwardBuilder addHidden(int size, ActivationFunction activationFunction) {
		hidden.add(new HiddenLayer(size, requireNonNull(activationFunction)));
		return this;
	}

	public FeedforwardBuilder setOutput(int size, ActivationFunction activationFunction) {
		output = new OutputLayer(size, requireNonNull(activationFunction));
		return this;
	}

	@Override
	public FeedforwardConfiguration build() {
		if (input == null || output == null)
			throw new IllegalStateException("Input and output layers must both be set before a network can be built.");
		Collection<LayerConfiguration<?>> layers =
				concat(Stream.of(input), concat(hidden.stream(), Stream.of(output)))
						.collect(toCollection(LinkedHashSet::new));
		return new FeedforwardConfiguration(layers);
	}

	@Override
	public NeuralNetwork buildNetwork() {
		return build().create();
	}
}
