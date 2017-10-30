/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.integrationtest;

import static java.util.Collections.unmodifiableList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;
import uk.me.eddies.lib.neuron.neuralnet.activation.Polarisation;
import uk.me.eddies.lib.neuron.neuralnet.activation.ThresholdActivation;
import uk.me.eddies.lib.neuron.neuralnet.management.BulkQueryTool;
import uk.me.eddies.lib.neuron.neuralnet.type.feedforward.FeedforwardBuilder;

/**
 * Integration test using the bulk query tool to replicate an XOR gate.
 */
public class BulkQueryXorIT {

	private static final List<Double> WEIGHTS = unmodifiableList(Arrays.asList(
			2d, 2d, -1d, // Connections to H0 from I0, I1 and IB respectively, makes an OR of the inputs
			2d, 2d, -3d, // Connections to H1 from I0, I1 and IB respectively, makes an AND of the inputs
			2d, -2d, -1d)); // Connections to O from H0, H1 and HB respectively, makes H0 AND NOT H1
	private static final List<List<Double>> INPUTS = unmodifiableList(Arrays.asList(
			Arrays.asList(0d, 0d),
			Arrays.asList(0d, 1d),
			Arrays.asList(1d, 0d),
			Arrays.asList(1d, 1d)));
	private static final List<List<Double>> OUTPUTS = unmodifiableList(Arrays.asList(
			Arrays.asList(0d),
			Arrays.asList(1d),
			Arrays.asList(1d),
			Arrays.asList(0d)));

	private ExecutorService executor;
	private NeuralNetwork network;
	private BulkQueryTool queryer;

	@Before
	public void setUp() {
		network = new FeedforwardBuilder()
				.setInput(2)
				.addHidden(2, new ThresholdActivation(Polarisation.UNIPOLAR))
				.setOutput(1, new ThresholdActivation(Polarisation.UNIPOLAR))
				.buildNetwork();
		executor = Executors.newCachedThreadPool();
		queryer = new BulkQueryTool(executor, network);
	}

	@Test
	public void shouldProduceCorrectResults() {
		queryer.setWeights(WEIGHTS);
		assertThat(queryer.query(INPUTS), equalTo(OUTPUTS));
	}

	@After
	public void cleanUp() {
		assertThat(executor.shutdownNow(), empty());
	}
}
