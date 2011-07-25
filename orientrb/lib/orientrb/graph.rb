import com.orientechnologies.orient.core.db.graph.OGraphDatabase

module OrientDB
  class Graph
    
    def initialize()
      @master_db = OGraphDatabase.new("local:#{OrientDB::Config.storage_path}")
    end

    def create()
      @@database = @master_db.create();
      @@database
    end

    def open(user='admin', pass='admin')
      @@database = @master_db.open(user, pass)
      @@database
    end

    def self.db
      @@database
    end

    def save
      @@database.save
    end

    def close
      @master_db.close
    end

  end
end
