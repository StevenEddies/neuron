/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.concurrency;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class PooledResourceTest {

	@Mock private Object resource;
	@Mock private ResourcePool<Object> pool;

	private PooledResource<Object> systemUnderTest;

	@Before
	public void setUp() throws InterruptedException {
		initMocks(this);
		when(pool.acquire()).thenReturn(resource);
	}

	@Test
	public void shouldAcquireResource() throws InterruptedException {
		systemUnderTest = new PooledResource<>(pool);
		verify(pool).acquire();
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutPool() throws InterruptedException {
		systemUnderTest = new PooledResource<>(null);
	}

	@Test
	public void shouldProvideAccessToResource() throws InterruptedException {
		systemUnderTest = new PooledResource<>(pool);
		assertThat(systemUnderTest.get(), sameInstance(resource));
	}

	@Test
	public void shouldReleaseResourceOnClose() throws InterruptedException {
		systemUnderTest = new PooledResource<>(pool);
		systemUnderTest.close();
		verify(pool).release(resource);
	}
}
