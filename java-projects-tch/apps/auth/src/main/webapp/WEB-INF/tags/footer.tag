<%@ tag language="java" pageEncoding="ISO-8859-1"%>
	 
	<script src="${staticContextPath}/js/lib/jquery/jquery-1.11.1.min.js"></script>
    <script src="${staticContextPath}/js/lib/jquery-mobile/jquery.mobile-1.4.2.min.js"></script>    
    <script src="${staticContextPath}/js/app/auth.js"></script>
    <script src="${staticContextPath}/js/lib/jquery-validation/jquery.validate.min.js"></script>
    <script src="${staticContextPath}/js/lib/jquery-validation/additional-methods.min.js"></script> 
       <script>
    	var controllerContextPath='${controllerContextPath}';
    	var staticContextPath='${staticContextPath}';
        $( document ).on( "pagecreate", function() {
        	bootstrapAuth();
        });
    </script>
</body>
</html>