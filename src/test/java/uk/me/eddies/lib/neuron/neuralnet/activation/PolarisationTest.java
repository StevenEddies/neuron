/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.activation;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class PolarisationTest {

	@Test
	public void unipolarShouldCoverZeroToOne() {
		assertThat(Polarisation.UNIPOLAR.getMinimum(), equalTo(0d));
		assertThat(Polarisation.UNIPOLAR.getMaximum(), equalTo(1d));
	}

	@Test
	public void bipolarShouldCoverMinusOneToOne() {
		assertThat(Polarisation.BIPOLAR.getMinimum(), equalTo(-1d));
		assertThat(Polarisation.BIPOLAR.getMaximum(), equalTo(1d));
	}

	@Test
	@Parameters(source = Polarisation.class)
	public void shouldHaveCorrectRange(Polarisation systemUnderTest) {
		assertThat(systemUnderTest.getRange(), equalTo(systemUnderTest.getMaximum() - systemUnderTest.getMinimum()));
	}
}
