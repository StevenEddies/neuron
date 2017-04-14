/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static java.util.Objects.requireNonNull;

/**
 * Represents a neural network.
 */
public class NeuralNetwork {
	
	private final NetworkConfiguration configuration;
	private final MutableInterfaceNeurons inputs;
	private final InterfaceNeurons outputs;
	private final Connections allConnections;
	
	public NeuralNetwork(NetworkConfiguration configuration, MutableInterfaceNeurons inputs,
			InterfaceNeurons outputs, Connections allConnections) {
		this.configuration = requireNonNull(configuration);
		this.inputs = requireNonNull(inputs);
		this.outputs = requireNonNull(outputs);
		this.allConnections = requireNonNull(allConnections);
	}

	public NetworkConfiguration getConfiguration() {
		return configuration;
	}

	public MutableInterfaceNeurons getInputs() {
		return inputs;
	}

	public InterfaceNeurons getOutputs() {
		return outputs;
	}

	public Connections getAllConnections() {
		return allConnections;
	}
}
