/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.activation;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PolarisationTest {

	@Test
	public void unipolarShouldHaveRangeZeroToOne() {
		assertThat(Polarisation.UNIPOLAR.getMinimum(), equalTo(0d));
		assertThat(Polarisation.UNIPOLAR.getMaximum(), equalTo(1d));
	}

	@Test
	public void bipolarShouldHaveRangeMinusOneToOne() {
		assertThat(Polarisation.BIPOLAR.getMinimum(), equalTo(-1d));
		assertThat(Polarisation.BIPOLAR.getMaximum(), equalTo(1d));
	}
}
