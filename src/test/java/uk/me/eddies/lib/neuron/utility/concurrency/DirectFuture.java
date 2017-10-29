/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.concurrency;

import static java.util.Objects.requireNonNull;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * {@link Future} representing a result which is available at construction time.
 */
public class DirectFuture<ResultT> implements Future<ResultT> {

	private final Throwable exception;
	private final ResultT result;

	public DirectFuture(ResultT result) {
		this.exception = null;
		this.result = result;
	}

	public DirectFuture(Throwable exception) {
		this.exception = requireNonNull(exception);
		this.result = null;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public ResultT get() throws ExecutionException {
		if (exception != null)
			throw new ExecutionException(exception);

		return result;
	}

	@Override
	public ResultT get(long timeout, TimeUnit unit) throws ExecutionException {
		return get();
	}
}
