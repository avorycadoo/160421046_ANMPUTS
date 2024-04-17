<?php

// error_reporting(E_ERROR | E_PARSE);
// $c = new mysqli("localhost", "root", "", "hobbyapps");

// if ($c->connect_errno) {
//     $arrayerror = array(
//         'result' => 'ERROR',
//         'msg' => 'Failed to connect DB'
//     );
//     echo json_encode($arrayerror);
//     die();
// }

// $username = $_GET['username'];
// $password = $_GET['password'];

// $stmt = $c->prepare("SELECT * FROM user WHERE username = ? AND password = ?");
// $stmt->bind_param("ss", $username, $password);
// $stmt->execute();
// $result = $stmt->get_result();

// if ($result->num_rows > 0) {
//     $obj = $result->fetch_object();
//     $arrayjson = array(
//         // 'result' => 'OK',
//         'data' => $obj
//     );
// } else {
//     $arrayjson = array(
//         'result' => 'ERROR',
//         'msg' => 'User not found'
//     );
// }
// echo json_encode($arrayjson);
// $stmt->close();
// $c->close();
// die();

error_reporting(E_ERROR | E_PARSE);
$c = new mysqli("localhost", "root", "", "hobbyapps");

if ($c->connect_errno) {
    $arrayerror = array(
        'result' => 'ERROR',
        'msg' => 'Failed to connect DB'
    );
    echo json_encode($arrayerror);
    die();
}


$password = $_GET['password'];
$username = $_GET['username'];
$sql = "SELECT * FROM user WHERE username = '$username' AND password = '$password'";

$result = $c->query($sql);

$obj = $result->fetch_object();

if (!empty($obj)) {
    $arrayjson = array(
        'result' => 'OK',
        'data' => $obj
    );
} else {
    $arrayjson = array(
        'result' => 'ERROR',
        'msg' => 'User not found'
    );
}
echo json_encode($arrayjson);
die();


?>