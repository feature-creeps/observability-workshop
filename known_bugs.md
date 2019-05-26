### known Bugs

### to be fixed
* traceId is not always propagated (noticed with orchestrator)

#### to be refined
* imagerotate slightly moves image to the right when rotating. very noticeable with higher degrees number.
* imagegrayscale cannot handle the images it creates itself: grayscale + persist -> grayscale again -> fail
* Large numbers of images in Mongo need a long time to retrieve, exceeding the nginx proxy time limit of 30s. For example, 

        curl  -vv http://3.8.172.78/proxy/imageholder/api/images
        *   Trying 3.8.172.78...
        * TCP_NODELAY set
        * Connected to 3.8.172.78 (3.8.172.78) port 80 (#0)
        > GET /proxy/imageholder/api/images HTTP/1.1
        > Host: 3.8.172.78
        > User-Agent: curl/7.54.0
        > Accept: */*
        >
        < HTTP/1.1 504 Gateway Time-out
        < Server: nginx/1.15.8
        < Date: Sun, 26 May 2019 09:48:49 GMT
        < Content-Type: text/html
        < Content-Length: 167
        < Connection: keep-alive
        <
        <html>
        <head><title>504 Gateway Time-out</title></head>
        <body>
        <center><h1>504 Gateway Time-out</h1></center>
        <hr><center>nginx/1.15.8</center>
        </body>
        </html>
        * Connection #0 to host 3.8.172.78 left intact

#### embraced

* rotate cannot handle jpg
* image size larger than 10mb fails

### cannot reproduce

### fixed
* imageresize changes background color to black on some png's (suppose some alpha/transparency layer thingy)
