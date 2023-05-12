# Params

[![Maven Central](https://img.shields.io/maven-central/v/com.iloo/params.svg)](https://search.maven.org/artifact/com.iloo/params/2.0-SNAPSHOT/jar)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/{username}/params/blob/master/LICENSE)

DESCRIPTION
===========

The `Params` framework offers a powerful toolset for developers to effectively manage and organize application parameters. By providing a clear and structured way of creating and organizing parameter categories and items, it helps developers to better manage and configure their applications, resulting in more efficient and streamlined workflows.

Using the IParameterFactory interface, developers can abstract away the details of parameter creation, allowing for a more flexible and maintainable codebase. Additionally, the IParameterCategory and IParameterItem interfaces offer a wide range of methods for accessing and modifying parameter information, making it easy to customize the behavior of the application to meet specific requirements.

By adopting the `Params` framework, developers can ensure that their applications are easy to configure and maintain, resulting in a more robust and scalable product. Whether you're building a simple web application or a complex enterprise system, `Params` offers a powerful set of tools for managing and organizing application parameters, making it a must-have tool for developers looking to streamline their workflows and improve the overall quality of their code.



CONTRIBUTOR INSTRUCTIONS
========================

We appreciate your interest in contributing. To ensure a smooth collaboration process, please follow the instructions below:

Issue Ticket Creation:
----------------------

Before starting any work, create an issue ticket in the GitHub repository.
Clearly describe the problem or feature you intend to address.
This will help us track your progress and facilitate discussion.

Branching Strategy:
-------------------

Create a special branch for your changes from the 'develop' branch.
Use a descriptive name for your branch that reflects the purpose of your work, prefixed with "PARAMS-".
For example, if you are fixing a bug related to params conversion, your branch name could be 'bug/conversion-fix'.

Code Guidelines:
----------------

Follow standard Java coding conventions and adhere to the existing project style.
Ensure your code is clean, readable, and well-documented.
Class names should be descriptive and follow the appropriate naming conventions:
Interface classes should start with the letter 'I' (e.g., IParams).
Abstract classes should start with the letter 'A' (e.g., AParams).
Enumerations should start with the letter 'E' (e.g., EParams).

Unit Testing:
-------------

Whenever possible, add unit tests to validate your code changes.
Test classes should be suffixed with 'IT' (e.g., ParamsIT).
Write meaningful test cases to cover different scenarios.
Ensure your tests are independent, isolated, and provide good code coverage.

Maven Build:
------------

Ensure your code compiles successfully and passes all existing tests.
Run mvn clean verify locally before pushing your changes to the repository.
Resolve any build errors or failures that occur.

Documentation and Comments:
---------------------------

Document any changes made to the project, including new features or modifications.
Include comments within your code to explain complex logic or provide additional context.
Pull Request Submission:

Once you have completed your work and tested it thoroughly, submit a pull request (PR) from your branch to the 'develop' branch.
Provide a clear and concise description of the changes made in the PR.
Mention the related issue ticket in the PR description using GitHub's linking syntax (e.g., Fixes #123).

Collaboration and Communication:
--------------------------------

Stay engaged in the GitHub repository's communication channels (issues, comments, PR discussions) to address questions or concerns promptly.
Be open to feedback and iterate on your work based on the review process.
Thank you for your contribution! Your efforts are valuable in improving our project. If you have any further questions or need assistance, feel free to ask on the GitHub repository or contact the project maintainers.