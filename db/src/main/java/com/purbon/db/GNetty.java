package com.purbon.db;

import com.purbon.db.Impl.GraphImpl;

public class GNetty {

	public Graph newGraph(String name) {
		return new GraphImpl(name);
	}
}
