window.helpme.gps = function(callback) {
    cordova.exec(callback, function(err) {
        callback('Nothing to echo.');
    }, "Helpme", "GPS", []);
};

window.helpme.token = function(token, callback) {
    cordova.exec(callback, function(err) {
        callback('Nothing to echo.');
    }, "Helpme", "Token", [token]);
};