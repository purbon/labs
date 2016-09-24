require "spec_helper"

describe RubyClass do
  subject { RubyClass.instance }
  it "call the original method" do
      expect(subject).to receive(:shout).and_call_original
      expect(subject.shout).to eq("RubyModule.get called!!")
  end
end

describe JRubyClass do
  subject { JRubyClass.instance }
  it "call the original method" do
   expect(subject).to receive(:shout).and_call_original
   expect(subject.shout).to eq("JRubyModuleExtension.get called")
  end
end

##
# This test will fail, as we're using the module
# with a method defined with an alias.
##
describe Foo::Bar do
  it "call the original method" do
    expect(subject).to receive(:shout).and_call_original
    expect(subject.shout).to eq("JRubyModuleExtension.get called")
  end
  it "call the original method" do
    expect(subject).to receive(:shout).and_call_original
    expect(subject.shout).to eq("JRubyModuleExtension.get called")
  end
end
