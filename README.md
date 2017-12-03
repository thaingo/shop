# Online shop "Интернет-магазин DJ"
[![Build Status](https://travis-ci.org/tkaczenko/shop.svg?branch=master)](https://travis-ci.org/tkaczenko/shop)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](/LICENSE.md)

Online store of dj equipment. `shop` is written in Java 8.
> This app was created just for fun and may have some bugs. If you see some, write to me.

## Built With
* Spring Framework (IoC, Boot, MVC, Data, Security, Mail)
* Hibernate/JPA, Hibernate Search, Hibernate Validator
* Junit4, DBUnit
* Selenium WebDriver, Jsoup
* PostgreSQL
* JSP, JSTL, Bootstrap3
* Gradle - using `gradle build`, `gradle bootRun`

## Features
* Implemented shopping cart; `Cart`, `CartController`
* Implemented browsing for store; `BrowsingController`
* Implemented authentication; `WebSecurityConfig`
* Implemented administation features; `AdminController`
* Implemented buying by one click; `CartController`
* Implemented rating products using like/dislike `LikedCart`
* Implemented searching products by text in name and description of products. `ProductService`

## Getting started

### Demo version

**Demo** of the online store of dj equipment is available at [http://dj-shop.herokuapp.com/](http://dj-shop.herokuapp.com/)

> Demo version isn't use the full database!

> If you need to see `shop` with full database, just setup `shop` at your local machine

**For access to admin features**

```
login: admin
password: admin
```
### Building and running
This project use Gradle and Spring Boot Gradle Plugin for building and running the application.

#### Prerequisites
If you need to see this application with some data, just import 
[store.dump](/src/main/resources/store.dump) to your local `store` 
(you can rename database with `application.properties`) PostgreSQL database.
To do this you can use `pg_backups`, `psql`.

After these steps you can build and run the app with prepared database.

#### Installing
Just write the next command: `gradle build`

This command will create a builded `.jar`

## Deployment
Just write the next command: `gradle bootRun`

Then you can get access to the application through the following URL:
[http://localhost:8080/](http://localhost:8080/)

## Modules 
* [app-data](/app-data) - **Data layer** for another modules and root project. Also in this module it was implemented [shopping cart](/app-data/src/main/java/io/github/tkaczenko/session/Cart.java), that based on HashMap. It used JPA, Spring Data ([dao](/app-data/src/main/java/io/github/tkaczenko/dao) contains implementation with `JDBCTemplate`, [repository](/app-data/src/main/java/io/github/tkaczenko/repository) contains implementation with `CrudRepository`, `PagingAndSortingRepository`.
* [app-scraper](/app-scraper) - **Scraping module** which use [app-data](/app-data) to save parsed data to database. It used Jsoup, Selenium WebDriver.
* [root-project](/src) - It implements a bussiness logic (`service`) of this application with validation and web layer (`controller` and `webapp` files).

## UML diagrams
For better understanding how `shop` works, you can look at UML diagrams below
* [Use case diagram](/docs/use-case-diagram.png)
* [Entity-relationship diagram](/docs/entity-relationship-model.png)
* [Activity diagram of purchase](/docs/activity-diagram-of-purchase.png)
* [Sequence diagram](/docs/sequence-diagram.png)
* [Communication diagram](/docs/communication-diagram.png)
* [Logical data model](/docs/logical-data-model.png)
* [Physical data model](/docs/physical-data-model.png)
* [Deployment diagram](/docs/deployment-diagram.png)
* [Class diagram for "View catalog" use case](/docs/class-diagram-view-catalog.png)
* [Class diagram 1](/docs/class-diagram-one.png), [Class diagram 2](/docs/class-diagram-two.png), [Class diagram 3](/docs/class-diagram-three.png)

## Authors
* **Andrii Tkachenko** - [GitHub](https://github.com/tkaczenko)

## License
MIT License

Copyright (c) 2017 Andrii Tkachenko

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
