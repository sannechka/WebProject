<#import  "parts/Common.ftlh" as c>
<@c.page>
<link href="/static/chat.css" rel="stylesheet" type="text/css">
    <form action="/createRoom" method="post" xmlns="http://www.w3.org/1999/html">
        <h3 class=" p-3 bg-primary text-white"> Create new room </h3>
        <div class="container">
            <div class="row">
                <div class="conversation-wrap col-lg-3">
                    <div class="media-title">
                        <h5 class="media-heading">List of users:</h5>
                    </div>
                    <ul class="list-group">
                        <#list users as user>
                            <div class="media conversation">
                                <div>
                                    <label>${user.getUsername()} <input type="checkbox"
                                                                        name="${user.getUsername()}">
                                </div>
                            </div>
                        </#list>
                    </ul>
                </div>
                <div class=" col-lg-8">
                    <p class="text-primary  m-2 ">Name of room: </p>
                    <input type="text" name="name"
                           class=" m-2 form-control ${(nameError??)?string('is-invalid', '')}"
                           placeholder="Room name"/>
                    <#if nameError??>
                        <div class="invalid-feedback">
                            ${nameError}
                        </div>
                    </#if>
                    <button class="m-2 btn btn-info" type="submit">Create</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </div>
            </div>
        </div>

    </form>

</@c.page>+