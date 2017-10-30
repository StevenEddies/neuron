/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management;

import static java.lang.Thread.currentThread;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;
import uk.me.eddies.lib.neuron.utility.concurrency.PooledResource;
import uk.me.eddies.lib.neuron.utility.concurrency.ResourcePool;
import uk.me.eddies.lib.neuron.utility.system.SystemInfo;

/**
 * Network management tool to efficiently run multiple queries on a network using multiple threads.
 */
public class BulkQueryTool {

	private final ExecutorService executor;
	private final ResourcePool<NeuralNetwork> networkPool;

	private List<Double> weights;

	public BulkQueryTool(ExecutorService executor, ResourcePool<NeuralNetwork> networkPool) {
		this.executor = requireNonNull(executor);
		this.networkPool = requireNonNull(networkPool);
	}

	public BulkQueryTool(ExecutorService executor, NeuralNetwork network) {
		this(executor, new NetworkPools(new NetworkCloner()).build(network, new SystemInfo()));
	}

	public void setWeights(List<Double> newWeights) {
		newWeights.forEach(Objects::requireNonNull);
		weights = unmodifiableList(new ArrayList<>(newWeights));
	}

	public List<List<Double>> query(List<List<Double>> inputs) {
		inputs.forEach(input -> input.forEach(Objects::requireNonNull));
		List<Future<List<Double>>> tasks = inputs.stream()
				.map(this::asyncSingleQuery)
				.collect(toList());
		return tasks.stream()
				.map(this::retrieveResult)
				.collect(toList());
	}

	private List<Double> retrieveResult(Future<List<Double>> future) {
		try {
			return future.get();
		} catch (InterruptedException e) {
			currentThread().interrupt();
			throw new RuntimeException("Query thread interrupted while running query.", e);
		} catch (ExecutionException e) {
			throw new RuntimeException("Query thread failed to run query.", e.getCause());
		}
	}

	private Future<List<Double>> asyncSingleQuery(List<Double> inputs) {
		return executor.submit(() -> singleQuery(inputs));
	}

	private List<Double> singleQuery(List<Double> inputs) throws InterruptedException {
		try (PooledResource<NeuralNetwork> network = new PooledResource<>(networkPool)) {
			network.get().getAllConnections().setWeights(weights);
			network.get().getInputs().setValues(inputs);
			return network.get().getOutputs().getValues();
		}
	}
}
