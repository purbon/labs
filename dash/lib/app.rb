require 'initialize'
require 'helpers'
require 'json'

class App < Sinatra::Application

  include Helpers

  get '/' do
    erb :index
  end

  get '/events.json' do
    generate_data
  end

  get '/best-tps.json' do
    generate_data(10)
  end

end
