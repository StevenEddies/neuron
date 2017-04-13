/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet;

import static java.util.Objects.requireNonNull;

/**
 * Represents a neural network.
 */
public class NeuralNetwork {
	
	private final MutableInterfaceNeurons inputs;
	private final InterfaceNeurons outputs;
	
	public NeuralNetwork(MutableInterfaceNeurons inputs, InterfaceNeurons outputs) {
		this.inputs = requireNonNull(inputs);
		this.outputs = requireNonNull(outputs);
	}

	public MutableInterfaceNeurons getInputs() {
		return inputs;
	}

	public InterfaceNeurons getOutputs() {
		return outputs;
	}
}
