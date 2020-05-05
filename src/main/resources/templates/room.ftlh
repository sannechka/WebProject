<#import  "parts/Common.ftlh" as c>
<@c.page>
    <h3 class=" p-1 bg-primary text-white"> List of rooms</h3>

    <div> <a  class=" p-1  btn btn-info" href="createroom">Create new room</a></div>
    <ul class=" p-1 list-group">
        <#list rooms as room>
            <li class="list-group-item">
                <div class="media-body">
                    <a href="/room/${room.getName()}">${room.getName()} </a>
                </div>
            </li>
        </#list>
    </ul>
</@c.page>



