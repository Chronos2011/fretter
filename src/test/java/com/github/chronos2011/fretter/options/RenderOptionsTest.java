package com.github.chronos2011.fretter.options;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.chronos2011.fretter.options.RenderOptions.FretLabeling;

public class RenderOptionsTest {
	@Test
	public void testFretLabeling_GetName() {
		assertEquals("scale_membership", FretLabeling.SCALE_MEMBERSHIP.getName());
	}

	@Test
	public void testFretLabeling_GetAlternativeNames() {
		assertEquals(0, FretLabeling.SCALE_MEMBERSHIP.getAlternativeNames().size());
	}
}
