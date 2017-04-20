/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.concurrency;

import static java.util.Collections.emptySet;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ResourcePoolTest {

	@Mock private Object resource1;
	@Mock private Object resource2;
	@Mock private Object resource3;

	private ResourcePool<Object> systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new ResourcePool<>(Arrays.asList(resource1, resource2, resource3));
	}

	@Test(timeout = 10000)
	public void shouldAcquireEachResourceInTurn() throws InterruptedException {
		assertThat(systemUnderTest.acquire(), sameInstance(resource1));
		assertThat(systemUnderTest.acquire(), sameInstance(resource2));
		assertThat(systemUnderTest.acquire(), sameInstance(resource3));
		assertThat(systemUnderTest.available(), equalTo(0));
	}

	@Test
	public void shouldAllBeInitiallyAvailable() {
		assertThat(systemUnderTest.available(), equalTo(3));
	}

	@Test(timeout = 10000)
	public void shouldBeAvailableAfterReleasing() throws InterruptedException {
		systemUnderTest.acquire();
		systemUnderTest.acquire();
		systemUnderTest.acquire();

		systemUnderTest.release(resource2);
		systemUnderTest.release(resource1);

		assertThat(systemUnderTest.available(), equalTo(2));
	}

	@Test(timeout = 10000)
	public void shouldAllowReacquisitionAfterReleasing() throws InterruptedException {
		systemUnderTest.acquire();
		systemUnderTest.acquire();
		systemUnderTest.acquire();

		systemUnderTest.release(resource2);
		systemUnderTest.release(resource1);

		assertThat(systemUnderTest.acquire(), sameInstance(resource2));
		assertThat(systemUnderTest.acquire(), sameInstance(resource1));
	}

	@Test(expected = IllegalStateException.class, timeout = 10000)
	public void shouldFailToReleaseReleasedResource() throws InterruptedException {
		systemUnderTest.acquire();

		systemUnderTest.release(resource3);
	}

	@Test(expected = IllegalStateException.class, timeout = 10000)
	public void shouldFailToReleaseUnknownResource() throws InterruptedException {
		systemUnderTest.acquire();

		systemUnderTest.release(mock(Object.class));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToReleaseNull() {
		systemUnderTest.release(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullResourcesCollection() {
		new ResourcePool<>(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullResource() {
		new ResourcePool<>(Arrays.asList(resource1, resource2, null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailToConstructWithEmptyResources() {
		new ResourcePool<>(emptySet());
	}

	@Test
	public void shouldWorkIndependentlyOfChangesToInputCollection() {
		Collection<Object> input = new ArrayList<>(Arrays.asList(resource1, resource2, resource3));
		systemUnderTest = new ResourcePool<>(input);
		input.clear();
		assertThat(systemUnderTest.available(), equalTo(3));
	}
}
