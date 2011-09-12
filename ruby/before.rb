#!/usr/bin/env ruby

module I
  class B
    def before_method
      puts "before method"
    end

    def self.run(method)
        send :define_method, method do
          before_method
          puts "method #{method}"
        end
    end
  end

  class A < B
    run :m
    run :n
  end
end

#a = I::A.new
#a.m
#a.n

class B
   def self.before_method
     puts "before class method"
   end

  def self.run(method, &block)
    self.class.instance_eval do
      define_method(method) do |*args|
        before_method
        block.call args
      end
    end
  end
end


class A < B
  run :m do |i|
    puts "method following #{i}"
  end
end

A.m(10000)
