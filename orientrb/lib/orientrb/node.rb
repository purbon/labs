module OrientDB
    class Node

      def initialize
        db = OrientDB::Graph.db
        @node = db.createVertex()
      end

      def set_property(name, value)
        @node.set(name, value)
      end

    end
end
