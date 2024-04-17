<?php
error_reporting(E_ERROR | E_PARSE);
$conn = new mysqli("localhost", "root", "", "hobbyapps");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$username = $_POST['username'];
$password = $_POST['password'];
$firstname = $_POST['firstname'];
$lastname = $_POST['lastname'];

// Ambil ID terakhir dari tabel user
$sql = "SELECT MAX(iduser) FROM user";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $data = $row['MAX(iduser)'];
    }
    echo json_encode(array('result' => 'OK', 'data' => $data));
} else {
    echo json_encode(array('result' => 'ERROR', 'message' => 'No data found'));
    die();
}

// Gunakan parameterized query untuk mencegah SQL Injection
$sql2 = "INSERT INTO user (`username`, `password`, `firstname`, `lastname`) VALUES (?, ?, ?, ?)";
$stmt2 = $conn->prepare($sql2);
$stmt2->bind_param("ssss", $username, $password, $firstname, $lastname);

if ($stmt2->execute()) {
    echo "Record inserted successfully";
} else {
    echo "Error: " . $stmt2->error;
}

$stmt2->close();
$conn->close();
?>
