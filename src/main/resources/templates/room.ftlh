<#import  "parts/Common.ftlh" as c>
    <@c.page>
        My Rooms
        <table>
            <thead>
            <tr>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <#list rooms as room>
                <tr>
                    <td>${room.name}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </@c.page>



