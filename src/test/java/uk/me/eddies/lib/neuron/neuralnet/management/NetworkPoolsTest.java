/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management;

import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;
import uk.me.eddies.lib.neuron.utility.concurrency.PooledResource;
import uk.me.eddies.lib.neuron.utility.concurrency.ResourcePool;
import uk.me.eddies.lib.neuron.utility.system.SystemInfo;

public class NetworkPoolsTest {

	@Mock private NetworkCloner cloner;
	@Mock private SystemInfo systemInfo;
	@Mock private NeuralNetwork originalNetwork;
	@Mock private NeuralNetwork clonedNetwork1;
	@Mock private NeuralNetwork clonedNetwork2;

	private NetworkPools systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		when(cloner.clone(originalNetwork)).thenReturn(clonedNetwork1).thenReturn(clonedNetwork2).thenReturn(null);
		when(systemInfo.getProcessorCount()).thenReturn(2);

		systemUnderTest = new NetworkPools(cloner);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutCloner() {
		new NetworkPools(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToBuildPoolWithoutNetwork() {
		systemUnderTest.build(null, 2);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToBuildPoolUsingCountFromSystemInfoWithoutNetwork() {
		systemUnderTest.build(null, systemInfo);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToBuildPoolUsingCountFromSystemInfoWithoutSystemInfo() {
		systemUnderTest.build(originalNetwork, null);
	}

	@SuppressWarnings("resource") // Closeable is only the mock network "resource" used in the test
	@Test(timeout = 10000)
	public void shouldCloneNetworkAndBuildPool() throws InterruptedException {
		ResourcePool<NeuralNetwork> pool = systemUnderTest.build(originalNetwork, 2);
		assertThat(new PooledResource<>(pool).get(),
				either(sameInstance(clonedNetwork1)).or(sameInstance(clonedNetwork2)));
		assertThat(new PooledResource<>(pool).get(),
				either(sameInstance(clonedNetwork1)).or(sameInstance(clonedNetwork2)));
	}

	@SuppressWarnings("resource") // Closeable is only the mock network "resource" used in the test
	@Test(timeout = 10000)
	public void shouldCloneNetworkAndBuildPoolUsingCountFromSystemInfo() throws InterruptedException {
		ResourcePool<NeuralNetwork> pool = systemUnderTest.build(originalNetwork, systemInfo);
		assertThat(new PooledResource<>(pool).get(),
				either(sameInstance(clonedNetwork1)).or(sameInstance(clonedNetwork2)));
		assertThat(new PooledResource<>(pool).get(),
				either(sameInstance(clonedNetwork1)).or(sameInstance(clonedNetwork2)));
	}
}
