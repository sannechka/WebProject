<#import  "parts/Common.ftlh" as c>
<#import  "parts/Loginn.ftlh" as l>
<@c.page>
    <link href="static/main.css" rel="stylesheet" type="text/css">
    <div class = "container">
    <h2 class=" p-3 bg-primary text-white"> Add new user
        <small>(registration)</small>
    </h2>
    <@l.login "/registration" true/>
    </div>
</@c.page>