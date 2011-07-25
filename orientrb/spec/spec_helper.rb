APP_ROOT = File.expand_path(File.dirname(__FILE__))
$LOAD_PATH.unshift File.join(APP_ROOT, 'lib')
Dir.glob('lib/**').each{ |d| $LOAD_PATH.unshift(File.join(APP_ROOT, d)) }

require 'rubygems'
require 'rspec'
require 'orientrb'
