<?php 
    $con = mysqli_connect("localhost", "mmuyaho", "pusan11!!", "mmuyaho");

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];

    $statement = mysqli_prepare($con, "SELECT userID FROM USER WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID);
	mysqli_stmt_bind_result($statement, $userPassword);
    
	$response = array();
    $response["success"] = false;
	
	while(mysqli_stmt_fetch($statement)){
		$response["success"] = true;
		$response["userID"] = $userID;
	}
 
    echo json_encode($response);
?>