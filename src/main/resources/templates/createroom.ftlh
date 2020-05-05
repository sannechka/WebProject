<#import  "parts/Common.ftlh" as c>
<@c.page>
    <form action="/createroom" method="post" xmlns="http://www.w3.org/1999/html">
        <div>
            <ul class="list-group">
                <#list users as user>
                    <div>

                        <label><input type="checkbox" name="${user.getUsername()}" }>${user.getUsername()}</label>
                    </div>
                </#list>
            </ul>
        </div>
        <button class="btn btn-primary" type="submit">Create</button>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Name of room :</label>
            <div class="col-sm-6">
                <input type="text" name="name" class="form-control ${(nameError??)?string('is-invalid', '')}" placeholder="Room name" />
                <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
                </#if>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>



    </form>

</@c.page>