#!/usr/bin/env ruby

require 'benchmark'
require 'model'


times = 8000
bench_times = 10

a = []
times.times do |i|
  a[i] = MyObject.new
end

b = []

times.times do |i|
  b << i
end

puts "Benchmarking big objects"

Benchmark.bm do |x|
  x.report("map") { a.map(&:_do) }
  x.report("map witb bloc") { a.map { |e| e._do } }
  x.report("my map") { do_bm(a) }
end

puts "Benchmarking thin objects"



Benchmark.bm do |x|
  x.report("map") { b.map(&:abs) }
  x.report("map witb bloc") { b.map { |e| e.abs } }
  x.report("my map") { do_tm(b) }
end



