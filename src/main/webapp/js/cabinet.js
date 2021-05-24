let activityList = null;

$.get("activityList", function (data) {
    if (data != '') {
        activityList = data;
    }
}, "json").done(function () {
    let cardsContent = "";

    jQuery.each(JSON.parse(activityList), function (i, activity) {
        cardsContent += "<div class='card'>" +
            "<div class='card-body'>" +
            "<h5 class='card-title'>Product name : " + activity.name + "</h5>" +
            "<h6 class='card-subtitle mb-2 text-muted'> Price : " + activity.status + "</h6>" +
            "<p class='card-text'>Description : " + activity.description + "</p>" +
            "<a href='product?id=" + activity.id + "'class='card-link'>About product</a>" +
            "</div>" +
            "</div>"
    });

    $("div#all-activities").html(cardsContent);
});