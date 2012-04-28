<#import "spring.ftl" as spring />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <title><@spring.message "weather.title"/></title>
</head>

<body>

<a href="/weather/data">
    <img src="/resources/icon.png" alt="weather">
</a>

<h1><@spring.message "weather.title"/></h1>

<#if weatherList?size != 0 >

<table cellpadding="5" cellspacing="5" frame="border">

    <tr>
        <th><@spring.message "weather.time"/></th>
        <th><@spring.message "weather.conditions"/></th>
        <th><@spring.message "weather.temperature"/></th>
        <th><@spring.message "weather.city"/></th>
        <th><@spring.message "weather.provider"/></th>
    </tr>

    <#list weatherList as weatherData >

        <tr>
            <td>${weatherData.observationTimeAsString}</td>
            <td align="right">${weatherData.conditions}</td>
            <td align="center">${weatherData.temperature}</td>
            <td>${weatherData.cityData.name}</td>
            <td>${weatherData.cityData.weatherProvider.name}</td>
        </tr>

    </#list>

</table>

<#else >

<p><@spring.message "weather.nodata"/></p>

</#if>

</body>

</html>