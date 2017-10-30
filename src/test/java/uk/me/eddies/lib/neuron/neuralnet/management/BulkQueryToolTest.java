/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import uk.me.eddies.lib.neuron.neuralnet.Connections;
import uk.me.eddies.lib.neuron.neuralnet.InterfaceNeurons;
import uk.me.eddies.lib.neuron.neuralnet.MutableInterfaceNeurons;
import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;
import uk.me.eddies.lib.neuron.utility.concurrency.DirectExecutor;
import uk.me.eddies.lib.neuron.utility.concurrency.ResourcePool;

public class BulkQueryToolTest {

	@Mock private NeuralNetwork network1;
	@Mock private Connections connections1;
	@Mock private MutableInterfaceNeurons inputs1;
	@Mock private InterfaceNeurons outputs1;
	@Mock private ExecutorService mockExecutor;
	@Mock private Future<List<Double>> result1;

	private ExecutorService executor;
	private ResourcePool<NeuralNetwork> networkPool;

	private BulkQueryTool systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);

		when(network1.getAllConnections()).thenReturn(connections1);
		when(network1.getInputs()).thenReturn(inputs1);
		when(network1.getOutputs()).thenReturn(outputs1);

		executor = new DirectExecutor();
		networkPool = new ResourcePool<>(Arrays.asList(network1));
		systemUnderTest = new BulkQueryTool(executor, networkPool);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutExecutor() {
		new BulkQueryTool(null, networkPool);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithoutNetworkPool() {
		new BulkQueryTool(executor, (ResourcePool<NeuralNetwork>) null);
	}

	@Test(expected = RuntimeException.class)
	public void shouldFailToQueryBeforeWeightsSet() {
		doThrow(NullPointerException.class).when(connections1).setWeights(null);
		when(outputs1.getValues()).thenReturn(Arrays.asList(5d, 6d));

		systemUnderTest.query(Arrays.asList(Arrays.asList(3d, 4d)));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToSetWeightsToNull() {
		systemUnderTest.setWeights(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToSetWeightsContainingNull() {
		systemUnderTest.setWeights(Arrays.asList(1d, null));
	}

	@Test
	public void shouldPreventModificationOfExternalWeightsCollection() {
		List<Double> weights = new ArrayList<>(Arrays.asList(1d, 2d));
		when(outputs1.getValues()).thenReturn(Arrays.asList(5d, 6d));

		systemUnderTest.setWeights(weights);
		weights.clear();

		List<List<Double>> result = systemUnderTest.query(Arrays.asList(Arrays.asList(3d, 4d)));

		verify(connections1).setWeights(Arrays.asList(1d, 2d));
		verify(inputs1).setValues(Arrays.asList(3d, 4d));

		assertThat(result.get(0), contains(5d, 6d));
	}

	@Test
	public void shouldQueryWithSingleInput() {
		when(outputs1.getValues()).thenReturn(Arrays.asList(5d, 6d));

		systemUnderTest.setWeights(Arrays.asList(1d, 2d));
		List<List<Double>> result = systemUnderTest.query(Arrays.asList(Arrays.asList(3d, 4d)));

		verify(connections1).setWeights(Arrays.asList(1d, 2d));
		verify(inputs1).setValues(Arrays.asList(3d, 4d));

		assertThat(result.get(0), contains(5d, 6d));
	}

	@Test
	public void shouldQueryWithMultipleInputs() {
		when(outputs1.getValues()).thenReturn(Arrays.asList(7d, 8d)).thenReturn(Arrays.asList(9d, 10d));

		systemUnderTest.setWeights(Arrays.asList(1d, 2d));
		List<List<Double>> result = systemUnderTest.query(Arrays.asList(Arrays.asList(3d, 4d), Arrays.asList(5d, 6d)));

		verify(connections1, times(2)).setWeights(Arrays.asList(1d, 2d));
		verify(inputs1).setValues(Arrays.asList(3d, 4d));
		verify(inputs1).setValues(Arrays.asList(5d, 6d));

		assertThat(result, contains(Arrays.asList(contains(7d, 8d), contains(9d, 10d))));
	}

	@Test
	public void shouldQueryWithAllInputsBeforeProcessingResults() {
		when(outputs1.getValues()).thenThrow(new NullPointerException());

		systemUnderTest.setWeights(Arrays.asList(1d, 2d));
		try {
			systemUnderTest.query(Arrays.asList(Arrays.asList(3d, 4d), Arrays.asList(5d, 6d)));
			fail();
		} catch (RuntimeException expected) {
			// Expected
		}

		// All inputs are processed even though processing results will throw an exception
		verify(connections1, times(2)).setWeights(Arrays.asList(1d, 2d));
		verify(inputs1).setValues(Arrays.asList(3d, 4d));
		verify(inputs1).setValues(Arrays.asList(5d, 6d));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToQueryWithNullListList() {
		systemUnderTest.query(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToQueryWithNullList() {
		systemUnderTest.query(Arrays.asList(Arrays.asList(3d, 4d), null));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToQueryWithNullValue() {
		systemUnderTest.query(Arrays.asList(Arrays.asList(3d, 4d), Arrays.asList(5d, null)));
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionIfInterruptedWhileWaitingForResults()
			throws InterruptedException, ExecutionException {
		when(mockExecutor.submit(Mockito.<Callable<List<Double>>> any())).thenReturn(result1);
		when(result1.get()).thenThrow(new InterruptedException());

		systemUnderTest = new BulkQueryTool(mockExecutor, networkPool);

		try {
			systemUnderTest.query(Arrays.asList(Arrays.asList(1d, 2d)));
		} finally {
			assertThat(Thread.interrupted(), equalTo(true));
		}
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowExceptionIfQueryThrowsException()
			throws InterruptedException, ExecutionException {
		when(mockExecutor.submit(Mockito.<Callable<List<Double>>> any())).thenReturn(result1);
		when(result1.get()).thenThrow(new ExecutionException(new NullPointerException()));

		systemUnderTest = new BulkQueryTool(mockExecutor, networkPool);

		try {
			systemUnderTest.query(Arrays.asList(Arrays.asList(1d, 2d)));
		} catch (RuntimeException ex) {
			assertThat(ex.getCause(), instanceOf(NullPointerException.class));
			throw ex;
		}
	}
}
