package com.purbon.db.graph;

import com.purbon.db.Graph;
import com.purbon.db.graph.Impl.GraphImpl;
import com.purbon.db.graph.Impl.PGraphImpl;

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
