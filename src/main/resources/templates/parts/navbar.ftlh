<#include "Security.ftlh">
<#import "Loginn.ftlh" as l>
<link href="/static/menu.css" rel="stylesheet" type="text/css">
<nav class=" navbar navbar-expand-lg ">
    <a class="navbar-brand " href="/">Chat</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">

        <ul class="navbar-nav mr-auto">

            <#if user??>
                <li class="nav-item  ">
                    <a class="nav-link" href="/room">My rooms</a>
                </li>
            </#if>
            <#if user??>
                <li class="nav-item ">
                    <a class="nav-link" href="/addPhoto">Photo</a>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item ">
                    <a class="nav-link" href="/user">User list</a>
                </li>
            </#if>

        </ul>


        <div class="navbar-text mr-3"><#if user??>${name}<#else> Please, Login </#if></div>
        <@l.logout />
    </div>
</nav>