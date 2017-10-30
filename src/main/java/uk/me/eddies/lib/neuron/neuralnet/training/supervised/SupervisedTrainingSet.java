/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.neuralnet.training.supervised;

import static java.util.Collections.unmodifiableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a set of supervised training data.
 */
public class SupervisedTrainingSet {

	private final List<SupervisedTrainingRecord> records;

	public SupervisedTrainingSet(List<SupervisedTrainingRecord> records) {
		this.records = unmodifiableList(new ArrayList<>(records));
		this.records.forEach(Objects::requireNonNull);
	}

	public List<SupervisedTrainingRecord> getRecords() {
		return records;
	}
}
