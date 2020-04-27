<#import  "parts/Common.ftlh" as c>
<#import  "parts/Loginn.ftlh" as l>
<c.page xmlns="http://www.w3.org/1999/html">
    <@l.logout/>
    <span> <a href="/user"> List of users</a></span>
    <div>
        <form method="post" enctype="multipart/form-data">
            <input type="text" name="text" placeholder="Введите сообщение"/>
            <input type="file" name="file">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit"> Добавить</button>

            <#list messages as message>
                <div>
                    <strong>${message.authorName}</strong>
                    <span> ${message.text}</span>
                </div>
                <div>
                    <#if message.filename??>
                        <img src="/img/${message.filename}">
                    </#if>
                </div>
            <#else>
                No messages
            </#list>
</c.page>