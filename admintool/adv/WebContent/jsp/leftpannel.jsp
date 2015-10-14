 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<script>
function changeColor(id)
{
  document.getElementById(id).style.color = "#ddf1ff"; // forecolor
  document.getElementById(id).style.backgroundColor = "#6c0031"; // backcolor
}

</script>
</head>
<body>

<div>	<!-- side bar--> 
			
            <aside class="left-side sidebar-offcanvas">
                
                <section class="leftpannel">
                    <!-- Sidebar user panel -->
                    <div class="user-panel">
                        
                        <div class="pull-left info">
                            <p>Hello, ${USER}</p>
							                           
                        </div>
                    </div>
                    
					<div id='menuId'>
						<ul class="sidebar-menu">

							<li class="treeview">
								<a href="crawl" onclick="changeColor('crawlid');">
									<i class="fa-li fa fa-square"></i> 
									<span id='crawlid'>Crawled Ads</span>
								</a>
							</li>
							<li class="treeview">
								<a href="historical" onclick="changeColor('historylid');">
									<i class="fa-li fa fa-square"></i> 
									<span id='historylid'>Historical Ads</span>
								</a>
							</li>
							<li class="treeview">
								<a href="login">
									<i class="fa-li fa fa-square"></i> 
									<span>Logout</span>
								</a>
							</li>
						</ul>
					</div>
                </section>
                <!-- /.sidebar -->

            </aside>
	</div>
</body>
</html>