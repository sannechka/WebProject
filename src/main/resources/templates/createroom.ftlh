
<#import  "parts/Common.ftlh" as c>
<@c.page>
 <form action="/createroom"  method="post" xmlns="http://www.w3.org/1999/html">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Name of room :</label>
        <div class="col-sm-6">
            <input type="text" name="roomname" class="form-control" placeholder="Room name" />
        </div>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary" type="submit">Create</button>

    <ul class="list-group">
        <#list users as user>
            <div>
                <label><input type="checkbox" name="${user.getUsername()}" }>${user.getUsername()}</label>
            </div>
        </#list>

    </ul>
 </form>

</@c.page>