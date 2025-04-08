<?php
header('Content-Type: application/json; charset=utf-8');

$db_host = "localhost";
$db_uid = "abstract-programmer";
$db_pass = "SouleymaneHM954<3";
$db_name = "exempledb";

// Create connection
$conn = mysqli_connect($db_host, $db_uid, $db_pass, $db_name);

// Check connection
if ($conn->connect_error) {
    die(json_encode(['error' => 'Connection failed: ' . $conn->connect_error]));
}

// Set charset to UTF-8
$conn->set_charset("utf8");

// First get all habitats with their residents
$sql = "SELECT h.id as habitat_id, h.floor, h.area, 
            u.firstname, u.lastname
            FROM habitat h
            LEFT JOIN user u ON u.id_habitat = h.id
            ORDER BY h.floor, u.lastname";

$result = $conn->query($sql);

if ($result === false) {
error_log("ERRORA");
}

$habitats = [];

while($row = $result->fetch_assoc()) {
    $habitatId = $row['habitat_id'];
    $residentName = trim($row['firstname'] . ' ' . $row['lastname']);

    // Get appliances for this habitat
    $appliancesSql = "SELECT id, name, reference, wattage 
                         FROM appliance 
                         WHERE habitat_id = ?";

    $stmt = $conn->prepare($appliancesSql);
    $stmt->bind_param("i", $habitatId);
    $stmt->execute();
    $appliancesResult = $stmt->get_result();
    $appliances = [];
    while($applianceRow = $appliancesResult->fetch_assoc()) {
        $appliances[] = [
            'id' => intval($applianceRow['id']),
            'name' => $applianceRow['name'],
            'reference' => $applianceRow['reference'],
            'wattage' => intval($applianceRow['wattage'])
        ];
    }

    $habitats[] = array(
        'id' => intval($row['habitat_id']),
        'residentName' => $residentName,
        'floor' => intval($row['floor']),
        'area' => floatval($row['area']),
        'appliances' => $appliances
    );
    error_log($row['habitat_id']);

    $stmt->close();
}

echo json_encode($habitats);

$conn->close();
?>