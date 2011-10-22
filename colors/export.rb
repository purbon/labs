require 'rubygems'
require 'neo4j'
require 'pacer'
require 'pacer-neo4j'


Neo4j.start

pacer = Pacer.neo4j(Neo4j.db.graph)
export = Pacer::Utils::YFilesExport.new
export.export(pacer, 'sudoku.graphml')

Neo4j.shutdown
