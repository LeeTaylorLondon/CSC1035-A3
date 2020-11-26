---
title: Assessment 3 (Team Project) - Small Business Case Study
author: Jordan Barnes
---

# Overview

This assignment is a team project that will introduce you to working on a
singular codebase with other members from your team. This means that every
member of your team will be contributing to a singular solution to a client's
problem.

The general scenario  of this team assignment is to implement a programmatic
solution to a clients specification. The client's solution will provide an
overview of their functional needs for the system that they want you to
implement. You will find a more detailed client specification below.

You will be developing a functional Java `back-end` system. This will require
you to develop functional interfaces so that the system can be expanded later
on.

## Technical Learning Outcomes

- How to create and write an object-oriented Java program
- How to effectively use Interfaces in Java
- How to use data structures from the Java collections framework
- How to use Hibernate for communicating with a database

## Software Engineering Learning Outcomes

- Working effectively in a team environment to plan, organise and deliver a
solution
- To communicate effectively with each other
- To analyse a specification from a client and deliver a programmatic solution

## Client Specification

### Client Problem Explanation

*I am the owner of a small business that sells homemade trinkets and household
items. I have a store 'Tricky Trinkets' on what used to be a quiet high street
in the village. There has been a bunch of development in the village, and thus
bringing in more custom. I used to manage with the small number of customers,
but now I am starting to struggle processing customer transactions with the
increased customer count.

Currently, I run the store single-handed and process customer transactions
through a manual checkout till. I have a paper log of the amount of what each
customer has purchased, a paper log of the stock in the store, and I write
receipts out manually for each transaction. You can start to see why I can't
handle the increase in customers.

I'd ideally like a program, or some kind of solution, that does all of that for
me. Of course, if you can come up with some extra features that would make my
life easier then that'd be great.

Kind Regards,
Tricky Trinkets Manager
*

### Clients Requirements

From the previous client explanation, the client would like the following:

- Electronic Point of Sale (EPOS) program
- An EPOS that has a count of available stock
- An EPOS that allows for customer transaction of stock, including a cost of the
product, the amount of money given by the customer and the change given back to
the client
- A way of producing a receipt of the transaction in the EPOS system
- A way of updating stock outside of a transaction

The client has given almost free-reign to develop extra features that you may
deem useful for the program. However, you **must not** do  the following:

- Produce Graphical User Interface (GUI). You are only developing a command-line
application with a necessary Interface so that the application can be expanded
later to provide a GUI
- Use any other database than the one provided
- Leave any testing code in the main section of the program, all test code must be in a separate directory

To follow good standards of development, you **must** include the following:

- An Interface to communicate with the program
- Use appropriate collections and algorithms when manipulating information
- Follow CRUD when manipulating the database with Hibernate
- Include JavaDoc comments in the code so that documentation can be generated
- Use version control, an appropriate GitFlow, and appropriate commit messages. Commit messages will be checked for
contribution to the team

## Connecting to the Database

- You can use DBeaver or IntelliJ to connect to the database.
The connection information details are in your project description for your Team
on NUcode
- Database connection files using Maven and Hibernate will be provided. You need
to fork this into your Team space. You can use this as the basis of your project
if you want

The `hibernate.cfg.xml` file contains the necessary database connection configuration settings

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://cs-db.ncl.ac.uk/DATABASE_NAME</property>
        <property name="hibernate.connection.username">USERNAME</property>
        <property name="hibernate.connection.password">PASSWORD</property>
        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="show_sql">true</property>
        <property name="format_sql">true</property>
        <property name="hbm2ddl.auto">update </property>

        <mapping class="csc1035.project3.CLASS" /> <!--- Add a new mapping class for a new table in the database -->
        <mapping class="csc1035.project3.CLASS" />

    </session-factory>
</hibernate-configuration>
```

You can find your teams connection settings in the description of your team on NUcode

## What you need to deliver

You need to submit a 1000-word-report broken into the following sections:

- An outline of your contribution and responsibilities to the team project
- A personal log table that gives more information about your specific technical
contributions to the project. For example

|File/Method Created or Modified|Date|Description of work|
|:-----------|:----|:----------|
|Created `complexAlgorithm()` method in Foo.java|10/02/2020|Created a method that has a purpose of x and does y|

You should provide a short couple of sentence description of what you've done
for each contribution. **Your log does not count towards the word count**

- A reflective commentary on the project, making use of reflection theory. See
[here][1]

## What the team need to deliver

- Your team need to produce a software solution for the project outline above
- Meet the requirements outlined about the client
- Include Javadoc in the program
- Produce evidence of testing

## Marking Criteria

The submission will be allocated marks for:

- Individual Report (75%) - This is an individual mark
- Produced program (25%) - This is a team mark.

Both marks will be combined to produce your final individual mark for this assessment.

# Deliverables

Your coursework must be submitted to the NESS system by the deadline
specified. Please note that NESS imposes deadlines rigorously, and
even work that is a few seconds late (e.g. because of network delays
caused by students all submitting at the last moment) will be flagged
as late. Please allow for at least 15 minutes to upload your files.

You should submit to NESS a zip file containing the following:

- Your Java project source code (i.e. all of your .java files)
- Your Word/PDF documentation with the NUCode URL of your last commit
  ("HEAD") to NESS (please ensure that the URL includes a Git hash)
- Any test data needed to run your project. **HINT**: ensure to use relative
  file paths in your code, not absolute ones


[1]: https://portfolio.ncl.ac.uk/help/reflection/
