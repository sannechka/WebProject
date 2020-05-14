<#import  "parts/Common.ftlh" as c>
<#import  "parts/Loginn.ftlh" as l>
<@c.page>

    <div class = "container">
    <h2 class=" p-3 bg-primary text-white"> Please, Login!
        <small>(or registration)</small>
    </h2>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <@l.login "/login" false />
    </div>
</@c.page>