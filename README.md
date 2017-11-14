# Online shop "Интернет-магазин DJ"
Online store of dj equipment. `shop` is written in Java 8 with Spring Framework (IoC, Boot, MVC, Data, Security), Hibernate/JPA, Hibernate Search, Hibernate Validator, PostgreSQL and JSP(JSTL + Bootstrap3). 

In testing it was used Junit4, DBUnit and Selenium WebDriver.

**Demo** of the online store of dj equipment is available at [http://dj-shop.herokuapp.com/](http://dj-shop.herokuapp.com/)

*Demo version isn't use the full database!*

*If you need to see `shop` with full database, just setup `shop` at your local machine*

This app was created just for fun and may have some bugs. If you see some, write to me.

**For access to admin features**

```
login: admin
password: admin
```

## Features
* Implemented shopping cart; `Cart`, `CartController`
* Implemented browsing for store; `BrowsingController`
* Implemented authentication; `WebSecurityConfig`
* Implemented administation features; `AdminController`
* Implemented buying by one click; `CartController`
* Implemented rating products using like/dislike `LikedCart`
* Implemented searching products by text in name and description of products. `ProductService`

## Modules 
* [app-data](https://github.com/tkaczenko/shop/tree/master/app-data) - Data layer for another modules and root project. Also in this module it was implemented [shopping cart](https://github.com/tkaczenko/shop/blob/master/app-data/src/main/java/io/github/tkaczenko/session/Cart.java), that based on HashMap. It used JPA, Spring Data ([dao](https://github.com/tkaczenko/shop/tree/master/app-data/src/main/java/io/github/tkaczenko/dao) contains implementation with JDBCTemplate, [repository](https://github.com/tkaczenko/shop/tree/master/app-data/src/main/java/io/github/tkaczenko/repository) contains implementation with CrudRepository, PagingAndSortingRepository.
* [app-scraper](https://github.com/tkaczenko/shop/tree/master/app-scraper) - Scraping module which use [app-data](https://github.com/tkaczenko/shop/tree/master/app-data) to save parsed data to database. It used Jsoup, Selenium WebDriver.
* [root-project](https://github.com/tkaczenko/shop/tree/master/src) - It implements a bussiness logic (`service`) of this application with validation and web layer (`controller` and `webapp` files).

## Building and running
This project use Gradle and Spring Boot Gradle Plugin for building and running the application.

### Database preparing
If you need to see this application with some data, just import 
[store.dump](https://github.com/tkaczenko/shop/blob/master/src/main/resources/store.dump) to your local `store` 
(you can rename database with `application.properties`) PostgreSQL database.
To do this you can use `pg_backups`, `psql`.

After these steps you can build and run the app with prepared database.

### Build

`gradle build`

### Run
Just write the next command:

`gradle bootRun`

Then you can get access to the application through the following URL:

[http://localhost:8080/](http://localhost:8080/)
