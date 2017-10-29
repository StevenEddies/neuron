/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility.concurrency;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * {@link ExecutorService} to simply run tasks inline on the calling thread. Not thread safe and does not perform any
 * special shutdown handling.
 */
public class DirectExecutor implements ExecutorService {

	@Override
	public void execute(Runnable command) {
		command.run();
	}

	@Override
	public void shutdown() {
		// No action necessary
	}

	@Override
	public List<Runnable> shutdownNow() {
		return emptyList();
	}

	@Override
	public boolean isShutdown() {
		return false;
	}

	@Override
	public boolean isTerminated() {
		return false;
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) {
		return true;
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		try {
			return new DirectFuture<>(task.call());
		} catch (Exception ex) {
			return new DirectFuture<>(ex);
		}
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		try {
			task.run();
			return new DirectFuture<>(result);
		} catch (Exception ex) {
			return new DirectFuture<>(ex);
		}
	}

	@Override
	public Future<?> submit(Runnable task) {
		try {
			task.run();
			return new DirectFuture<Void>((Void) null);
		} catch (Exception ex) {
			return new DirectFuture<>(ex);
		}
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) {
		return tasks.stream()
				.map(this::submit)
				.collect(toList());
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
		return invokeAll(tasks);
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws ExecutionException {
		Callable<T> task = tasks.stream()
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException());
		try {
			return task.call();
		} catch (Exception ex) {
			throw new ExecutionException(ex);
		}
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws ExecutionException {
		return invokeAny(tasks);
	}

}
