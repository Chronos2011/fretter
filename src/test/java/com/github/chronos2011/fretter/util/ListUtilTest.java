package com.github.chronos2011.fretter.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;

import org.junit.Test;

public class ListUtilTest {
	@Test
	public void testModifiableList() {
		List<String> list = ListUtil.ml();
		assertEquals(0, list.size());
		list = ListUtil.ml("A");
		assertEquals(1, list.size());
		assertEquals("A", list.get(0));
		list = ListUtil.ml("A", "B", "C");
		assertEquals(3, list.size());
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		assertEquals("C", list.get(2));
		list.set(0, "D");
		assertEquals("D", list.get(0));
	}
	@Test
	public void testUnmodifiableList() {
		List<String> list = ListUtil.ul();
		assertEquals(0, list.size());
		list = ListUtil.ul("A");
		assertEquals(1, list.size());
		assertEquals("A", list.get(0));
		list = ListUtil.ul("A", "B", "C");
		assertEquals(3, list.size());
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		assertEquals("C", list.get(2));
		final List<String> finalList = list;
		assertThrows(UnsupportedOperationException.class, () -> finalList.set(0, "D"));
	}
}
