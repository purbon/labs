require 'spec_helper'

describe OrientDB::Node do

  let(:db) { db = OrientDB::Graph.new }

  before(:all) do
    db.create
  end

  after(:all) do 
    db.close
    FileUtils.remove_dir(storage_path, true)
  end

  it "should create a new node" do

  end

end
