<!DOCTYPE html >
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html lang="en">
<head>
<script type="text/javascript">
var customerTimeZoneOffset = "${sessionScope['timeZoneOffset']}";
</script>

<title><tiles:getAsString name="title"/></title>
</head>
<table>
      <tr>
        <td colspan="3">
          <tiles:insertAttribute name="header" />
        </td>
      </tr>
      <tr>
        <td width="10%">
          <tiles:insertAttribute name="menu" />
        </td>
        <td width="70%">
          <tiles:insertAttribute name="body" />
        </td>
        <td width="10%">
          
        </td>
      </tr>
      <tr>
        <td colspan="3">
          <tiles:insertAttribute name="footer" />
        </td>
      </tr>
    </table>
</html>