package com.github.chronos2011.fretter.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DomainExceptionTest {
	@Test
	public void testDomainException_Empty() {
		DomainException exception = new DomainException();
		assertEquals(null, exception.getMessage());
		assertEquals(null, exception.getCause());
	}

	@Test
	public void testDomainException_Message() {
		DomainException exception = new DomainException("TEST");
		assertEquals("TEST", exception.getMessage());
		assertEquals(null, exception.getCause());
	}

	@Test
	public void testDomainException_Cause() {
		DomainException exception = new DomainException(new IllegalArgumentException());
		assertEquals(IllegalArgumentException.class.getName(), exception.getMessage());
		assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
	}

	@Test
	public void testDomainException_MessageCause() {
		DomainException exception = new DomainException("TEST", new IllegalArgumentException());
		assertEquals("TEST", exception.getMessage());
		assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
	}
}
