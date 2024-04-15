<?php
$arr = array(
    array(
        "id" => "1",
        "title" => "Dog Agility Training",
        "image" => "https://loremflickr.com/320/240/dog?lock=1",
        "date" => "2021-11-20",
        "description" => "Train your dog to navigate obstacle courses with agility training.",
        "isi" => [
            "Discover the benefits of agility training for dogs.",
            "Learn how to set up an agility course in your backyard.",
            "Get tips for teaching your dog agility commands.",
            "Join a local agility club for fun competitions."
        ],
        "username_pembuat" => "Valerin"
    ),
    array(
        "id" => "2",
        "title" => "Dog Swimming Lessons",
        "image" => "https://loremflickr.com/320/240/dog?lock=2",
        "date" => "2022-01-19",
        "description" => "Teach your dog to swim safely and confidently.",
        "isi" => [
            "Explore the importance of teaching your dog to swim.",
            "Learn how to introduce your dog to water.",
            "Discover different swimming techniques for dogs.",
            "Find a certified dog swim instructor near you."
        ],
        "username_pembuat" => "Ievana"
    ),
    array(
        "id" => "3",
        "title" => "DIY Dog Toys",
        "image" => "https://loremflickr.com/320/240/dog?lock=3",
        "date" => "2023-07-01",
        "description" => "Create homemade toys to keep your dog entertained.",
        "isi" => [
            "Learn how to make chew toys from household items.",
            "Discover DIY puzzle toys to stimulate your dog's mind.",
            "Get creative with homemade tug-of-war ropes.",
            "Find eco-friendly materials for crafting dog toys."
        ],
        "username_pembuat" => "Sally"
    ),
    array(
        "id" => "4",
        "title" => "Dog Park Playtime",
        "image" => "https://loremflickr.com/320/240/dog?lock=4",
        "date" => "2024-06-13",
        "description" => "Let your dog socialize and burn off energy at the local dog park.",
        "isi" => [
            "Explore the benefits of dog park playtime for your furry friend.",
            "Learn how to ensure a safe and enjoyable experience at the dog park.",
            "Get tips for encouraging positive interactions between dogs.",
            "Discover fun games to play with your dog at the park."
        ],
        "username_pembuat" => "Catherine"
    ),
    array(
        "id" => "5",
        "title" => "Dog Nutrition Guide",
        "image" => "https://loremflickr.com/320/240/dog?lock=5",
        "date" => "2025-12-25",
        "description" => "Provide your dog with a balanced diet for optimal health and wellbeing.",
        "isi" => [
            "Understand the nutritional needs of different dog breeds.",
            "Learn how to choose the right dog food for your pet.",
            "Discover homemade dog food recipes for a wholesome diet.",
            "Get tips for managing your dog's weight and dietary restrictions."
        ],
        "username_pembuat" => "Jeanne"
    )
);

$result = null;
if (isset($_GET["dogs_list"])) {
    $result = $arr;
    echo json_encode($result);
} else if (isset($_GET["username_pembuat"])) {
    $name = $_GET["username_pembuat"];
    foreach ($arr as $dogs) {
        if ($dogs["username_pembuat"] == $name) {
            $result = $dogs;
            echo json_encode($result);
            break;
        }
    }
} else {
    $result = ["result" => "success", "message" => "Dogs not found"];
    echo json_encode($result);
}
?>