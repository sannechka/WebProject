<#import  "parts/Common.ftlh" as c>
<@c.page>
    <link href="static/main.css" rel="stylesheet" type="text/css">
    <div class = "container">
    <h3 class=" p-3 bg-primary text-white"> List of rooms</h3>
    <div> <a  class=" p-1  btn btn-info" href="createRoom">Create new room</a></div>
    <ul class=" p-2 list-group">
        <form>
        <#list rooms as room>
            <li class=" p-2 list-group-item">
                <div class="p-2 media-body">
                    <a  href="/room/${room.getId()}">${room.getName()}</a>
                </div>
            </li>
        </#list>
        </form>
    </ul>
    </div>
</@c.page>



