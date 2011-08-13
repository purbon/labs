#!/usr/bin/env ruby

require 'benchmark'

class MyObject

  attr_accessor :string

  def initialize
    @string = (0...8).map{65.+(rand(25)).chr}.join
  end

  def _do
    {
      :label =>  self.string
    }
  end

end

times = 8000

a = []
times.times do |i|
  a[i] = MyObject.new
end

def do_map(ma)
 na = []
 ma.each do |e|
   na << e._do
 end
 na
end

puts "Benchmarking big objects"

Benchmark.bm do |x|
  x.report("map") { a.map(&:_do) }
  x.report("map witb bloc") { a.map { |e| e._do } }
  x.report("my map") { do_map(a) }
end

b = []

times.times do |i|
  b << i
end

def do_map_int(ma)
  na = []
  ma.each do |e|
    na << e.abs
  end
end

puts "Benchmarking thin objects"

Benchmark.bm do |x|
  x.report("map") { b.map(&:abs) }
  x.report("map witb bloc") { b.map { |e| e.abs } }
  x.report("my map") { do_map_int(b) }
end

