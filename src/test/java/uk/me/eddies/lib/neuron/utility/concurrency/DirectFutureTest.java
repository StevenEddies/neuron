/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.concurrency;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class DirectFutureTest {

	private static final String VALUE = "aiajoijji";

	@Mock private Exception exception;

	private DirectFuture<String> systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new DirectFuture<String>(VALUE);
	}

	@Test
	public void shouldFailToCancel() {
		assertThat(systemUnderTest.cancel(false), equalTo(false));
		assertThat(systemUnderTest.cancel(true), equalTo(false));
	}

	@Test
	public void shouldNotBeCancelled() {
		assertThat(systemUnderTest.isCancelled(), equalTo(false));
	}

	@Test
	public void shouldBeDone() {
		assertThat(systemUnderTest.isDone(), equalTo(true));
	}

	@Test
	public void shouldGetResult() throws ExecutionException {
		assertThat(systemUnderTest.get(), equalTo(VALUE));
		assertThat(systemUnderTest.get(0, TimeUnit.SECONDS), equalTo(VALUE));
	}

	@Test(expected = ExecutionException.class)
	public void shouldThrowExecutionExceptionWhenAppropriate() throws ExecutionException {
		systemUnderTest = new DirectFuture<>(exception);
		try {
			systemUnderTest.get();
		} catch (ExecutionException ex) {
			assertThat(ex.getCause(), equalTo(exception));
			throw ex;
		}
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullException() {
		new DirectFuture<>((Exception) null);
	}

	@Test
	public void shouldWorkWithNullResult() throws ExecutionException {
		systemUnderTest = new DirectFuture<>((String) null);
		assertThat(systemUnderTest.get(), nullValue());
	}
}
