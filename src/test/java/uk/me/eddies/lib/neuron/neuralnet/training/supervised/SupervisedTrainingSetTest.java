/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.training.supervised;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class SupervisedTrainingSetTest {

	@Mock private SupervisedTrainingRecord record1;
	@Mock private SupervisedTrainingRecord record2;

	private SupervisedTrainingSet systemUnderTest;

	@Before
	public void setUp() {
		initMocks(this);
		systemUnderTest = new SupervisedTrainingSet(Arrays.asList(record1, record2));
	}

	@Test
	public void shouldPublishRecords() {
		assertThat(systemUnderTest.getRecords(), contains(record1, record2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void shouldFailToModifyReturnedRecords() {
		systemUnderTest.getRecords().clear();
	}

	@Test
	public void shouldInsulateFromInputRecordCollectionChanges() {
		List<SupervisedTrainingRecord> records = new ArrayList<>(Arrays.asList(record1, record2));
		systemUnderTest = new SupervisedTrainingSet(records);
		records.clear();
		assertThat(systemUnderTest.getRecords(), contains(record1, record2));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullRecords() {
		new SupervisedTrainingSet(null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailToConstructWithNullRecordValue() {
		new SupervisedTrainingSet(Arrays.asList(record1, null));
	}
}
