/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management;

import static java.util.stream.Collectors.toList;
import java.util.stream.DoubleStream;
import uk.me.eddies.lib.neuron.neuralnet.Connections;
import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;

/**
 * Capable of resetting a network according to a {@link ResetDistribution}.
 */
public class NetworkResetter {

	public void reset(NeuralNetwork network, ResetDistribution distribution) {
		Connections weights = network.getAllConnections();
		weights.setWeights(DoubleStream.generate(distribution)
				.boxed()
				.limit(weights.getCount())
				.collect(toList()));
	}
}
