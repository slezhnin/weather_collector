<#import "spring.ftl" as spring />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title><@spring.message "weather.title"/></title>
</head>

<body>

<img src="/resources/icon.png" alt="weather">

<h1><@spring.message "weather.title"/></h1>

<#if weatherList?size != 0 >

<#list weatherList as weatherData >

</#list>

<#else >

<p><@spring.message "weather.nodata"/></p>

</#if>

</body>

</html>