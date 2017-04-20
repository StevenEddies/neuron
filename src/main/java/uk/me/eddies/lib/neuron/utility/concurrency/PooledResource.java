/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.concurrency;

/**
 * Represents a resource from a {@link ResourcePool}.
 */
public class PooledResource<ResourceT> implements AutoCloseable {

	private final ResourcePool<ResourceT> pool;
	private final ResourceT resource;

	public PooledResource(ResourcePool<ResourceT> pool) throws InterruptedException {
		this.pool = pool;
		this.resource = this.pool.acquire();
	}

	public ResourceT get() {
		return resource;
	}

	@Override
	public void close() {
		pool.release(resource);
	}
}
