var stompClient = null;

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility
        = connected ? 'visible' : 'hidden';
    document.getElementById('response').innerHTML = '';
}

function connect() {
    var socket = new SockJS('/log-monitoring-app/monitor');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        setConnected(true);
        showWelcomeMessage(frame);
        stompClient.subscribe('/topic/loginfo', function(messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });
}

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function showWelcomeMessage(frame) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode('Connected: ' + frame));
    response.appendChild(p);
}

function showMessageOutput(messageOutput) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    for(var i =0; i<Object.keys(messageOutput.log).length; i++) {
        p.appendChild(document.createTextNode(Object.keys(messageOutput.log)[i].toUpperCase().replace('COUNT','')
            +" : "+ Object.values(messageOutput.log)[i] + " "));
    }
    p.appendChild(document.createTextNode(" (" + messageOutput.time + ")"));
    response.appendChild(p);
}

function clearBox()
{
    document.getElementById('response').innerHTML = "";
}