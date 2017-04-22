/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.management;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.me.eddies.lib.neuron.neuralnet.Connections;
import uk.me.eddies.lib.neuron.neuralnet.NeuralNetwork;

public class NetworkResetterTest {

	private static final double WEIGHT1 = 11d;
	private static final double WEIGHT2 = 12d;
	private static final double WEIGHT3 = 13d;

	@Mock private ResetDistribution distribution;
	@Mock private NeuralNetwork network;
	@Mock private Connections weights;

	private NetworkResetter systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		when(distribution.getAsDouble()).thenReturn(WEIGHT1).thenReturn(WEIGHT2).thenReturn(WEIGHT3);
		when(network.getAllConnections()).thenReturn(weights);
		when(weights.getCount()).thenReturn(3);
		systemUnderTest = new NetworkResetter();
	}

	@Test
	public void shouldResetWeightsAccordingToDistribution() {
		systemUnderTest.reset(network, distribution);
		verify(weights).setWeights(Arrays.asList(WEIGHT1, WEIGHT2, WEIGHT3));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToResetWeightsOfNullNetwork() {
		systemUnderTest.reset(null, distribution);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToResetWeightsUsingNullDistribution() {
		systemUnderTest.reset(network, null);
	}
}
