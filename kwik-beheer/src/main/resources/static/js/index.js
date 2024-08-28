$(document).ready(function() {
    function loadRooms() {
        $.ajax({
            url: 'http://localhost:8080/api/buildings',
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                var buildingList = $('#buildings');
                buildingList.empty();
                $.each(data, function(index, building) {
                    buildingList.append(
                        '<article>' +
                        '<header>' +
                        '<h5>' +
                        building.buildingName +
                        '</h5>' +
                        '</header>' +
                        '<body>' +
                        '<button onclick="getRooms(' +
                        building.id +
                        ')" ' +
                        '">' +
                        'show rooms' +
                        '</button>' +
                        '</body>' +
                        '</article>'
                    );
                });
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error('Error loading buildings:', textStatus, errorThrown);
            }
        });
    }
    loadRooms();
});


getRooms = (id) => {
    $.ajax({
        url: 'http://localhost:8080/api/buildings/' +
            id +
            '/rooms',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var rooms = $('#rooms');
            rooms.empty();
            $.each(data, function (index, room) {
                rooms.append(
                    '<article>' +
                    '<header>' +
                    '<h5>' +
                    room.roomName +
                    '</h5>' +
                    '</header>' +
                    '<body>' +
                    '<a href="/room/' + room.id + '/temperature' + '">' +
                    'Show temperature readings</a>' +
                    '</body>' +
                    '</article>'
                );
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.error('Error loading buildings:', textStatus, errorThrown);
        }
    });
}