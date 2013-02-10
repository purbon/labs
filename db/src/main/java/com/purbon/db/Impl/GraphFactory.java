package com.purbon.db.Impl;

import com.purbon.db.Graph;

public class GraphFactory {

	public static Graph get(String type) {
		return get(type, "");
	}
	
	public static Graph get(String type, String name) {
		if (type == "tgraph") {
			return new GraphImpl(name);
		} else if (type == "pgraph") {
			return new PGraphImpl();
		}
		return null;
	}
}
