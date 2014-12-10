package edu.imsa.students.milestones.utils;

public class BooleanUtils {
	
	public static final Integer INTEGER_TRUE = 1;
	public static final Integer INTEGER_FALSE = 0;
	
	public static Boolean fromInteger(Integer booleanRepresentation) {
		if(booleanRepresentation != INTEGER_FALSE && booleanRepresentation > 0) {
			return true;
		}
		
		return false;
	}
	
	public static Integer fromBoolean(Boolean bool) {
		if(bool) {
			return INTEGER_TRUE;
		}
		
		return INTEGER_FALSE;
	}
}
