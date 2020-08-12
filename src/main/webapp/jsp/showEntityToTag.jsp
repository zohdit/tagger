<!DOCTYPE html>
<html lang="en">
<%@page import="bean.Entity"%>
<%@page import="java.util.*"%>
<%@page import="usi.tagger.utilities.Utilities"%>

<%
	Entity toEvaluate = (Entity) session.getAttribute("toEvaluate");
	Integer evaluatorId = (Integer) session.getAttribute("evaluatorId");
	ArrayList<String> existingTags = (ArrayList<String>) session.getAttribute("existingTags");
	Collections.sort(existingTags);
%>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="TAGGER">
    <meta name="author" content="Gabriele Bavota">

    <title>TAGGER</title>

    <!-- Bootstrap Core CSS -->
    <link href="../bower_components/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <!-- Custom Fonts -->
    <link href="../bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <!-- Custom CSS -->
    <link href="../css/clap.css" rel="stylesheet" type="text/css">
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


</head>

<body>

    <div id="wrapper">

	<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="logo" href="../loadHomepage">TAGGER</a>
            </div>
            <!-- /.navbar-header -->
     </nav>

      
           <div class="col-md-10 col-md-offset-1" style="margin-top:10px">
                
                <div class="panel"  style="margin-top:20px">
                        <div class="panel-heading">
                        	<h3 class="panel-title">Entity to Tag</h3>
                    	</div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTable">
                                    <thead>
                                        <tr>
                                            <th><%=toEvaluate.getType()%></th>
                                            <th width="500">Tag</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr class="odd gradeX">
                                            <td><%=toEvaluate.getTextToShow()%></td>
                                            <td style="text-align:center">
                                            
                                            	<form class="form-horizontal" role="form"  action="../insertTagByEvaluator" method="post">
                                <div class="form-group">
									<input type="text" size="50" list="tag" name="tag"/>
										<datalist id="tag"  name="tag">
  											<% for(String tag: existingTags) {%>
          									<option value="<%=tag %>"><%=tag %></option>
          									<%} %>
										</datalist>
                                </div>
                                
                                <input type="hidden" name="evaluatorId" id="evaluatorId" value="<%=evaluatorId%>">
                                <input type="hidden" name="entityId" id="entityId" value="<%=toEvaluate.getId()%>">
                                
                                <div class="form-check">
  									<input class="form-check-input" type="checkbox" value="1" id="isInteresting" name="isInteresting">
 								    <label class="form-check-label" for="isInteresting">
    									Mark for qualitative analysis
  									</label>
								</div>
                                
                                <div class="row" style="margin-top:20px">
                                <button class="btn btn-sm btn-primary" type="submit">Add tag</button>
                                </div>
                        </form>
                                            
                                            </td>
                                        </tr>
                                        <tr><td colspan="3"><b>EXISTING TAGS</b><br>
                                        <% for(String tag: existingTags) {
                                        	if(existingTags.indexOf(tag)%2==0){%>
                                        		<span style="color: #ff0000"><%=tag%></span>
                                        	<%} else {%>
                                        		<span style="color: #0000ff"><%=tag%></span>
                                        	<%} %>
                                        <%} %>
                                        </td></tr>
                                        
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                        
                    </div> 
            </div>
        
        <!-- /#page-wrapper -->
     <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
	

</body>



</html>
