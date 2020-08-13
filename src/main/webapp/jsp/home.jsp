<!DOCTYPE html>
<%@page import="usi.tagger.utilities.Constants"%>
<html lang="en">
<%@page import="usi.tagger.bean.Evaluator"%>
<%@page import="java.util.*"%>
<%@page import="usi.tagger.utilities.Utilities"%>

<%
    ArrayList<Evaluator> evaluators = (ArrayList<Evaluator>) session.getAttribute("evaluators");
    Double completed = (Double) session.getAttribute("completed");
%>

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="TAGGER">
<meta name="author" content="Gabriele Bavota">

<title>TAGGER</title>

<!-- Bootstrap Core CSS -->
<link href="../bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- MetisMenu CSS -->
<link href="../bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">
<!-- Timeline CSS -->
<link href="../dist/css/timeline.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="../dist/css/sb-admin-2.css" rel="stylesheet">
<!-- Morris Charts CSS -->
<link href="../bower_components/morrisjs/morris.css" rel="stylesheet">
<!-- Custom Fonts -->
<link href="../bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
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

		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="logo" href="../loadHomepage">TAGGER</a>
			</div>
			<!-- /.navbar-header -->
		</nav>


		<div class="col-md-6 col-md-offset-3" style="margin-top: 10px">
			<h2>
				Tagged documents:
				<%=completed%>%
			</h2>

			<div class="panel panel-default" style="margin-top: 20px">
				<div class="panel-heading">
					<h3 class="panel-title">Available Evaluators</h3>
				</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="dataTable_wrapper">
						<table class="table table-striped table-bordered table-hover"
							id="dataTable">
							<thead>
								<tr>
									<th>Name</th>
									<th>Tagged Documents</th>
									<th>Continue Tagging</th>
								</tr>
							</thead>
							<tbody>
								<%
								    for (Evaluator evaluator : evaluators) {
								%>
								<tr class="odd gradeX">
									<td><%=evaluator.getName()%></td>
									<td><%=evaluator.getEvaluatedEntities()%></td>
									<td style="text-align: center"><a
										class="fa fa-caret-square-o-right detailsIcon"
										href="../getNextTaggingByEvaluator?dataset=<%=Constants.MNIST%>&evaluatorId=<%=evaluator.getId()%>"></a>
									<td style="text-align: center"><a
										class="fa fa-car detailsIcon"
										href="../getNextTaggingByEvaluator?dataset=<%=Constants.BEAMNG%>&evaluatorId=<%=evaluator.getId()%>"></a>

									</td>
								</tr>
								<%
								    }
								%>
							</tbody>
						</table>
					</div>
					<!-- /.table-responsive -->
				</div>
				<!-- /.panel-body -->

			</div>
			<div class="panel panel-default" style="margin-top: 30px">
				<div class="panel-heading">
					<h3 class="panel-title">Add a new evaluator</h3>
				</div>
				<div class="panel-body">
					<form class="form-horizontal" data-toggle="validator"
						data-delay="0" data-disable="true" role="form"
						action="../insertEvaluator" method="post">
						<div class="form-group">
							<label class="col-md-4 control-label">Name</label>
							<div class="col-md-8">
								<input class="form-control" placeholder="Name" name="name"
									id="name" type="text" autofocus
									data-error="The evaluator name is mandatory" required>
								<div class="help-block with-errors"></div>
							</div>
						</div>
						<div class="row">
							<button class="btn btn-sm btn-primary col-md-6 col-md-offset-4"
								type="submit">Add evaluator</button>
						</div>
					</form>
				</div>

			</div>


		</div>

		<!-- /#page-wrapper -->
		<!-- /#page-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="../bower_components/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../dist/js/sb-admin-2.js"></script>

</body>

</html>
