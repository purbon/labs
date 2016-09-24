module Helpers

  def generate_data(days=30)
    data  = {}
    today = Time.now-86400
    days.times do |i|
      time = today-(86400*(days-i))
      data[time.to_s.split(' ').first] = 7.times.map{ Random.rand(30000) }
    end
    data.to_json
  end

end
