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

<#if weatherData?size != 0 >

<form action="data/city" method="POST">
    <@spring.message "weather.select.city"/>&nbsp;
    <@spring.formSingleSelect "weatherFormData.cityDataId", cityData, "" />
    <input type="submit" value="<@spring.message "weather.select"/>">
</form>

<p/>

<table cellpadding="5" cellspacing="5" frame="border">

    <tr>
        <th><@spring.message "weather.time"/></th>
        <th><@spring.message "weather.conditions"/></th>
        <th><@spring.message "weather.temperature"/></th>
        <th><@spring.message "weather.city"/></th>
        <th><@spring.message "weather.provider"/></th>
    </tr>

    <#list weatherData as weatherItem >

        <tr>
            <td>${weatherItem.observationTimeAsString}</td>
            <td align="right">${weatherItem.conditions}</td>
            <td align="center">${weatherItem.temperature}</td>
            <td>${weatherItem.cityData.name}</td>
            <td>${weatherItem.cityData.weatherProvider.name}</td>
        </tr>

    </#list>

</table>

<#else >

<p><@spring.message "weather.nodata"/></p>

</#if>

</body>

</html>