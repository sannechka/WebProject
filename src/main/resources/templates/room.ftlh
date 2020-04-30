<#import  "parts/Common.ftlh" as c>
<@c.page>
    My Rooms
    <div> <a  class="btn btn-info" href="createroom">Create new room</a></div>
    <ul class="list-group">
        <#list rooms as room>
            <li class="list-group-item">
                <a href="/room/${room.getName()}">${room.getName()}</a>
            </li>
        </#list>
    </ul>
</@c.page>



