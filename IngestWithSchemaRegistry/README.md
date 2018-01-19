# PoC for a reliable ingestion pipeline with semantic validation

This is a dummy implementation of an ingestion endpoint with semantic
validation. What does this means? you might be wondering..... nothing
more, nothing less than making sure that:

* There is an endpoint able to receive messages.
* This messages could be in any format, but need to follow a predefined
  schema.
* Schemas can follow an arbitrary way of live, there should be an
  component responsible of following their evolution. This evolution
  should enable different levels of consumption and information about
  compatibility.

This is a reliable pattern to implement a trustworthy ingestion system
that receive messages that your platform is going to be able to process.

To implement this we have used two well known open source solutions.

For the receiver part, we used [Apache Nifi](http://nifi.apache.org/).
In their own words:

```
Apache NiFi supports powerful and scalable directed graphs of data routing, transformation, and system mediation logic. Some of the high-level capabilities and objectives of Apache NiFi include:

* Web-based user interface
* Seamless experience between design, control, feedback, and monitoring
* Highly configurable
* Loss tolerant vs guaranteed delivery
* Low latency vs high throughput
* Dynamic prioritization
* Flow can be modified at runtime
* Back pressure
* Data Provenance
* Track dataflow from beginning to end
* Designed for extension
* Build your own processors and more
* Enables rapid development and effective testing
* Secure
```

definitely something for you to check if you are planning a component
that will have as responsibility to receive, process and send large
quantities of data.

For this example we used the version 1.5.0.

To perform handle the different schemas, and their validation, in this
PoC we used the Confluence Inc [Schema Registry](https://github.com/confluentinc/schema-registry).

An schema registry, either this, the one provided by [hortonworks](https://github.com/hortonworks/registry), or your own implementation, are responsible of handling the schema evolution and performing the necessary compatibility checks when ask for. They usually work with avro as internal format, however allow the operation other formats such as CSV and JSON.

## How to reproduce this example.

You want to get your hands dirty, to test this example you should:

* Have a version of [Apache Nifi](http://nifi.apache.org/) newer or
  equal to 1.5.0.
* You will find the flow for this example under the directory nifi of
  this repo. Replace the flow.xml.gz file in your nifi config directory
  with this one and you will have the new flows available in your
  installation.
* You will also need to run the schema registry. For this i've prepared
  a docker-compose file with all necessary components, check it and run
  it.
* You will need to register your desired schema in the registry.

Next step is to send your events and see how the validation is being
performed.

I know this document is brief, if you have more question don't hesitate
to open an issue here and we can discuss.


