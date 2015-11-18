package com.runescape.core.net;

public enum ByteOrder {

	/**
	 * Stores the least significant byte in the smallest memory address.
	 */
	LITTLE,

	/**
	 * Stores the most significant byte in the smallest memory address.
	 */
	BIG,

	/**
	 * Stores the most significant part in the second to last byte. (V1 Order)
	 */
	MIDDLE,

	/**
	 * Stores the most significant part in the second byte (V2 Order)
	 */
	INVERSE;
}