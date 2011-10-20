require 'rubygems'
require 'neo4j'

heigth, weight = 0, 0
sudoku = nil

file = File.open("sudoku.txt")

data = file.readline.split(',')
height, weight = data[0].to_i, data[1].to_i
sudoku  = []
height.times do |i|
  sudoku.insert i, []
end

begin
  while (line = file.readline)
    data = line.split(',').map { |i| i.to_i }
    sudoku[data[0]].insert data[1], data[2]
  end
rescue EOFError
    file.close
end

class Cell
  include Neo4j::NodeMixin
  index :row
  index :column
  index :color
end

def add_node(props)
  Neo4j::Transaction.run do
    Cell.new(props)
  end
end

def add_relationship(from, to)
  Neo4j::Transaction.run do 
    Neo4j::Relationship.new(:rel, from, to)
  end
end

def connect_with_row(source, start, size, table)
  (start..size).each do |k|
    target = table[k][start-1]
    add_relationship(source, target)
  end
end

def connect_with_column(source, start, size, table)
  (start..size).each do |k|
    target = table[start-1][k]
    add_relationship(source, target)
  end
end


Neo4j.start

# Node generation
height.times do |i|
  weight.times do |j|
    cell = add_node({:row => i, :column => j, :color => sudoku[i][j]})
    sudoku[i][j] = cell
  end
end

# Edge generation.
# if they are on the same row
# if they are on the same column
# if they are on the same 3x3 cell

height.times do |i|
  weight.times do |j|
    source = sudoku[i][j]
    connect_with_row    source, i+1, height-1, sudoku
    connect_with_column source, j+1, weight-1, sudoku
  end
end

# Connect with the 3x3 cell

[0,3,6].each do |i|
  [0,3,6].each do |j|
    source = sudoku[i][j]
    k = i
    while (k < i + 3)
      z = j + 1
      while (z < j + 3)
        add_relationship(sudoku[i][j], sudoku[k][z])
        z = z + 1
      end
      k = k + 1
    end

  end
end

require 'pacer'
require 'pacer-neo4j'

pacer = Pacer.neo4j(Neo4j.db.graph)
export = Pacer::Utils::YFilesExport.new
export.export(pacer, 'sudoku.graphml')

Neo4j.shutdown
