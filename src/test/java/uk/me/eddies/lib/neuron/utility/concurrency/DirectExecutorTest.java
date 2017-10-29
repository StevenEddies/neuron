/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.concurrency;

import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class DirectExecutorTest {

	private static final String VALUE_1 = "slisdfoji";
	private static final String VALUE_2 = "slkdjlkll";
	private static final String VALUE_3 = "lksklsjdl";

	@Mock private Runnable runnable;
	@Mock private Callable<String> callable1;
	@Mock private Callable<String> callable2;
	@Mock private Callable<String> callable3;

	private DirectExecutor systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new DirectExecutor();
	}

	@Test
	public void shouldExecuteRunnable() {
		systemUnderTest.execute(runnable);
		verify(runnable).run();
	}

	@Test
	public void shouldReturnEmptyFromShutdownNow() {
		assertThat(systemUnderTest.shutdownNow(), emptyIterable());
	}

	@Test
	public void shouldNeverBeShutdown() {
		assertThat(systemUnderTest.isShutdown(), equalTo(false));
		systemUnderTest.shutdownNow();
		assertThat(systemUnderTest.isShutdown(), equalTo(false));
	}

	@Test
	public void shouldNeverBeTerminated() {
		assertThat(systemUnderTest.isTerminated(), equalTo(false));
		systemUnderTest.awaitTermination(0, TimeUnit.SECONDS);
		assertThat(systemUnderTest.isTerminated(), equalTo(false));
	}

	@Test
	public void shoudImmediatelyCompleteAwaitingTermination() {
		assertThat(systemUnderTest.awaitTermination(0, TimeUnit.SECONDS), equalTo(true));
	}

	@Test
	public void shouldSubmitCallableWithNormalCompletion() throws Exception {
		when(callable1.call()).thenReturn(VALUE_1);
		assertThat(systemUnderTest.submit(callable1).get(), equalTo(VALUE_1));
	}

	@Test(expected = ExecutionException.class)
	public void shouldSubmitCallableWithException() throws Exception {
		when(callable1.call()).thenThrow(new NullPointerException());
		try {
			systemUnderTest.submit(callable1).get();
		} catch (ExecutionException ex) {
			assertThat(ex.getCause(), instanceOf(NullPointerException.class));
			throw ex;
		}
	}

	@Test
	public void shouldSubmitResultRunnableWithNormalCompletion() throws Exception {
		assertThat(systemUnderTest.submit(runnable, VALUE_1).get(), equalTo(VALUE_1));
	}

	@Test(expected = ExecutionException.class)
	public void shouldSubmitResultRunnableWithException() throws Exception {
		doThrow(new NullPointerException()).when(runnable).run();
		try {
			systemUnderTest.submit(runnable, VALUE_1).get();
		} catch (ExecutionException ex) {
			assertThat(ex.getCause(), instanceOf(NullPointerException.class));
			throw ex;
		}
	}

	@Test
	public void shouldSubmitRunnableWithNormalCompletion() throws Exception {
		assertThat(systemUnderTest.submit(runnable).get(), nullValue());
	}

	@Test(expected = ExecutionException.class)
	public void shouldSubmitRunnableWithException() throws Exception {
		doThrow(new NullPointerException()).when(runnable).run();
		try {
			systemUnderTest.submit(runnable).get();
		} catch (ExecutionException ex) {
			assertThat(ex.getCause(), instanceOf(NullPointerException.class));
			throw ex;
		}
	}

	@Test
	public void shouldInvokeMultipleCallables() throws Exception {
		when(callable1.call()).thenReturn(VALUE_1);
		when(callable2.call()).thenReturn(VALUE_2);
		when(callable3.call()).thenReturn(VALUE_3);

		List<Future<String>> result = systemUnderTest.invokeAll(Arrays.asList(callable1, callable2, callable3));

		assertThat(result.get(0).get(), equalTo(VALUE_1));
		assertThat(result.get(1).get(), equalTo(VALUE_2));
		assertThat(result.get(2).get(), equalTo(VALUE_3));
	}

	@Test
	public void shouldInvokeMultipleCallablesWithTimeout() throws Exception {
		when(callable1.call()).thenReturn(VALUE_1);
		when(callable2.call()).thenReturn(VALUE_2);
		when(callable3.call()).thenReturn(VALUE_3);

		List<Future<String>> result =
				systemUnderTest.invokeAll(Arrays.asList(callable1, callable2, callable3), 0, TimeUnit.SECONDS);

		assertThat(result.get(0).get(), equalTo(VALUE_1));
		assertThat(result.get(1).get(), equalTo(VALUE_2));
		assertThat(result.get(2).get(), equalTo(VALUE_3));
	}

	@Test
	public void shouldGetAnyResult() throws Exception {
		when(callable1.call()).thenReturn(VALUE_1);
		when(callable2.call()).thenReturn(VALUE_2);
		when(callable3.call()).thenReturn(VALUE_3);

		String result = systemUnderTest.invokeAny(Arrays.asList(callable1, callable2, callable3));
		assertThat(result, equalTo(VALUE_1));
	}

	@Test(expected = ExecutionException.class)
	public void shouldGetAnyResultWithException() throws Exception {
		when(callable1.call()).thenThrow(new NullPointerException());
		when(callable2.call()).thenReturn(VALUE_2);
		when(callable3.call()).thenReturn(VALUE_3);

		try {
			systemUnderTest.invokeAny(Arrays.asList(callable1, callable2, callable3));
		} catch (ExecutionException ex) {
			assertThat(ex.getCause(), instanceOf(NullPointerException.class));
			throw ex;
		}
	}

	@Test
	public void shouldGetAnyResultWithTimeout() throws Exception {
		when(callable1.call()).thenReturn(VALUE_1);
		when(callable2.call()).thenReturn(VALUE_2);
		when(callable3.call()).thenReturn(VALUE_3);

		String result = systemUnderTest.invokeAny(Arrays.asList(callable1, callable2, callable3), 0, TimeUnit.SECONDS);
		assertThat(result, equalTo(VALUE_1));
	}
}
