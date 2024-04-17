<?php
error_reporting(E_ERROR | E_PARSE);

$conn = new mysqli("localhost", "root", "", "hobbyapps");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$password = $_GET['password'];
$id = $_GET['iduser'];

$stmt = $conn->prepare("UPDATE user SET password=? WHERE iduser=?");
$stmt->bind_param("ss", $password, $id);

if ($stmt->execute()) {
    $arr = ["result" => "success", "password" => $password];
} else {
    $arr = ["result" => "error", "password" => "Gagal update data"];
}

echo json_encode($arr);

$stmt->close();
$conn->close();
?>
