# encoding: utf-8

require "java"
jar_path = File.join(File.dirname(__FILE__), "jars/jruby-ext-1.0.jar" )

if File.exist?(jar_path)
  require jar_path
else
  $CLASSPATH << File.join(File.dirname(__FILE__), "..", "jruby-ext", "target", "classes")
end

require "bar"

module RubyModule
  def shout
    "RubyModule.get called!!"
  end
end

require "singleton"

class RubyClass
  include ::Singleton
  include RubyModule
end

class JRubyClass
  include ::Singleton
  include Foo::Bar
end