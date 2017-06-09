<?php

	require_once 'conf.php';

	class DB_connection {
		
		private $connect;
		
		function __construct(){
			
			$this->connect = mysqli_connect(HOSTNAME, USERNAME, PASSWORD, DB_NAME) or die ("Connection error");
			
		}
		
		public function get_connection(){
			
			return $this->connect;
		}
		
	}
	
	
?>