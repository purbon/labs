#!/usr/bin/env ruby

require 'benchmark'

class MyObject

  attr_accessor :string

  def initialize
    @string = (0...8).map{65.+(rand(25)).chr}.join
  end

  def _do
    {
      :label =>  (0...8).map{65.+(rand(25)).chr}.join
    }
  end

end

a = []
8000.times do |i|
  a[i] = MyObject.new
end

def do_map(ma)
 na = []
 ma.each do |e|
   na << e._do
 end
 na
end

Benchmark.bm do |x|
  x.report("map") { a.map(&:_do) }
  x.report("map block") { a.map { |e| e._do } }
  x.report("my map") { do_map(a) }
end

