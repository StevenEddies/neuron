/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management;

import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;

/**
 * Responsible for cloning a {@link NeuralNetwork}.
 */
public class NetworkCloner {
	
	public NeuralNetwork clone(NeuralNetwork originalNetwork) {
		NeuralNetwork newNetwork = cloneStructure(originalNetwork);
		cloneWeights(originalNetwork, newNetwork);
		cloneInputs(originalNetwork, newNetwork);
		return newNetwork;
	}

	private NeuralNetwork cloneStructure(NeuralNetwork originalNetwork) {
		return originalNetwork.getConfiguration().create();
	}

	private void cloneWeights(NeuralNetwork originalNetwork, NeuralNetwork newNetwork) {
		newNetwork.getAllConnections().setWeights(
				originalNetwork.getAllConnections().getWeights());
	}

	private void cloneInputs(NeuralNetwork originalNetwork, NeuralNetwork newNetwork) {
		newNetwork.getInputs().setValues(
				originalNetwork.getInputs().getValues());
	}
}
