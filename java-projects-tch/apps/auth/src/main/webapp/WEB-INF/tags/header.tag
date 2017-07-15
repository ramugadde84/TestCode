<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <sec:csrfMetaTags />
    <title>TCH Wallet</title>
    <link rel="stylesheet" href="${staticContextPath}/css/lib/jquery-mobile/tch.miv.auth.custom.css">
    <link rel="stylesheet" href="${staticContextPath}/css/lib/jquery-mobile/jquery.mobile.icons.min.css">
    <link rel="stylesheet" href="${staticContextPath}/css/lib/jquery-mobile/jquery.mobile.structure-1.4.2.css">
    <link rel="stylesheet" href="${staticContextPath}/css/app/auth.css">
</head>
<body id="authApp">