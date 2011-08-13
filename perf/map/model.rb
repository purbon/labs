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

def do_tm(ma)
  na = []
  ma.each do |e|
    na << e.abs
  end
  na
end

def do_bm(ma)
  na = []
  ma.each do |e|
    na << e._do
  end
  na
end
