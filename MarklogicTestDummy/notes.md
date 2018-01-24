# Marklogic

Multi model NoSQL database with ability to store, manager and search JSON and XML and semantic (RDF triples)

Comparing Elasticsearch vs Marklogic: https://db-engines.com/en/system/Elasticsearch;MarkLogic

## On search

MarkLogic provides search features through a set of layered APIs

Search API, which is an XQuery API designed to make it easy to create search applications that contain facets, search results, and snippets.
combines searching, search parsing, search grammar, faceting, snippeting, search term completion, and other search application features into a single API.
you can interact with the Search API through XQuery, REST, Node.js, and Java, using a variety of query styles.

### Different query styles:

Combined Query, Query By Example, String Query, Structured Query.

- Can use String Query Grammar. http://docs.marklogic.com/8.0/guide/search-dev/string-query#id_23164
  Additionally, you can change, extend, and modify the default search parsing grammar in the options node. Most applications will not need it.

- Constrained Searches and Faceted Navigation.The Search API makes it easy to constrain your searches to a subset of the content. 
  You can generate facets from range, collection, geospatial, and custom constraints.

- Lexicon and Range Index-Based APIs. MarkLogic Server has range indexes which index XML and JSON structures such as elements, element
  attributes, XPath expressions, and JSON keys. There are also range indexes over geospatial values.

- Alert API and Built ins. You can create applications that notify users when new content is available that matches a predefined query. 
  There is an API to help build these applications as well as a built-in cts:query constructor (cts:reverse-query) and indexing support to
  build large and scalable alerting applications. For details on alerting applications.

- Semantic Searches. MarkLogic allows you use SPARQL 

### Suggestion and Auto Complete:

The search:suggest function returns suggestions that match a wildcarded string, and it is used in query-completion applications.
For best performance, especially on large databases, use with a default-suggestion-source with a range or collection instead of one with a word lexicon.

The following default-suggestion-source example uses the string range index on the attribute named my-attribute as a source for suggesting 
terms. Range suggestion sources tend to perform the best, especially for large databases. The range index must exist or an exception is thrown
 at search runtime.

```<default-suggestion-source>
  <range type="xs:string">
    <element ns="my-namespace" name="my-localname"/>
    <attribute ns="" name="my-attribute"/>
   </range>
</default-suggestion-source>
```

*Suggestion source:*

For some applications, you want to have a very specific list from which to choose suggestions for a particular constraint. For example,
 you might have a constraint named name that has millions of unique values, but perhaps you only want to make suggestions for a specific
 500 of them.
 
 ```<constraint name="name">
       <range collation="http://marklogic.com/collation" 
              type="xs:string" facet="true">
          <element ns="my-namespace" name="fullname"/>
       </range>
     </constraint>
     <suggestion-source ref="name">
         <range collation="http://marklogic.com/collation" 
              type="xs:string" facet="true">
          <element ns="my-namespace" name="short-list-name"/>
       </range>
     </suggestion-source>
  ```
     
### Other bits

* Use Multiple Query Text Inputs to search:suggest
* Make Suggestions Based on Cursor Position
* Creating a Custom Constraint

### Search Grammar

* Query components and operators: http://docs.marklogic.com/8.0/guide/search-dev/string-query#id_98389
* extending the query grammar: http://docs.marklogic.com/8.0/guide/search-dev/string-query#id_60961

### About Lexicons

A word lexicon stores all of the unique, case-sensitive, diacritic-sensitive words, either in a database, in an element defined by a QName,
or in an attribute defined by a QName.

* A value lexicon stores all of the unique values for an element or an attribute.
* A value co-occurrences lexicon stores all of the pairs of values that appear in the same fragment.
* A geospatial lexicon returns geospatial values from the geospatial index. 
* A range lexicon stores buckets of values that occur within a specified range of values.
* ...

## Text Match Semantics

Whether a value, term, or word query on text content is case-sensitive, diacritic-sensitive, whitespace-sensitive, or punctuation-sensitive depends on the query options in scope during the search. Whether stemming and wildcarding are active similar depends on options and database configuration.

The defaults for text matches are as follows:

* Case: If the criteria text is all lower-case, then the match is case-insenstive. If the criteria contains any upper-case letters, then the match is case sensitive.
* Diacritics: If the criteria text contains no diacritics, then the match is diacritic-insenstive. If the criteria contains any diacritics, then the match is diacritic-sensitive.
* Whitespace: Whitespace insenstive. (Whitespace is still used to tokenize words.)
* Punctuation: If the criteria text contains no punctuation, then the match is punctuation-insenstive. If the criteria contains any punctuation, then the match is punctuation sensitive.
* Stemming: Depends on the database configuration. Stemmed search is disabled on a database by default.
* Wildcarding: Depends on the database configuration and the criteria text. Wildcard searches are disabled on a database by default. If any wildcard search is enabled and the criteria text contains wildcard characters (‘?' or ‘*'), then wildcarding is applied.

