import com.orientechnologies.orient.core.db.graph.OGraphDatabase

module OrientDB
  class Graph
    
    def initialize()
      @master_db = OGraphDatabase.new("local:#{OrientDB::Config.storage_path}")
      @opened = false
    end

    def create()
      @@database = @master_db.create();
      @@opened = true
      @@database
    end

    def open(user='admin', pass='admin')
      @@database = @master_db.open(user, pass)
      @@opened = true
      @@database
    end

    def open?
      @@opened
    end

    def self.db
      @@database
    end

    def save
      @@database.save
    end

    def close
      @@opened = false
      @master_db.close
    end

  end
end
