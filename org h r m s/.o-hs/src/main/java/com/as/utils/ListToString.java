package com.as.utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListToString {

	public static StringBuffer Bind(List<String> items) {
		
		if(items == null) { return null; }
		
		int expectedSize = 0;
		
		for (String item : items) { expectedSize += item.length(); }
		
		StringBuffer result = new StringBuffer(expectedSize);
		
		for (String item : items) { result.append(item); }
		
		return result;
	}

	public static String getString(List<String> items) {
		if (items == null) { return " "; }
		return items.stream().map(i -> i.toString().trim()).collect(Collectors.joining(", "));
	}

	public static String getString(Set<String> items) {
		if (items == null) { return " "; }
		return items.stream().map(i -> i.toString().trim()).collect(Collectors.joining(", "));
	}
}
