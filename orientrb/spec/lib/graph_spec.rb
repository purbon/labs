require 'spec_helper'
require 'fileutils'

describe OrientDB::Graph do

    let(:storage_path) { OrientDB::Config.storage_path }
    let(:db) { db = OrientDB::Graph.new }

    before(:all) do
      db.create
    end

    after(:all) do
      db.close
      FileUtils.remove_dir(storage_path, true)
    end

    it "should create a new database" do
      File.exist?(storage_path).should be_true
    end

    it "should open a previously created database" do
      db.close
      db.open
      db.open?.should be_true
    end

    it "should throw an exception if want to open an opened database" do
      expect { db.open }.to raise_error
    end

    it "should close a database" do
      db.close
      db.open?.should be_false
    end

end
