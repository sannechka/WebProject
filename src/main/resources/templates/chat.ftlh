<#import  "parts/Common.ftlh" as c>
<@c.page>

    <div> <a href="/room">My rooms </a></div>

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
</@c.page>