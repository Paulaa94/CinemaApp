<?php
	
	require_once 'connection.php';
	header('Content-Type: application/json');
	
	
		class User{
			
			private $db;
			private $connection;
			
			function __construct(){
				
				$this->db = new DB_connection();
				$this->connection = $this->db->get_connection();
			}
			
			public function does_user_exist($username, $password){
				
				$query = "SELECT * FROM User WHERE username='$username' AND password='$password'";
				$result = mysqli_query($this->connection, $query);
				
				if(mysqli_num_rows($result)>0){
					
					$json['OK'] = 'Witaj '.$username;
					echo json_encode($json);
					mysqli_close($this->connection);
				} 
				else {
					
					$query = "INSERT INTO User(username, password) VALUES ('$username', '$password')";
					$is_insert = mysqli_query($this->connection, $query);
					
					if ($is_insert == 1) {
						
						$json['OK'] = 'Konto zosta³o utworzone, witaj '.$username;
					}
					else {
						
						$json['error'] = 'Nieprawid³owe has³o!';
						
					}
					
					echo json_encode($json);
					mysqli_close($this->connection);
				}
			}
			
		}

		$user = new User();
		
		if (isset($_POST['username'], $_POST['password'])){
			
			$username = $_POST['username'];
			$password = $_POST['password'];
			
			if (!empty($username) && !empty($password)){
				
				$encrypted = md5($password);
				$user -> does_user_exist($username, $encrypted);
						
			}
			
			else {
				
				echo json_encode("Uzupelnij oba pola");
				
			}
		}


?>