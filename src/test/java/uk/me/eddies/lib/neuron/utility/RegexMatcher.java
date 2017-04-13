/* Copyright Steven Eddies, 2017. See the LICENCE file in the project root. */

package uk.me.eddies.lib.neuron.utility;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RegexMatcher extends TypeSafeMatcher<String> {

	private String regex;

	public RegexMatcher(String regex) {
		this.regex = regex;
	}

	@Override
	protected boolean matchesSafely(String item) {
		return item.matches(regex);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("String matching regex \"")
				.appendText(regex)
				.appendText("\"");
	}
}
