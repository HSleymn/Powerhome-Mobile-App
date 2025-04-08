<?php
error_reporting(E_ERROR | E_PARSE);
$db_host = "localhost";
$db_uid = "abstract-programmer";
$db_pass = "SouleymaneHM954<3";
$db_name = "exempledb";

$db_con = mysqli_connect($db_host, $db_uid, $db_pass, $db_name);
$data = json_decode(file_get_contents("php://input"), true);

$id = $data['id'];
//$id = $_GET['id'];
error_log( " l id est " . $id);
//
$sql = "SELECT * FROM habitat WHERE id = ?";
$stmt = $db_con->prepare($sql);
$stmt->bind_param("i",$id);
$stmt->execute();
$result = $stmt->get_result();
if ($row = $result->fetch_assoc()) {
    $floor = (int)$row['floor'];
    $area = (int)$row['area'];
    error_log(strval($area)); // This will write to the server's error log

}

    error_log( " l nom est ");
    // Get appliances for this habitat
    $appliancesSql = "SELECT id, name, reference, wattage 
                         FROM appliance 
                         WHERE habitat_id = ?";

    $stmt = $db_con->prepare($appliancesSql);
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $appliancesResult = $stmt->get_result();
    $appliances = [];

while ($applianceRow = $appliancesResult->fetch_assoc()) {
        error_log( " l nom est " . $$applianceRow['name']);

        $appliances[] = [
            'id' => intval($applianceRow['id']),
            'name' => $applianceRow['name'],
            'reference' => $applianceRow['reference'],
            'wattage' => intval($applianceRow['wattage'])
        ];
}


$response = array("id" => $id,  "floor" => $floor, "area" => $area, "listApp" => $appliances);
// Fermer la connexion
$stmt->close();
$db_con->close();

// Retourner la réponse en JSON
header('Content-Type: application/json');
echo json_encode($response);
http_response_code(200);
