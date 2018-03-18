# Contributing to spraybot

Thank you for taking the time to contribute to spraybot! We appreciate your ideas, suggestions, and pull requests. Before
you get started though please make sure you take look over this document to ensure the best odds of your code being accepted
into the codebase.

## Read and practive the spraybot Directives!

In the README.md of the source repo the spraybot Directives are  described. They are vitally important and if your PR violates
any of these rules it will likely be refused.

## Add Unit Tests!

spraybot's implementations were designed and developed practicing TDD. It is highly recommended that your spraybot code is also 
developed following this practice. However, the more important factor is that your code is well tested; this does not necessarily 
simply mean good coverage, although well covered code is good. This means that appropriate error cases are covered, the tests 
are simple and easy to maintain, and that the test suite covers all feasible use cases.

## Describe your PR with practical use cases!

spraybot is intentionally designed to be chat provider agnostic and represent the foundational workings of a ChatBot. Beyond 
the generic processing of ChatMessages and possibly executing Commands off of them the `co.spraybot` package should be kept 
free of chat specific code. If chat specific functiionality is provided in your idea please ensure it is included in the 
appropriate package; the convention being the regular package name suffiixed with `-chat-provider`. For example, if you were 
interacting with Slack the pacakge would be `co.spraybot-slack`.

Beyond ensuring that your PR comes in the right package include practical use cases in the commit message/PR description. 
Knowing what value your idea brings with it will be a large deciding factor on whether it gets merged into the codebase.
