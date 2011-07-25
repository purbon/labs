require 'spec_helper'
require 'fileutils'

describe OrientDB::Graph do

  describe "create" do

    let(:db) { db = OrientDB::Graph.new }

    it "should create a new database" do
      storage_path = OrientDB::Config.storage_path
      db.create
      File.exist?(storage_path).should be_true
      FileUtils.remove_dir(storage_path, true)
    end

  end
end
