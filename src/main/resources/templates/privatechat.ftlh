<#import  "parts/Common.ftlh" as c>
<@c.page>

    <div>
    ${room.getName()}
    <form method="post"  action="/privatechat" enctype="multipart/form-data">
    <input type="text"  class="form-control" name="text" placeholder="Введите сообщение"/>
    <input type="file" name="file">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="hidden" value="${room.getName()}" name = 'roomName'>
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