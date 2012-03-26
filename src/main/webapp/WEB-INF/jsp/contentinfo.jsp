<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:url value="/contentinfo/jqgrecords" var="recordsUrl"/>
<c:url value="/contentinfo/download" var="downloadUrl"/>
<c:url value="/contentinfo/download/gettoken" var="downloadTokenUrl"/>
<c:url value="/contentinfo/download/progress" var="downloadProgressUrl"/>
<c:url value="/contentinfo/create" var="createUrl"/>

<html>
<head>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/jquery-ui/pepper-grinder/jquery-ui-1.8.16.custom.css"/>'/>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/ui.jqgrid-4.3.1.css"/>'/>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/style.css"/>'/>
	
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui-1.8.16.custom.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/grid.locale-en-4.3.1.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery.jqGrid.min.4.3.1.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>
	
	<title>User Records</title>
	
	<script type='text/javascript'>
	$(function() {
		$("#grid").jqGrid({
		   	url:'${recordsUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['Id', 'Name', 'Description', 'URL', 'Type'],
		   	colModel:[
		   		{name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true},
		   		{name:'name',index:'name', width:100, editable:true, editrules:{required:true}, editoptions:{size:10}},
		   		{name:'description',index:'description', width:100, editable:true, editrules:{required:true}, editoptions:{size:10}},
		   		{name:'url',index:'url', width:100, editable:true, editrules:{required:true}, editoptions:{size:10}},
		   		{name:'type',index:'type', width:50, editable:true, editrules:{required:true}, 
		   			edittype:"select", formatter:'select', stype: 'select', 
		   			editoptions:{value:"1:Atom;2:RSS"},
		   			formatoptions:{value:"1:Atom;2:RSS"}, 
		   			searchoptions: {sopt:['eq'], value:":;1:Atom;2:RSS"}}
		   	],
		   	postData: {},
			rowNum:10,
		   	rowList:[10,20,40,60],
		   	height: 240,
		   	autowidth: true,
			rownumbers: true,
		   	pager: '#pager',
		   	sortname: 'id',
		    viewrecords: true,
		    sortorder: "asc",
		    caption:"Records",
		    emptyrecords: "Empty records",
		    loadonce: false,
		    loadComplete: function() {},
		    jsonReader : {
		        root: "rows",
		        page: "page",
		        total: "total",
		        records: "records",
		        repeatitems: false,
		        cell: "cell",
		        id: "id"
		    }
		});

		$("#grid").jqGrid('navGrid','#pager',
				{edit:false, add:false, del:false, search:false},
				{}, {}, {}, {}
		);
		
		$("#grid").navButtonAdd('#pager',
				{ 	caption:"Pdf", 
					buttonicon:"ui-icon-arrowreturn-1-s", 
					onClickButton: downloadPdf,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
			);
		
		$("#grid").navButtonAdd('#pager',
				{ 	caption:"Excel", 
					buttonicon:"ui-icon-arrowreturn-1-s", 
					onClickButton: downloadXls,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
			);
		
	});

	function downloadXls() {download('xls');}
	
	function downloadPdf() {download('pdf');}
	
	function download(type) {
		// Retrieve download token
		// When token is received, proceed with download
		$.get('${downloadTokenUrl}', function(response) {
			// Store token
			var token = response.message[0];
			
			// Show progress dialog
			$('#msgbox').text('Processing download...');
			$('#msgbox').dialog( 
					{	title: 'Download',
						modal: true,
						buttons: {"Close": function()  {
							$(this).dialog("close");} 
						}
					});
			
			// Start download
			window.location = '${downloadUrl}'+'?token='+token+'&type='+type;

			// Check periodically if download has started
			var frequency = 1000;
			var timer = setInterval(function() {
				$.get('${downloadProgressUrl}', {token: token}, 
						function(response) {
							// If token is not returned, download has started
							// Close progress dialog if started
							if (response.message[0] != token) {
								$('#msgbox').dialog('close');
								clearInterval(timer);
							}
					});
			}, frequency);
			
		});
	}
	</script>
</head>

<body>
	<h1 id='banner'>System Records</h1>
	
	<div id='jqgrid'>
		<table id='grid'></table>
		<div id='pager'></div>
	</div>
	
	<div id='createForm'>
		<form:form modelAttribute="contentInfo" method="POST" action="{createUrl}">
			<table>
				<tr>
					<td><form:label path="name">Name</form:label></td>
					<td><form:input path="name"/></td>
				</tr>
				<tr>
					<td><form:label path="descriptions">Description</form:label></td>
					<td><form:input path="descriptions"/></td>
				</tr>
				<tr>
					<td><form:label path="url">URL</form:label></td>
					<td><form:input path="url"/></td>
				</tr>
				<tr>
					<td><form:label path="type">Type</form:label></td>
					<td><form:select path="type"><form:options items="${contentInfoForm.contentTypeList}" itemValue="id" itemLabel="label"/></form:select></td>
				</tr>
			</table>
			<input type="submit" value="Save"/>
		</form:form>
	</div>
	
	<div id='msgbox' title='' style='display:none'></div>
</body>
</html>