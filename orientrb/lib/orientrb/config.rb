module OrientDB
  module Config
    def self.storage_path
      @storage_path ||= 'db'
    end
  end
end
