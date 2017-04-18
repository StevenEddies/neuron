/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.type.feedforward;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Objects.requireNonNull;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import uk.me.eddies.lib.neuron.neuralnet.Connection;
import uk.me.eddies.lib.neuron.neuralnet.InterfaceNeurons;
import uk.me.eddies.lib.neuron.neuralnet.MutableInterfaceNeurons;
import uk.me.eddies.lib.neuron.neuralnet.Neuron;

/**
 * Stores details about an actual layer in a feedforward network.
 */
class Layer {

	private final Collection<Neuron> neurons;
	private final Collection<Connection> connections;
	private final Optional<InterfaceNeurons> outputInterface;
	private final Optional<MutableInterfaceNeurons> inputInterface;

	public Layer(Collection<Neuron> neurons, Collection<Connection> connections,
			Optional<InterfaceNeurons> outputInterface, Optional<MutableInterfaceNeurons> inputInterface) {
		this.neurons = unmodifiableCollection(new LinkedHashSet<>(neurons));
		this.neurons.forEach(Objects::requireNonNull);
		this.connections = unmodifiableCollection(new LinkedHashSet<>(connections));
		this.connections.forEach(Objects::requireNonNull);
		this.outputInterface = requireNonNull(outputInterface);
		this.inputInterface = requireNonNull(inputInterface);
	}

	public Collection<Neuron> getNeurons() {
		return neurons;
	}

	public Collection<Connection> getConnections() {
		return connections;
	}

	public Optional<InterfaceNeurons> getOutputInterface() {
		return outputInterface;
	}

	public Optional<MutableInterfaceNeurons> getInputInterface() {
		return inputInterface;
	}
}
