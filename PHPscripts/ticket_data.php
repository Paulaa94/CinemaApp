<?php

	require_once 'conf.php';
	header('Content-Type: application/json');


	$connect = mysqli_connect("HOSTNAME, USERNAME, PASSWORD, DB_NAME);
	$query = "SELECT title, director, showingStart, showingEnd, description FROM Film";
	$result = mysqli_query($connect, $query);
	$Film = array();
	
	while($row = mysqli_fetch_array($result)){
		
		$Film[] = array(
		
			'title'		 	=> $row["title"],
			'director'	 	=> $row["director"],
			'showingStart'	=> $row["showingStart"],
			'showingEnd' 	=> $row["showingEnd"],
			'description'	=> $row["description"]
		);
	}
?>