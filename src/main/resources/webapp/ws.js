
$(document).ready(function() {

	var ws = new WebSocket("ws://localhost:9090/camel-webcam");

	ws.onopen = function(e) {
		if (typeof console !== 'undefined') {
			console.info('WS open');
		}
	};

	ws.onmessage = function (e) {
		var data = JSON.parse(e.data);
		if (data && data.image) {
			$("#image").attr('src', 'data:image/png;base64,' + data.image);
		} 
		else {
			$("#image").attr('src', 'rhiot.png');
		}
		
		if (data.message) {
			$("#message").text(data.message);
		} else {
			$("#message").text('');
		}
	};

	ws.onclose = function() {
		if (typeof console !== 'undefined') {
			console.info('WS close');
		}
	};

	ws.onerror = function(err) {
		if (typeof console !== 'undefined') {
			console.info('WS error');
		}
	};
});

