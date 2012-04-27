<!-- freemarker macros have to be imported into a namespace.  We strongly
recommend sticking to 'spring' -->
<#import "spring.ftl" as spring />

<html>

<head>
    <title><@spring.message "weather.title"/></title>
</head>

<body>

<h1><@spring.message "weather.title"/></h1>

<#if weatherList >

<#else >

<p><@spring.message "weather.nodata"/></p>

</#if>

</body>

</html>