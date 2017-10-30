/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.training.supervised;

import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;

/**
 * Represents a method for iteratively training a neural network using supervised-style data.
 */
public interface SupervisedTrainingMethod {

	public void iterate(NeuralNetwork network, SupervisedTrainingSet trainingSet, SupervisedTrainingSet validationSet);
}
