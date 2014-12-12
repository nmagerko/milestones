package edu.imsa.students.milestones.utils;

/**
 * Helper class to convert boolean values
 * from their primitive/object representations
 * to their integer representations
 * This is required due to SQLite's lack of a
 * boolean data type
 * @author nmagerko
 *
 */
public class BooleanUtils {
	
	// constants defining how the states of existing booleans should be treated
	public static final Integer INTEGER_TRUE = 1;
	public static final Integer INTEGER_FALSE = 0;
	
	/**
	 * Converts an integer representation of a boolean
	 * to its proper Object representation
	 * @param booleanRepresentation	the boolean, as an integer
	 * @return	the boolean, as an object
	 */
	public static Boolean fromInteger(Integer booleanRepresentation) {
		// as long as the value is not INTEGER_FALSE and is positive,
		// treat the value as true
		if(booleanRepresentation != INTEGER_FALSE && booleanRepresentation > 0) {
			return true;
		}
		// otherwise, return false
		return false;
	}
	
	/**
	 * Converts an Object representation of a boolean
	 * to its proper Integer repesentation
	 * @param bool	the boolean, as an Object
	 * @return	the boolean, as an Integer
	 */
	public static Integer fromBoolean(Boolean bool) {
		// match the provided values exactly
		if(bool) {
			return INTEGER_TRUE;
		}
		
		return INTEGER_FALSE;
	}
}
