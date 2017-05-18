/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.integrationtest;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;
import uk.me.eddies.lib.neuron.neuralnet.activation.Polarisation;
import uk.me.eddies.lib.neuron.neuralnet.activation.ThresholdActivation;
import uk.me.eddies.lib.neuron.neuralnet.type.feedforward.FeedforwardBuilder;

/**
 * Integration test using a feedforward network to replicate an XOR gate.
 */
@RunWith(JUnitParamsRunner.class)
public class FeedforwardXorIT {

	private NeuralNetwork network;

	@Before
	public void setUp() {
		network = new FeedforwardBuilder()
				.setInput(2)
				.usingActivationFunction(new ThresholdActivation(Polarisation.UNIPOLAR))
				.addHidden(2)
				.setOutput(1)
				.buildNetwork();

		network.getAllConnections().setWeights(Arrays.asList(
				2d, 2d, -1d, // Connections to H0 from I0, I1 and IB respectively, makes an OR of the inputs
				2d, 2d, -3d, // Connections to H1 from I0, I1 and IB respectively, makes an AND of the inputs
				2d, -2d, -1d)); // Connections to O from H0, H1 and HB respectively, makes H0 AND NOT H1
	}

	public Object[][] xorTruthTable() {
		return new Object[][] {
				{ 0, 0, 0 },
				{ 0, 1, 1 },
				{ 1, 0, 1 },
				{ 1, 1, 0 }
		};
	}

	@Test
	@Parameters(method = "xorTruthTable")
	public void shouldProduceCorrectResult(int in1, int in2, int expectedOut) {
		network.getInputs().setValues(Arrays.asList((double) in1, (double) in2));
		assertThat(network.getOutputs().getValues().get(0), closeTo(expectedOut, 0.01));
	}
}
