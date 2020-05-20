<#import  "parts/Common.ftlh" as c>
<@c.page>
    <h3 class=" p-3 bg-primary text-white"> Edit user{${user.username}}
        <small>(control)</small>
    </h3>
    <form action="/user" method="post" xmlns="http://www.w3.org/1999/html">

        <div class=" p-2 form-group row">
            <label class=" text-secondary col-sm-3 col-form-label">Edit username:</label>

            <input type="text" name="username"
                   class=" col-sm-3 form-control"
                   value="${user.username}"/>
            <#if usernameError??>
                <label class=" text-secondary col-sm-3 col-form-label">   ${usernameError}</label>
            </#if>
        </div>

        <div class=" p-2 m-1 form-group row">
            <#if user.isActive()>
                <label class="text-secondary"><input type="checkbox" name="block">Block user</label>
            <#else>
                <label class="text-secondary"><input type="checkbox" name="unblock">unBlock user</label>
            </#if>

        </div>
        <div>
            <button class="btn btn-primary" type="submit">Save</button>
        </div>
        <input type="hidden" value="${user.id}" name='userId'/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>


    </form>
</@c.page>