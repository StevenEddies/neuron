/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;
import java.util.Collection;
import java.util.stream.Stream;
import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;
import uk.me.eddies.lib.neuron.utility.concurrency.ResourcePool;

/**
 * Factory for {@link ResourcePool}s whose resources are {@link NeuralNetwork}s.
 */
public class NetworkPools {

	private final NetworkCloner cloner;

	public NetworkPools(NetworkCloner cloner) {
		this.cloner = requireNonNull(cloner);
	}

	public ResourcePool<NeuralNetwork> build(NeuralNetwork base, int poolSize) {
		Collection<NeuralNetwork> clones = Stream.generate(() -> base)
				.limit(poolSize)
				.map(cloner::clone)
				.collect(toSet());
		return new ResourcePool<>(clones);
	}
}
