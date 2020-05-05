<#import  "parts/Common.ftlh" as c>
<@c.page>
    <h3 class=" p-3 bg-primary text-white">List Of users
        <small>(control)</small>
    </h3>

    <table class="table">
        <thead class="thead-inverse">
        <tr>
            <th>Status</th>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <th scope="row"> ${user.getBlock()}</th>
                <td>${user.username}</td>
                <td><#list  user.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${user.id}">edit</a></td>
            </tr>

        </#list>
        </tbody>
    </table>
</@c.page>