<#import "parts/Common.ftlh" as c>
<@c.page>

    <link href="/static/chat.css" rel="stylesheet" type="text/css">
    <div class="container">
        <div class="row">
            <div class="conversation-wrap col-lg-3">
                <div class="media-title">
                        <h5 class="media-heading">List of users:</h5>
                </div>
                <#list users as user>
                    <div class="media conversation">
                        <a class="pull-left" href="#">
                            <#if user.filename??>
                                <img class="media-object   " data-src="holder.js/64x64" alt="64x64"
                                     src="/img/${user.filename}">
                            <#else>
                                <img class="media-object " data-src="holder.js/64x64" alt="64x64"
                                     src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAACqUlEQVR4Xu2Y60tiURTFl48STFJMwkQjUTDtixq+Av93P6iBJFTgg1JL8QWBGT4QfDX7gDIyNE3nEBO6D0Rh9+5z9rprr19dTa/XW2KHl4YFYAfwCHAG7HAGgkOQKcAUYAowBZgCO6wAY5AxyBhkDDIGdxgC/M8QY5AxyBhkDDIGGYM7rIAyBgeDAYrFIkajEYxGIwKBAA4PDzckpd+322243W54PJ5P5f6Omh9tqiTAfD5HNpuFVqvFyckJms0m9vf3EY/H1/u9vb0hn89jsVj8kwDfUfNviisJ8PLygru7O4TDYVgsFtDh9Xo9NBrNes9cLgeTybThgKenJ1SrVXGf1WoVDup2u4jFYhiPx1I1P7XVBxcoCVCr1UBfTqcTrVYLe3t7OD8/x/HxsdiOPqNGo9Eo0un02gHkBhJmuVzC7/fj5uYGXq8XZ2dnop5Mzf8iwMPDAxqNBmw2GxwOBx4fHzGdTpFMJkVzNB7UGAmSSqU2RoDmnETQ6XQiOyKRiHCOSk0ZEZQcUKlU8Pz8LA5vNptRr9eFCJQBFHq//szG5eWlGA1ywOnpqQhBapoWPfl+vw+fzweXyyU+U635VRGUBOh0OigUCggGg8IFK/teXV3h/v4ew+Hwj/OQU4gUq/w4ODgQrkkkEmKEVGp+tXm6XkkAOngmk4HBYBAjQA6gEKRmyOL05GnR99vbW9jtdjEGdP319bUIR8oA+pnG5OLiQoghU5OElFlKAtCGr6+vKJfLmEwm64aosd/XbDbbyIBSqSSeNKU+HXzlnFAohKOjI6maMs0rO0B20590n7IDflIzMmdhAfiNEL8R4jdC/EZIJj235R6mAFOAKcAUYApsS6LL9MEUYAowBZgCTAGZ9NyWe5gCTAGmAFOAKbAtiS7TB1Ng1ynwDkxRe58vH3FfAAAAAElFTkSuQmCC">
                            </#if>
                        </a>
                        <div class="media-body">
                            <h5 class="media-heading">${user.getUsername()}</h5>
                        </div>
                    </div>
                </#list>
            </div>
            <div class="message-wrap col-lg-8">

                <div class="msg-wrap">
                    <div class="media-title">
                        <h5 class="media-heading">${room.getName()}</h5>
                    </div>
                    <#list messages as message>
                        <div class=" media msg ">
                            <#if message.getAuthor().filename??>
                                <img class="media-object rounded-circle" data-src="holder.js/64x64" alt="64x64"
                                     src="/img/${ message.getAuthor().filename}">
                            <#else>
                                <img class="media-object rounded-circle" data-src="holder.js/64x64" alt="64x64"
                                     src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAACqUlEQVR4Xu2Y60tiURTFl48STFJMwkQjUTDtixq+Av93P6iBJFTgg1JL8QWBGT4QfDX7gDIyNE3nEBO6D0Rh9+5z9rprr19dTa/XW2KHl4YFYAfwCHAG7HAGgkOQKcAUYAowBZgCO6wAY5AxyBhkDDIGdxgC/M8QY5AxyBhkDDIGGYM7rIAyBgeDAYrFIkajEYxGIwKBAA4PDzckpd+322243W54PJ5P5f6Omh9tqiTAfD5HNpuFVqvFyckJms0m9vf3EY/H1/u9vb0hn89jsVj8kwDfUfNviisJ8PLygru7O4TDYVgsFtDh9Xo9NBrNes9cLgeTybThgKenJ1SrVXGf1WoVDup2u4jFYhiPx1I1P7XVBxcoCVCr1UBfTqcTrVYLe3t7OD8/x/HxsdiOPqNGo9Eo0un02gHkBhJmuVzC7/fj5uYGXq8XZ2dnop5Mzf8iwMPDAxqNBmw2GxwOBx4fHzGdTpFMJkVzNB7UGAmSSqU2RoDmnETQ6XQiOyKRiHCOSk0ZEZQcUKlU8Pz8LA5vNptRr9eFCJQBFHq//szG5eWlGA1ywOnpqQhBapoWPfl+vw+fzweXyyU+U635VRGUBOh0OigUCggGg8IFK/teXV3h/v4ew+Hwj/OQU4gUq/w4ODgQrkkkEmKEVGp+tXm6XkkAOngmk4HBYBAjQA6gEKRmyOL05GnR99vbW9jtdjEGdP319bUIR8oA+pnG5OLiQoghU5OElFlKAtCGr6+vKJfLmEwm64aosd/XbDbbyIBSqSSeNKU+HXzlnFAohKOjI6maMs0rO0B20590n7IDflIzMmdhAfiNEL8R4jdC/EZIJj235R6mAFOAKcAUYApsS6LL9MEUYAowBZgCTAGZ9NyWe5gCTAGmAFOAKbAtiS7TB1Ng1ynwDkxRe58vH3FfAAAAAElFTkSuQmCC">

                            </#if>
                            <div class=" p-1 media-body">
                                <h5 class="media-heading">${message.authorName}
                                    <small class=" r-4 pull-right time"> ${message.getTime()} </small>
                                </h5>
                                <h7 class="col-lg-10 media-obj">  ${message.text}</h7>
                            </div>
                        </div>
                        <div>
                            <#if message.filename??>
                                <img class="file-object" src="/img/${message.filename}">
                            </#if>
                        </div>
                    </#list>
                </div>
                <form method="post" action="/privateChat" enctype="multipart/form-data">
                    <div class="send-wrap ">

                        <input type="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                               value="<#if message??>${message.text}</#if>" name="text"
                               placeholder="Write a message"/>
                        <#if textError??>
                            <div class="invalid-feedback">
                                ${textError}
                            </div>
                        </#if>
                        <div class="btn-panel">
                            <input type="file" name="file">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <input type="hidden" value="${room.getId()}" name='roomId'>
                            <button class="btn btn-primary" type="submit"> Send</button>
                            <a class="btn btn-primary " href="/room">Back</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

</@c.page>