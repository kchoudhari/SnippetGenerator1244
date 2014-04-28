<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="mystyle.css">
</head>
<body>
    <input type="text" name="msg1" id="msg1"/>
    <button id="Submit" onClick="push()">Start</button>
        <br/>
        <div  id="recentPosts"></div>


        <script type="text/javascript">
        function push() {
            document.getElementById('recentPosts').innerHTML="";
            var x = document.getElementById("msg1").value;
            var request=new XMLHttpRequest();
             request.onreadystatechange=function()
              {
              if (request.readyState==4 && request.status==200)
                {
                var node=  document.createElement("p");
                node.innerHTML= request.responseText;

                document.getElementById('recentPosts').appendChild(node);
                }
              }
                         request.open("GET",
                          "http://localhost:8080/SnippetGenerator/SnippetServlet?msg1=".concat(x) ,
                          true);

            request.send(x);

        }
        </script>

</body>
</html>