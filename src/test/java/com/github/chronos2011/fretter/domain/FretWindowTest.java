package com.github.chronos2011.fretter.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class FretWindowTest {
	FretWindow windowOriginal;
	FretWindow windowExtender0;
	FretWindow windowExtender1;
	FretWindow windowExtender2;

	@Before
	public void setup() {
		windowOriginal = new FretWindow();
		windowOriginal.start = 5;
		windowOriginal.end = 12;
		windowOriginal.includeOpen = false;
		windowExtender0 = new FretWindow();
		windowExtender0.start = 4;
		windowExtender0.end = 12;
		windowExtender0.includeOpen = false;
		windowExtender1 = new FretWindow();
		windowExtender1.start = 5;
		windowExtender1.end = 13;
		windowExtender1.includeOpen = false;
		windowExtender2 = new FretWindow();
		windowExtender2.start = 5;
		windowExtender2.end = 12;
		windowExtender2.includeOpen = true;
	}

	@Test
	public void testExtendBy_Start() {
		windowOriginal.extendBy(windowExtender0);
		assertEquals(4, windowOriginal.start);
		assertEquals(12, windowOriginal.end);
		assertEquals(false, windowOriginal.includeOpen);
	}

	@Test
	public void testExtendBy_End() {
		windowOriginal.extendBy(windowExtender1);
		assertEquals(5, windowOriginal.start);
		assertEquals(13, windowOriginal.end);
		assertEquals(false, windowOriginal.includeOpen);
	}

	@Test
	public void testExtendBy_IncludeOpen() {
		windowOriginal.extendBy(windowExtender2);
		assertEquals(5, windowOriginal.start);
		assertEquals(12, windowOriginal.end);
		assertEquals(true, windowOriginal.includeOpen);
	}
}
