<?php
$query=$_POST['query'];
//$query = "select distinct tr.language from cdeep.tutorial_resources tr, cdeep.tutorial_details td where td.id=tr.tutorial_detail_id and td.foss_category='Linux' order by tr.language";
$query_number=$_POST['query_no'];
//echo "my query" . $que;
//$pw=$_POST['password'];
//connect to the db
//$query_number= "5";
//$query =  "select tr.tutorial_video,td.foss_category,tr.language, td.tutorial_level, td.tutorial_name, td.order_code from CDEEP.tutorial_resources tr, CDEEP.tutorial_details td where tr.tutorial_status='accepted' and tr.tutorial_detail_id=td.id and tr.language='English'and td.foss_category='Linux' ORDER BY td.tutorial_level, td.order_code ASC";
$user = 'root';
$pswd = 'root';
$db = 'workshop';

//IMP: IN QUERY2 CHANGE DB NAME FROM 'cdeep' to 'CDEEP'

//$query = "SELECT wr.cfm_wkshop_date, wr.foss_category, ac.id, ac.institution_name, ac.state_code, ac.city FROM workshop_requests wr, academic_center ac WHERE wr.cfm_wkshop_date > '".DATE("Y-m-d")."' and wr.status=1 and ac.academic_code=wr.academic_code ORDER BY wr.cfm_wkshop_date, wr.cfm_wkshop_time ASC";
//echo $query;

$con=mysqli_connect("127.0.0.1",$user,$pswd,$db);

if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$result = mysqli_query($con,$query);


while($row = mysqli_fetch_array($result))
  {
  switch ($query_number) {
    case "1":
        echo "[" . $row['cfm_wkshop_date'] . "-" . $row['foss_category'] . "@" . $row['institution_name'] . "," . $row['city'] . "]";
	echo "<br>";
        break;
    case "2":
        echo "[" . "(" . $row['name'] . ")" . "(" . $row['rp_fname'] . ")" . "(" . $row['phone'] . ")" . "(" . $row['mail'] . ")" . "]";
	echo "<br>";
        break;
    case "3":
        echo "[". $row['foss_category'] ."]";
        break;
    case "4":
        echo "[". $row['language'] . "]";
        break;
    case "5":
	echo "[". $row['foss_category'].",".$row['language'].",".$row['tutorial_level'].",".$row['tutorial_name'].",".$row['tutorial_video']."]";
	
	}
  }
//echo $array[0];

mysqli_close($con);
?>
