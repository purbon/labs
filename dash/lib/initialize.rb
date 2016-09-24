
env = (ENV['RACK_ENV'] || :development).to_sym

set :root, ROOT
set :environment, env
set :public_folder, "#{ROOT}/public"
