# Data Flow

Every BPMN workflow instance has a JSON document associated with it, called the *workflow instance payload*. The payload carries contextual data of the workflow instance that is required by job workers to do their work. It can be provided when a workflow instance is created. Job workers can read and modify it.

![payload](/bpmn-workflows/payload1.png)

Payload is the link between workflow instance context and workers and therefore a form of coupling. We distinguish two cases:

1. **Tightly coupled job workers**: Job workers work with the exact JSON structure that the workflow instance payload has. This approach is applicable when workers are used only in a single workflow and the payload format can be agreed upon. It is convenient when building both, workflow and job workers, from scratch.
1. **Loosely coupled job workers**: Job workers work with a different JSON structure than the workflow instance payload. This is often the case when job workers are reused for many workflows or when workers are developed independently of the workflow.

Without additional configuration, Zeebe assumes *tightly* coupled workers. That means, on job execution the workflow instance payload is provided as is to the job worker:

![payload](/bpmn-workflows/payload2.png)

When the worker modifies the payload, the result replaces the workflow instance payload.

In order to use *loosely* coupled job workers, the workflow can be extended by *payload mappings* based on [JSONPath](http://goessner.net/articles/JsonPath/). Before providing the job to the worker, Zeebe applies the mappings to the payload and generates a new JSON document. Upon job completion, the same principle is applied to map the result back into the workflow instance payload:

![payload](/bpmn-workflows/payload3.png)

See the [Tasks section](bpmn-workflows/tasks.html) for how to define payload mappings in BPMN.

**Note**: Mappings are not a tool for performance optimization. While a smaller document can save network bandwidth when publishing the job, the broker has extra effort of applying the mappings during workflow execution.
