/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.concurrency;

import static java.util.Collections.singleton;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ResourcePoolConcurrentTest {

	@Mock private Object resource1;
	private ExecutorService executor;

	private ResourcePool<Object> systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new ResourcePool<>(singleton(resource1));
		executor = Executors.newCachedThreadPool();
	}

	@After
	public void cleanUp() {
		executor.shutdownNow();
	}

	@Test(timeout = 60000)
	public void shouldBlockDuringAcquisitionUntilResourceIsAvailable() throws InterruptedException, ExecutionException {
		systemUnderTest.acquire();

		CountDownLatch waitingLatch = new CountDownLatch(1);
		Future<Void> acquiredFuture = executor.submit(() -> {
			waitingLatch.countDown();
			systemUnderTest.acquire();
			return null;
		});

		waitingLatch.await();
		Thread.sleep(100);

		assertThat(acquiredFuture.isDone(), equalTo(false));

		systemUnderTest.release(resource1);
		acquiredFuture.get(); // Will time out if thread doesn't unblock once available
	}
}
