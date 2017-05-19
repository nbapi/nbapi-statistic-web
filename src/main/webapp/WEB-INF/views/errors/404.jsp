<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>404 error page</title>

<link href="<c:url value="/resources/css/bootstrap.min.css"></c:url>"
	rel="stylesheet" type="text/css">
<link
	href="<c:url value="/resources/css/font-awesome/css/font-awesome.min.css"></c:url>"
	rel="stylesheet" type="text/css">

<link href="<c:url value="/resources/css/themestyles.css"></c:url>"
	rel="stylesheet" type="text/css">



<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,600,700,300|Titillium+Web:200,300,400'
	rel='stylesheet' type='text/css'>
<!--[if lt IE 9]>
			<script src="../js/html5shiv.js"></script>
			<script src="../js/respond.min.js"></script>
	<![endif]-->
<script type="text/javascript">
/* <![CDATA[ */
var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-49262924-2']);
_gaq.push(['_trackPageview']);

(function() {
var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
})();

(function(b){(function(a){"__CF"in b&&"DJS"in b.__CF?b.__CF.DJS.push(a):"addEventListener"in b?b.addEventListener("load",a,!1):b.attachEvent("onload",a)})(function(){"FB"in b&&"Event"in FB&&"subscribe"in FB.Event&&(FB.Event.subscribe("edge.create",function(a){_gaq.push(["_trackSocial","facebook","like",a])}),FB.Event.subscribe("edge.remove",function(a){_gaq.push(["_trackSocial","facebook","unlike",a])}),FB.Event.subscribe("message.send",function(a){_gaq.push(["_trackSocial","facebook","send",a])}));"twttr"in b&&"events"in twttr&&"bind"in twttr.events&&twttr.events.bind("tweet",function(a){if(a){var b;if(a.target&&a.target.nodeName=="IFRAME")a:{if(a=a.target.src){a=a.split("#")[0].match(/[^?=&]+=([^&]*)?/g);b=0;for(var c;c=a[b];++b)if(c.indexOf("url")===0){b=unescape(c.split("=")[1]);break a}}b=void 0}_gaq.push(["_trackSocial","twitter","tweet",b])}})})})(window);
/* ]]> */
</script>
</head>
<body id="error-page">
	<div class="container">
		<div class="row">
			<div class="col-xs-12">
				<div id="error-box">
					<div class="row">
						<div class="col-xs-12">
							<div id="error-box-inner">
								<img src="<c:url value="/resources/image/error-404-v3.png"></c:url>">
							</div>
							<h1>ERROR 404</h1>
							<p>
								Page not found.<br/>
								If you find this page, let us know.
							</p>
							<p>
								Go back to <a href="${pageContext.request.contextPath }/">homepage</a>.
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="<c:url value="/resources/js/jquery-1.11.1.min.js"></c:url>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"></c:url>"></script>



</body>
</html>