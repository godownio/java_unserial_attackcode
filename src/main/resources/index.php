<?php
class CapooObj {
    public function __wakeup()
    {
	$action = $this->action;
	$action = str_replace("\"", "", $action);
	$action = str_replace("\'", "", $action);
	$banlist = "/(flag|php|base|cat|more|less|head|tac|nl|od|vi|sort|uniq|file|echo|xxd|print|curl|nc|dd|zip|tar|lzma|mv|www|\~|\`|\r|\n|\t|\	|\^|ls|\.|tail|watch|wget|\||\;|\:|\(|\)|\{|\}|\*|\?|\[|\]|\@|\\|\=|\<)/i";
	if(preg_match($banlist, $action)){
		die("Not Allowed!");
	}
        system($this->action);
    }
}
header("Content-type:text/html;charset=utf-8");
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['capoo'])) {
    $file = $_POST['capoo'];

    if (file_exists($file)) {
        $data = file_get_contents($file);
        $base64 = base64_encode($data);
    } else if (substr($file, 0, strlen("http://")) === "http://") {
        $data = file_get_contents($_POST['capoo'] . "/capoo.gif");
        if (strpos($data, "PILER") !== false) {
	        die("Capoo piler not allowed!");
        }
        file_put_contents("capoo_img/capoo.gif", $data);
        die("Download Capoo OK");
    } else {
        die('Capoo does not exist.');
    }
} else {
    die('No capoo provided.');
}
?>
<!DOCTYPE html>
<html>
  <head>
    <title>Display Capoo</title>
  </head>
  <body>
    <img style='display:block; width:100px;height:100px;' id='base64image'
       src='data:image/gif;base64, <?php echo $base64;?>' />
  </body>
</html>
