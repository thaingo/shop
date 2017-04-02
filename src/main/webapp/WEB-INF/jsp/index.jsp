<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html ⚡ lang="en"
      xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <script async src="https://cdn.ampproject.org/v0.js"></script>

    <script async custom-element="amp-sidebar" src="https://cdn.ampproject.org/v0/amp-sidebar-0.1.js"></script>
    <script async custom-element="amp-fit-text" src="https://cdn.ampproject.org/v0/amp-fit-text-0.1.js"></script>

    <meta name="viewport" content="width=device-width,minimum-scale=1,initial-scale=1">
    <meta name="description" content="Интернет-магазин DJ оборудования"/>
    <meta name="author" content="tkaczenko"/>

    <title>Online store</title>
    <link rel="canonical" href="index.jsp">
    <link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico"/>" type="image/x-icon"/>
    <link rel="icon" href="/resources/images/favicon.ico" type="image/x-icon">

    <style amp-custom>
        @import 'https://fonts.googleapis.com/css?family=Roboto';

        body {
            font-family: 'Roboto', sans-serif;
        }

        form.amp-form-submit-success [submit-success],
        form.amp-form-submit-error [submit-error] {
            margin-top: 16px;
        }

        form.amp-form-submit-success [submit-success] {
            color: green;
        }

        form.amp-form-submit-error [submit-error] {
            color: red;
        }

        form.amp-form-submit-success.hide-inputs > input {
            display: none;
        }

        amp-sidebar {
            width: 250px;
            padding-right: 10px;
        }

        .amp-sidebar-image {
            line-height: 100px;
            vertical-align: middle;
        }

        .amp-close-image {
            top: 15px;
            left: 225px;
            cursor: pointer;
        }

        amp-sidebar li {
            margin-bottom: 20px;
            list-style: none;
        }

        .menu-container {
            border-top: 5px solid #444;
            background-color: #fff;
            min-height: 50px;
            position: fixed;
            top: 0;
            transition: top 0.2s ease-in-out;
            width: 100%;
            z-index: 100;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            text-align: center;
        }

        #main-menu > ul > li > a:hover, #main-menu > ul > li:hover > a {
            color: #3d3d3d;
        }

        #main-menu > ul > li > a {
            font-size: 14px;
            padding: 0px 20px;
            color: #bababa;
            text-transform: uppercase;
            -webkit-transition: all 0.2s ease-out;
            -moz-transition: all 0.2s ease-out;
            -o-transition: all 0.2s ease-out;
            -ms-transition: all 0.2s ease-out;
            transition: all 0.2s ease-out;
        }

        div.logo {
            width: 128px;
            display: inline-block;
            margin-top: 10px;
            margin-bottom: 5px;
        }

        #main-menu {
            text-align: center;
        }

        #main-menu ul {
            position: relative;
            padding: 0;
        }

        #main-menu li {
            list-style-type: none;
            display: inline-block;
        }

        #main-menu li a {
            text-decoration: none;
        }

        #container {
            background: #fafafa;
            text-align: center;
            padding: 25px 165px;
            max-width: 1170px;
            margin: 110px auto 50px auto;
            box-sizing: border-box;
        }

        #container p {
            color: #a1a1a1;
        }

        h1 {
            font-weight: 300;
            color: #161616;
            font-size: 30px;
            line-height: 35px;
            margin-bottom: 15px;
        }

        #container.roboto, #main-menu.roboto, .menu-container.class li a {
            font-family: 'Roboto', sans-serif;
        }

        h3.section-title {
            font-weight: 300;
            color: #161616;
            font-size: 26px;
            line-height: 30px;
        }

        .mbottom5 {
            margin-bottom: 5px;
        }

        @media screen and (max-width: 480px) {
            #container {
                margin-top: 130px;
                padding: 25px;
            }

            h1 {
                font-size: 23px;
            }
        }

        footer {
            border-top: solid 3px #444;
        }

        .footer, .footer ul, .footer ol {
            margin: 0;
            padding: 0;
            list-style: none;
        }

        li.footer {
            display: inline;
        }

        li.footer a {
            margin-right: 6px;
            margin-left: 6px;
        }

        div.copyright {
            text-align: center;
        }

        .copyright {
            font-size: 11px;
            color: #575656;
            margin-bottom: 0;
        }

        li.footer a {
            margin-left: 6px;
        }

        a {
            color: #3d3d3d;
            font-weight: 500;
            text-decoration: none;
        }

        .footer p {
            font-weight: lighter;
        }

        .footer {
            font-family: 'Roboto', sans-serif;
            font-size: 15px;
            background: #E6E6E6;
            padding: 25px 0;
        }

        .col-left {
            width: auto;
            display: inline-block;
            vertical-align: top;
        }

        .col-right {
            max-width: 80px;
            display: inline-block;;
            vertical-align: top;
            float: right;
        }

        .bottom {
            text-align: left;
            overflow: overlay;
            margin-bottom: 20px;
            padding: 0 10%;
            overflow: hidden;
        }

        .top {
            margin-bottom: 20px;
            text-align: left;
            padding: 0 10%;
        }

        .content {
            margin-top: 110px;
        }

        dropdown-ul {
            position: relative;
            padding: 0;
        }

        li.dropdown-ul {
            list-style-type: none;
            display: inline-block;
        }

        li.dropdown-ul a, .dropdown-btn {
            display: inline-block;
            padding: 18px 22px;
            text-decoration: none;
            font-family: 'Roboto', sans-serif;
        }

        li.dropdown {
            display: inline-block;
        }

        .dropdown-menu {
            display: none;
            position: absolute;
            background-color: #E6E6E6;
            min-width: 160px;
            box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.1);
            z-index: 1000;
        }

        .dropdown-menu a {
            color: grey;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
            text-align: left;
        }

        .dropdown-menu a:hover {
            color: black;
        }

        .dropdown:hover .dropdown-menu {
            display: block;
        }

        .relative-section {
            max-width: 2000px;
            margin: 0 auto;
            padding: 6% 2%;
        }

        .relative-grid {
            margin: 20px 0 0 0;
            padding: 0;
            list-style: none;
            display: block;
            text-align: center;
            width: 100%;
        }

        .relative-grid:after {
            clear: both;
        }

        .relative-grid:after, .relative-grid:before {
            content: '';
            display: table;
        }

        .relative-grid li {
            width: 328px;
            height: 328px;
            display: inline-block;
            margin: 20px;
        }

        .product {
            width: 100%;
            height: 100%;
            position: relative;
            cursor: pointer;
            border-radius: 10px;
        }

        .relative-box {
            width: 100%;
            height: 100%;
            position: relative;
            cursor: pointer;
            border-radius: 10px;
            -webkit-transition: 0.3s ease-in-out,
            -webkit-transform 0.3s ease-in-out;
            -moz-transition: 0.3s ease-in-out,
            -moz-transform 0.3s ease-in-out;
            transition: all 0.3s ease-in-out,
            transform 0.3s ease-in-out,;
        }

        .relative-box:hover {
            transform: scale(1.05);
        }

        .info {
            position: absolute;
            width: inherit;
            height: inherit;
        }

        .info h3 {
            font-family: 'Roboto', cursive;
            font-weight: 400;
            color: black;
            font-size: 42px;
            margin: 0 30px;
            padding: 100px 0 0 0;
            line-height: 1.5;
        }

        .info p {
            font-family: 'Roboto', sans-serif;
            color: black;
            padding: 4px 5px;
            margin: 0 30px;
            font-size: 14px;
            line-height: 2;
        }

        .price {
            position: absolute;
            bottom: 0;
            left: 0;
        }

        .actions {
            position: absolute;
            bottom: 0;
            right: 0;
        }

        .title {
            font-family: 'Roboto', sans-serif;
            color: black;
            font-size: 14px;
            line-height: 1;
        }

        .title a {
            font-family: 'Roboto', cursive;
            font-size: 20px;
            display: inline;
            float: center;
            padding: 10px;
            color: black;
            text-decoration: none;
        }

        .title a:hover {
            text-decoration: underline;
            color: #ff7bac;
        }

        input[type=search] {
            width: 130px;
            box-sizing: border-box;
            border: 2px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
            background-color: white;
            background-image: url('/resources/images/searchicon.png');
            background-size: 36px;
            background-position: 10px 10px;
            background-repeat: no-repeat;
            padding: 12px 20px 12px 40px;
            -webkit-transition: width 0.4s ease-in-out;
            transition: width 0.4s ease-in-out;
        }

        input[type=search]:focus {
            width: 100%;
        }
    </style>
    <style amp-boilerplate>body {
        -webkit-animation: -amp-start 8s steps(1, end) 0s 1 normal both;
        -moz-animation: -amp-start 8s steps(1, end) 0s 1 normal both;
        -ms-animation: -amp-start 8s steps(1, end) 0s 1 normal both;
        animation: -amp-start 8s steps(1, end) 0s 1 normal both
    }

    @-webkit-keyframes -amp-start {
        from {
            visibility: hidden
        }
        to {
            visibility: visible
        }
    }

    @-moz-keyframes -amp-start {
        from {
            visibility: hidden
        }
        to {
            visibility: visible
        }
    }

    @-ms-keyframes -amp-start {
        from {
            visibility: hidden
        }
        to {
            visibility: visible
        }
    }

    @-o-keyframes -amp-start {
        from {
            visibility: hidden
        }
        to {
            visibility: visible
        }
    }

    @keyframes -amp-start {
        from {
            visibility: hidden
        }
        to {
            visibility: visible
        }
    }</style>
    <noscript>
        <style amp-boilerplate>body {
            -webkit-animation: none;
            -moz-animation: none;
            -ms-animation: none;
            animation: none
        }</style>
    </noscript>
</head>
<body>
<jsp:include page="_header.jsp"/>

<div class="content">
    <jsp:include page="_categories-menu.jsp"/>

    <div class="relative-section">
        <jsp:include page="_products.jsp"/>
    </div>

</div>
<jsp:include page="_footer.jsp"/>
</body>
</html>
