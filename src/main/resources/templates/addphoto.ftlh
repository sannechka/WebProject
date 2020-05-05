<#import  "parts/Common.ftlh" as c>

<@c.page>
    <form action="/addphoto" method="post" enctype="multipart/form-data">


    <div class ="dws-input">
        <p>Load your new photo <input  type="file" name="file" placeholder="..." required></p>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit"> Добавить</button>
    </div>
    <form>
</@c.page>