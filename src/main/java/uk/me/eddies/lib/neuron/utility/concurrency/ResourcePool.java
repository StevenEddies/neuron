/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.concurrency;

import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a pool of interchangeable resources which may be temporarily acquired by threads.
 */
public class ResourcePool<ResourceT> {

	private final BlockingQueue<ResourceT> available;
	private final Set<ResourceT> acquired;

	public ResourcePool(Collection<ResourceT> resources) {
		available = new ArrayBlockingQueue<>(resources.size(), true, resources);
		acquired = newSetFromMap(new ConcurrentHashMap<>());
	}

	ResourceT acquire() throws InterruptedException {
		ResourceT resource = available.take();
		acquired.add(resource);
		return resource;
	}

	void release(ResourceT resource) {
		if (!acquired.remove(requireNonNull(resource)))
			throw new IllegalStateException("Cannot release resource as it had not been previously acquired.");
		available.add(resource);
	}

	int available() {
		return available.size();
	}
}
