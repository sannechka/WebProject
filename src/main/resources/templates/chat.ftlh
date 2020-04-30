<#import  "parts/Common.ftlh" as c>
<@c.page>

    <div> <a  class="btn btn-info" href="room">My rooms </a></div>

    <div>
        <form method="post"  action="/chat" enctype="multipart/form-data">
            <input type="text"  class="form-control ${(textError??)?string('is-invalid','')}"
            value="<#if message??>${message.text}</#if>"
            name="text" placeholder="Введите сообщение"/>
    <#if textError??>
        <div class="invalid-feedback">
            ${textError}
        </div>
    </#if>
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